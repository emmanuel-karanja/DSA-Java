package Arrays;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * PROBLEM:
 * Given an integer array `nums` (can contain negative numbers) and an integer `k`, 
 * count the number of contiguous subarrays whose sum is less than or equal to `k`.
 *
 * Example:
 * nums = [1, -1, 2], k = 2
 * Valid subarrays: [1], [1,-1], [-1], [-1,2], [2], [1,-1,2] → sum ≤ 2
 * Output: 6
 *
 * REASONING:
 * 1. Compute prefix sums: prefixSum[i] = sum(nums[0..i-1])
 *    - This allows us to calculate sum of any subarray nums[start..end-1] as prefixSum[end] - prefixSum[start]
 *
 * 2. Goal: find number of pairs (start, end) with start < end such that prefixSum[end] - prefixSum[start] ≤ k
 *
 * 3. Monotonic queue / sliding window trick:
 *    - Maintain a deque of indices of prefix sums in increasing order.
 *    - As we iterate over prefixSum[i]:
 *        a) Remove from front while prefixSum[i] - prefixSum[deque.peekFirst()] > k
 *           → these starting indices cannot form valid subarrays with current end i
 *        b) Count the number of valid subarrays ending at i: it’s the number of indices left in deque
 *        c) Remove from back while prefixSum[i] <= prefixSum[deque.peekLast()] to maintain monotonicity
 *        d) Add i to deque for future subarrays
 *
 * 4. This ensures O(n) time: each index is added/removed at most once.
 */
public class CountSubarraysSumAtMostK {
    public static int countSubarrays(int[] nums, int k) {
        int n = nums.length;

        // 1. Compute prefix sums
        long[] prefixSum = new long[n + 1]; // use long to avoid overflow
        for (int i = 0; i < n; i++) {
            prefixSum[i + 1] = prefixSum[i] + nums[i];
        }

        int count = 0;
        Deque<Integer> deque = new ArrayDeque<>();

        // 2. Initialize deque with starting point 0
        deque.offerLast(0);

        for (int i = 1; i <= n; i++) {
            // 1. Remove front indices that would give sum > k
            while (!deque.isEmpty() && prefixSum[i] - prefixSum[deque.peekFirst()] > k) {
                deque.pollFirst();
            }

            // 2. The remaining indices in deque are valid start points for subarrays ending at i-1
            count += deque.size();

            // 3. Maintain monotonic increasing prefix sums in deque
            while (!deque.isEmpty() && prefixSum[i] <= prefixSum[deque.peekLast()]) {
                deque.pollLast();
            }

            deque.offerLast(i);
        }

        return count;
    }

    // Driver main
    public static void main(String[] args) {
        int[] nums1 = {1, -1, 2};
        int k1 = 2;
        System.out.println(countSubarrays(nums1, k1)); // Output: 6

        int[] nums2 = {2, 1, 3};
        int k2 = 3;
        System.out.println(countSubarrays(nums2, k2)); // Output: 4

        int[] nums3 = {-1, -2, 3};
        int k3 = 2;
        System.out.println(countSubarrays(nums3, k3)); // Output: 5

        int[] nums4 = {1, 2, 3, 4};
        int k4 = 5;
        System.out.println(countSubarrays(nums4, k4)); // Output: 6
    }
}
