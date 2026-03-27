package Arrays.MonotonicStack;

import java.util.ArrayDeque;
import java.util.Deque;

public class ShortestSubarraySumAtMostK {
    public int shortestSubarrayAtMostK(int[] nums, int k) {
        int n = nums.length;
        long[] prefixSum = new long[n + 1];
        for (int i = 0; i < n; i++) {
            prefixSum[i + 1] = prefixSum[i] + nums[i];
        }
        int minLength = n + 1;
        Deque<Integer> deque = new ArrayDeque<>();

        for (int i = 0; i <= n; i++) {
            // 1. VALIDITY CHECK (The Left Side)
            // We need ps[i] - ps[j] <= k  =>  ps[j] >= ps[i] - k
            // In a decreasing deque, the LARGEST values are at the front.
            while (!deque.isEmpty() && prefixSum[i] - prefixSum[deque.peekFirst()] <= k) {
                minLength = Math.min(minLength, i - deque.pollFirst());
            }

            // 2. MONOTONICITY (The Right Side)
            // To find the shortest sum <= K, we prefer LARGER prefix sums (easier to keep sum small).
            // So we maintain a DECREASING deque. 
            // If current ps[i] >= last, the last one is useless (smaller and further away).
            while (!deque.isEmpty() && prefixSum[i] >= prefixSum[deque.peekLast()]) {
                deque.pollLast();
            }

            deque.offerLast(i);
        }

        return minLength <= n ? minLength : -1;
    }
}
