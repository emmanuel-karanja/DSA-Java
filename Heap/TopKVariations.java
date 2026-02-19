package Heap;


import java.util.*;

/**
 * PROBLEM:
 * Generic Top-K Variations using DP.
 *
 * You have a DP problem where each state can have multiple possible values.
 * Instead of storing just the best value, you store the **top-K values** for each state.
 * 
 * This is useful for:
 *  - Top-K paths in a DAG
 *  - Top-K subsequences or sums
 *  - Combinatorial problems where multiple outcomes matter
 *
 * APPROACH:
 * 1. Each DP state stores a PriorityQueue (min-heap) of size at most K.
 *    This keeps the top-K largest values for that state.
 * 2. When transitioning from previous states:
 *    - Merge top-K lists from previous states + current value.
 *    - Keep only top-K values using min-heap.
 * 3. After processing all states, DP[final_state] contains the top-K variations.
 *
 * COMPLEXITY:
 * - Let N = number of states
 * - Let K = top-K variations
 * - Let T = number of predecessors for a state (e.g., edges in a DAG)
 * - Time: O(N * T * K * log K) because for each predecessor we merge heaps of size K
 * - Space: O(N * K)
 */

public class TopKVariations {

    /**
     * Merge two top-K lists into one top-K list using min-heap.
     */
    private static List<Integer> mergeTopK(List<Integer> a, List<Integer> b, int K) {
        PriorityQueue<Integer> minHeap = new PriorityQueue<>();
        for (int x : a) minHeap.offer(x);
        for (int x : b) {
            minHeap.offer(x);
            if (minHeap.size() > K) minHeap.poll(); // remove smallest
        }
        List<Integer> result = new ArrayList<>(minHeap);
        result.sort(Collections.reverseOrder()); // descending order
        return result;
    }

    /**
     * Generic DP function for Top-K variations
     * Example: DP[i] = top-K sums/subsequences ending at index i
     */
    public static List<Integer>[] topKVariations(int[] arr, int K) {
        int N = arr.length;
        // Each DP[i] stores top-K sums ending at arr[i]
        List<Integer>[] dp = new List[N];

        for (int i = 0; i < N; i++) {
            dp[i] = new ArrayList<>();
            dp[i].add(arr[i]); // start new subsequence with arr[i]

            // Merge with previous states
            for (int j = 0; j < i; j++) {
                List<Integer> merged = mergeTopK(dp[j], List.of(arr[i]), K);
                dp[i] = mergeTopK(dp[i], merged, K);
            }
        }

        // Collect all top-K across all DP states
        List<Integer> topKAll = new ArrayList<>();
        for (List<Integer> state : dp) {
            topKAll = mergeTopK(topKAll, state, K);
        }
        return new List[]{topKAll};
    }

    public static void main(String[] args) {
        int[] arr = {3, 2, 1, 4};
        int K = 5;

        List<Integer>[] topK = topKVariations(arr, K);
        System.out.println("Top " + K + " variations: " + topK[0]);
    }
}
