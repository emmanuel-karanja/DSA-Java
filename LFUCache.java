import java.util.*;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Thread-safe LFU Cache using LinkedHashSet
 *
 * Key points:
 * - Coarse-grained ReentrantLock ensures correctness for concurrent access.
 * - minFreq is always correct because all mutating operations are locked.
 * - LinkedHashSet preserves insertion order → oldest key is evicted among same frequency.
 * - O(1) amortized operations for get/put/evict.
 * 
 * Why LinkedHashSet and not ArrayList? 
 *  LinkedHashSet gives O(1) add/remove and preserves insertion order, while ArrayList does not.
 */
public class LFUCache {
    private final int capacity;                       // Max number of items
    private int minFreq;                              // Tracks minimum frequency
    private final Map<Integer, Node> keyToNode;      // key -> Node for O(1) lookup
    private final Map<Integer, LinkedHashSet<Integer>> freqToKeys; // freq -> keys set
    private final ReentrantLock lock = new ReentrantLock(); // coarse-grained lock for thread safety

    private static class Node {
        int value;  // actual value
        int freq;   // frequency counter

        Node(int value) {
            this.value = value;
            this.freq = 1; // new node starts at frequency 1
        }
    }

    public LFUCache(int capacity) {
        this.capacity = capacity;
        this.minFreq = 0;  // cache initially empty
        this.keyToNode = new HashMap<>();
        this.freqToKeys = new HashMap<>();
    }

    // Thread-safe get operation
    public int get(int key) {
        lock.lock(); // acquire lock for read-modify-write
        try {
            if (!keyToNode.containsKey(key)) return -1; // key not found
            Node node = keyToNode.get(key);            // O(1) lookup in HashMap
            incrementFreq(key, node);                  // safely increment frequency
            return node.value;
        } finally {
            lock.unlock();                             // always release lock
        }
    }

    // Thread-safe put operation
    public void put(int key, int value) {
        lock.lock(); // acquire lock to safely mutate state
        try {
            if (capacity == 0) return;                 // nothing to do

            if (keyToNode.containsKey(key)) {
                // Key exists → update value + increment frequency
                Node node = keyToNode.get(key);
                node.value = value;                    // O(1)
                incrementFreq(key, node);              // safe O(1) amortized
            } else {
                // New key → may need eviction
                if (keyToNode.size() >= capacity) {
                    evictLFU();                        // safely evict LFU key
                }
                Node node = new Node(value);
                keyToNode.put(key, node);              // O(1)
                freqToKeys.computeIfAbsent(1, ignore -> new LinkedHashSet<>()).add(key); // O(1)
                minFreq = 1;                            // reset minFreq for new key
            }
        } finally {
            lock.unlock();
        }
    }

    // Increment frequency of a key (called internally by get or put)
    private void incrementFreq(int key, Node node) {
        // still under lock
        int oldFreq = node.freq;
        node.freq++;                                     // increment frequency

        // Remove key from old frequency bucket. It will now move to a different bucket including a new bucket need be.
        LinkedHashSet<Integer> oldSet = freqToKeys.get(oldFreq);
        oldSet.remove(key);  
                                    // O(1)
        if (oldSet.isEmpty()) {  // This ensures that the last bucket has the smallest freq
            freqToKeys.remove(oldFreq);                 // remove empty bucket
            if (minFreq == oldFreq) minFreq++;          // correctly update minFreq to point to the next bucket with a 1 higher 
              //frequency
        }

        // Add key to new frequency bucket
        freqToKeys.computeIfAbsent(node.freq, ignore -> new LinkedHashSet<>()).add(key); // O(1)
    }

    // Evict the LFU key
    private void evictLFU() {
        // still under lock
        LinkedHashSet<Integer> keys = freqToKeys.get(minFreq); // always get min frequency bucket
        int evictKey = keys.iterator().next();                 // oldest key in bucket → LRU tie-breaker
        keys.remove(evictKey);                                 // O(1)
        if (keys.isEmpty()) {
            freqToKeys.remove(minFreq);        // cleanup empty bucket
        }
        keyToNode.remove(evictKey);                            // remove from main map
    }
}