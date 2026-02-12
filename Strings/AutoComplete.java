package Strings;

/**
 * Problem:
 * Design a search autocomplete system. Given a stream of characters (prefix), 
 * return the top k most frequently searched sentences that start with that prefix.
 *
 * Intuition:
 * 1. Use a Trie to encode all prefixes of sentences.
 * 2. Store a frequency map of sentences at each Trie node to track counts of sentences
 *    passing through that prefix.
 * 3. Use a min-heap of size k to efficiently retrieve the top k sentences for any prefix.
 * 4. Comparator ensures:
 *    - higher frequency comes first
 *    - tie-break lexicographically ascending
 *
 * Key insights:
 * - Storing frequencies inside Trie nodes avoids redundant maps per prefix.
 * - Min-heap of size k keeps memory usage low, especially for nodes with many sentences.
 * - Insertion updates all nodes along the sentence path.
 * - Querying top-k is fast: traverse prefix in Trie → extract top k from min-heap.
 */

import java.util.*;

class TrieNode {
    Map<Character, TrieNode> children = new HashMap<>();
    boolean isEndOfWord = false;
    Map<String, Integer> freqMap = new HashMap<>(); // sentence -> frequency
}

// Helper class to store sentence and its count for the heap
class SentenceCount {
    String sentence;
    int count;

    SentenceCount(String sentence, int count) {
        this.sentence = sentence;
        this.count = count;
    }
}

public class AutoComplete {
    private TrieNode root = new TrieNode();
    private int k; // number of top results to return

    public AutoComplete(int k) {
        this.k = k;
    }

    // Insert a sentence and update frequencies along the path
    public void insert(String sentence) {
        TrieNode node = root;
        for (char c : sentence.toCharArray()) {
            node = node.children.computeIfAbsent(c, key -> new TrieNode());
            node.freqMap.put(sentence, node.freqMap.getOrDefault(sentence, 0) + 1);
        }
        node.isEndOfWord = true;
    }

    // Get top-k autocomplete suggestions for a given prefix
    public List<String> getTopK(String prefix) {
        TrieNode node = root;
        for (char c : prefix.toCharArray()) {
            if (!node.children.containsKey(c)) return new ArrayList<>();
            node = node.children.get(c);
        }

       // Min-heap of size k storing SentenceCount objects
        PriorityQueue<SentenceCount> pq = new PriorityQueue<>(
            (a, b) -> {
                int freqComp = Integer.compare(a.count, b.count); // min-heap on frequency
                return freqComp != 0 ? freqComp : b.sentence.compareTo(a.sentence); // tie  breaker
            }
        );

        for (Map.Entry<String, Integer> entry : node.freqMap.entrySet()) {
            pq.offer(new SentenceCount(entry.getKey(), entry.getValue()));
            if (pq.size() > k) pq.poll(); // keep only top k
        }

        // Extract sentences from min-heap → reverse to get descending order
        List<String> result = new ArrayList<>();
        while (!pq.isEmpty()) {
            result.add(pq.poll().sentence);
        }
        Collections.reverse(result);

        return result;
    }

    public static void main(String[] args) {
        AutoComplete ac = new AutoComplete(3);

        String[] sentences = {
            "apple pie", "app", "apple", "application", "app", "apple", "app store"
        };

        for (String s : sentences) ac.insert(s);

        System.out.println(ac.getTopK("app"));   // top 3 most frequent starting with "app"
        System.out.println(ac.getTopK("apple")); // top sentences starting with "apple"
        System.out.println(ac.getTopK("ap"));    // top sentences starting with "ap"
        System.out.println(ac.getTopK("banana")); // empty list, no matches
    }
}
