import java.util.concurrent.*;
import java.util.Map;

public class ExpiringHashMap<K, V> {

    // Wrapper for value + expiration timestamp
    private static class CacheEntry<V> {
        final V value;
        final long expiryTimeMillis;

        CacheEntry(V value, long ttlMillis) {
            this.value = value;
            this.expiryTimeMillis = System.currentTimeMillis() + ttlMillis;
        }

        boolean isExpired() {
            return System.currentTimeMillis() > expiryTimeMillis;
        }
    }

    private final ConcurrentHashMap<K, CacheEntry<V>> map = new ConcurrentHashMap<>();
    private final ScheduledExecutorService cleaner = Executors.newSingleThreadScheduledExecutor();

    // Constructor sets up the periodic cleanup
    public ExpiringHashMap(long cleanupIntervalMillis) {
        // Schedule cleanup task
        cleaner.scheduleAtFixedRate(this::cleanup, cleanupIntervalMillis,
                cleanupIntervalMillis, TimeUnit.MILLISECONDS);
    }

    /** Put a value with TTL in milliseconds */
    public void put(K key, V value, long ttlMillis) {
        map.put(key, new CacheEntry<>(value, ttlMillis));
    }

    /** Get the value, removing it if expired */
    public V get(K key) {
        CacheEntry<V> entry = map.get(key);
        if (entry == null) return null;

        if (entry.isExpired()) {
            map.remove(key, entry); // Remove only if still same entry
            return null;
        }

        return entry.value;
    }

    /** Remove a key manually */
    public void remove(K key) {
        map.remove(key);
    }

    /** Cleanup expired entries - called by ScheduledExecutorService */
    private void cleanup() {
        long now = System.currentTimeMillis();
        for (Map.Entry<K, CacheEntry<V>> e : map.entrySet()) {
            CacheEntry<V> entry = e.getValue();
            if (entry.expiryTimeMillis <= now) {
                map.remove(e.getKey(), entry);
            }
        }
    }

    /** Shutdown the executor gracefully */
    public void shutdown() {
        cleaner.shutdown();
        try {
            if (!cleaner.awaitTermination(1, TimeUnit.SECONDS)) {
                cleaner.shutdownNow();
            }
        } catch (InterruptedException ignored) {
            cleaner.shutdownNow();
        }
    }
}
