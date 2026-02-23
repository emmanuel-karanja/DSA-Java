package Arrays.MonotonicStack;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Problem:
 * Given an integer array `nums` (which may contain negative numbers) and an integer `k`,
 * find the length of the shortest contiguous subarray whose sum is at least `k`.
 * If no such subarray exists, return -1.
 *
 * Example:
 * nums = [2, -1, 2], k = 3
 * The shortest subarray is the entire array [2, -1, 2] with sum = 3, so the output is 3.
 *
 * Reasoning:
 * 1. We first compute the prefix sum array: prefixSum[i] = sum of nums[0..i-1].
 *    This allows us to calculate any subarray sum as prefixSum[j] - prefixSum[i].
 *
 * 2. The goal is to find indices i < j such that prefixSum[j] - prefixSum[i] >= k,
 *    and (j - i) is minimized.
 *
 * 3. To do this efficiently, we maintain a monotonic increasing deque of indices of prefix sums:
 *    - Front of deque: smallest prefix sums, good for forming valid subarrays with current j.
 *    - Back of deque: remove indices where prefixSum[i] >= current prefixSum[j], as they
 *      will never give a shorter subarray in the future.
 *
 * 4. For each prefixSum[j], we:
 *    a) Remove indices from the front while prefixSum[j] - prefixSum[deque.front()] >= k,
 *       updating the minimum length.
 *    b) Remove indices from the back while prefixSum[j] <= prefixSum[deque.back()],
 *       to maintain monotonicity.
 *    c) Add j to the deque as a candidate for future subarrays.
 *
 * 5. This ensures each index is added and removed at most once → O(n) time complexity.
 * 
 * Purpose of the deque: keep only indices of prefix sums that could potentially start the shortest valid subarray.
 * 
 * That's why we check and remove all prefixSums that are >= prefixSum[i] why? They won't give us a start to
 * the shortest valid subarray.
 */
class ShortestSubarraySumAtLeastK {
    public static int shortestSubarray(int[] nums, int k) {
        int n = nums.length;

        //1. Compute prefix sums
        long[] prefixSum = new long[n + 1]; // use long to avoid overflow
        for (int i = 0; i < n; i++) {
            prefixSum[i + 1] = prefixSum[i] + nums[i];
        }

        int minLength = n + 1;
        Deque<Integer> deque = new ArrayDeque<>();

        // 2. Shrink from the left side/front
        for (int i = 0; i <= n; i++) {
            // Shrink from the front: valid subarray found, notice the condition here prefixSum[i] - prefixSum[j] >=k
            // This is the key point
            while (!deque.isEmpty() && prefixSum[i] - prefixSum[deque.peekFirst()] >= k) {
                //Why not right-left+1? because prefixSum[i] is the sum of nums[0..i-1], not nums[0..i].
                minLength = Math.min(minLength, i - deque.pollFirst());
            }

            //3. Maintain monotonic increasing prefix sums
            // Basically remove any non-monotonically increasing prefix sums this is the exit condition. Remove 
            // any prefixSums that are not going to help us.
            while (!deque.isEmpty() && prefixSum[i] <= prefixSum[deque.peekLast()]) {
                deque.pollLast();
            }

            deque.offerLast(i);
        }

        return minLength <= n ? minLength : -1;
    }

    // Driver main
    public static void main(String[] args) {
        int[] nums1 = {2, -1, 2};
        int k1 = 3;
        System.out.println(shortestSubarray(nums1, k1)); // Output: 3

        int[] nums2 = {1, 2, 3, 4, 5};
        int k2 = 11;
        System.out.println(shortestSubarray(nums2, k2)); // Output: 3

        int[] nums3 = {1, -1, 5, -2, 3};
        int k3 = 3;
        System.out.println(shortestSubarray(nums3, k3)); // Output: 1

        int[] nums4 = {-2, -1, -3};
        int k4 = 1;
        System.out.println(shortestSubarray(nums4, k4)); // Output: -1
    }
}
