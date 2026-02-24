import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * PROBLEM STATEMENT:
 * Design and implement a thread-safe, multi-tenant Rate Limiter using the Token Bucket algorithm.
 * The system must support different capacities and refill rates per user, ensure high 
 * concurrency without global locking, and handle "bursty" traffic patterns gracefully.
 *
 * DESIGN REASONING:
 * 1. ALGORITHM: Token Bucket was chosen over Leaky Bucket to allow for bursty traffic while 
 * maintaining a strict long-term average rate.
 * 2. REFILL STRATEGY: "Lazy Refill" is used. Instead of a background thread for every bucket 
 * (which doesn't scale), we calculate the token count on-demand based on elapsed time.
 * 3. CONCURRENCY: 
 * - ConcurrentHashMap ensures thread-safe access to different user buckets.
 * - computeIfAbsent ensures atomic creation of buckets (prevents race conditions).
 * - Instance-level 'synchronized' blocks ensure that multiple threads for the SAME user 
 * are handled safely without blocking OTHER users.
 * 4. PRECISION: Uses 'double' for token counts to allow for smooth, fractional refills.
 * 
 * NOTES:
 * The Case for System.nanoTime()
  Monotonicity: nanoTime() measures elapsed time from an arbitrary fixed point. It is guaranteed to only go forward.
  currentTimeMillis() follows the "Wall Clock," which can be adjusted backward by NTP (Network Time Protocol) syncs.
  If the clock jumps back 1 second, your elapsed time becomes negative, and your rate limiter breaks.

Precision: As the name suggests, it provides nanosecond precision. While you don't need nanoseconds for a rate limiter,
 the increased resolution prevents "zero-elapsed-time" issues in high-throughput loops.
 */
public class RateLimiterService {

    private final Map<String, TokenBucket> userBuckets = new ConcurrentHashMap<>();
    private final long defaultCapacity;
    private final double defaultTokensPerSec;

    public RateLimiterService(long capacity, double tokensPerSec) {
        this.defaultCapacity = capacity;
        this.defaultTokensPerSec = tokensPerSec;
    }

    public boolean isAllowed(String sourceId) {
        if (sourceId == null || sourceId.isBlank()) return false;

        // Atomic retrieval/creation
        TokenBucket bucket = userBuckets.computeIfAbsent(sourceId, 
            id -> new TokenBucket(defaultCapacity, defaultTokensPerSec));

        return bucket.tryConsume();
    }

    // Inner class to encapsulate bucket state
    private static class TokenBucket {
        private final long capacity;
        private final double tokensPerNano; // Switched from ms to nano
        
        private double currentTokens;
        private long lastRefillNano; // Store the nano-point

        public TokenBucket(long capacity, double tokensPerSecond) {
            this.capacity = capacity;
            // tokens/sec divided by 1,000,000,000
            this.tokensPerNano = tokensPerSecond / 1_000_000_000.0;
            this.currentTokens = (double) capacity;
            this.lastRefillNano = System.nanoTime();
        }

        public synchronized boolean tryConsume() {
            refill();
            if (currentTokens >= 1.0) {
                currentTokens -= 1.0;
                return true;
            }
            return false;
        }

        private void refill() {
            long now = System.nanoTime();
            // Since nanoTime is monotonic, 'elapsed' will not be negative
            long elapsed = now - lastRefillNano;

            if (elapsed > 0) {
                double tokensToAdd = elapsed * tokensPerNano;
                currentTokens = Math.min(capacity, currentTokens + tokensToAdd);
                lastRefillNano = now;
            }
        }
    }

    // --- DRIVER MAIN WITH TEST CASES ---
    public static void main(String[] args) throws InterruptedException {
        // 5 tokens capacity, 10 tokens per second (1 token every 100ms)
        RateLimiterService limiter = new RateLimiterService(5, 10);

        System.out.println("--- Test 1: Bursty Traffic ---");
        for (int i = 1; i <= 7; i++) {
            boolean allowed = limiter.isAllowed("user_1");
            System.out.println("Request " + i + ": " + (allowed ? "ALLOWED" : "REJECTED"));
        }
        // Result: 1-5 ALLOWED, 6-7 REJECTED (capacity reached)

        System.out.println("\n--- Test 2: Multi-Tenancy (User 2 is independent) ---");
        System.out.println("User 2 Request: " + (limiter.isAllowed("user_2") ? "ALLOWED" : "REJECTED"));
        // Result: ALLOWED

        System.out.println("\n--- Test 3: Refill Logic (Waiting 500ms for 5 tokens) ---");
        Thread.sleep(500); 
        for (int i = 1; i <= 6; i++) {
            boolean allowed = limiter.isAllowed("user_1");
            System.out.println("Request " + i + " after wait: " + (allowed ? "ALLOWED" : "REJECTED"));
        }
        // Result: 1-5 ALLOWED, 6 REJECTED

        System.out.println("\n--- Test 4: Concurrency Simulation ---");
        Runnable task = () -> {
            for(int i=0; i<5; i++) {
                limiter.isAllowed("concurrent_user");
            }
        };
        Thread t1 = new Thread(task);
        Thread t2 = new Thread(task);
        t1.start(); t2.start();
        t1.join(); t2.join();
        System.out.println("Concurrency test finished. (Check logs for thread safety if printing inside)");
    }
}