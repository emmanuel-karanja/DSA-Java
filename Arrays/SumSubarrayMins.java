package Arrays;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Problem: Sum of Subarray Ranges (LeetCode 2104)
 * * Reasoning:
 * We use the Contribution Principle. The total sum is the sum of every 
 * subarray's (max - min). This can be decomposed into:
 * Total = Sum(contribution of each element as a max) - Sum(contribution of each element as a min)
 * * To find how many subarrays an element nums[i] is the maximum/minimum for, 
 * we use a Monotonic Stack to find the nearest greater/smaller elements 
 * to its left and right. This defines the boundaries where nums[i] dominates.
 * * Complexity: O(n) time and O(n) space.
 */
public class SumSubarrayMins {
 
   public int sumSubarrayMins(int[] arr) {
        int n = arr.length;
        long totalSum = 0;
        long mod = 1_000_000_007;
        Deque<Integer> stack = new ArrayDeque<>();

        // We go to n to flush the remaining elements out of the stack
        for (int i = 0; i <= n; i++) {
            // Use -1 as a sentinel value to be smaller than any possible element
            int currentVal = (i == n) ? -1 : arr[i];

            while (!stack.isEmpty() && currentVal < arr[stack.peek()]) {
                int mid = stack.pop();
                int leftBoundary = stack.isEmpty() ? -1 : stack.peek();
                int rightBoundary = i;

                // L = mid - leftBoundary
                // R = rightBoundary - mid
                long count = (long) (mid - leftBoundary) * (rightBoundary - mid);
                totalSum = (totalSum + (count * arr[mid])) % mod;
            }
            stack.push(i);
        }

        return (int) totalSum;
    }
}
