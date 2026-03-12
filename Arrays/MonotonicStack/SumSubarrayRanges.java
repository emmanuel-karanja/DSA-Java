package Arrays.MonotonicStack;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * ==========================================================
 * PROBLEM: Sum of Subarray Ranges (LeetCode 2104 Variant)
 * ==========================================================
 *
 * Given an integer array nums[], return the sum of (max - min)
 * over **all subarrays** of nums.
 *
 * Example:
 * nums = [1,2,3]
 * Subarrays:
 *  [1]      → max-min = 0
 *  [2]      → 0
 *  [3]      → 0
 *  [1,2]    → 2-1 = 1
 *  [2,3]    → 3-2 = 1
 *  [1,2,3]  → 3-1 = 2
 * Total sum = 4
 *
 * ==========================================================
 * DETAILED REASONING
 * ==========================================================
 *
 * IDEA: All subarries ending at i can be calculated by constructing all
 *  subarries from j to i where  0 to j<i. 
 * 
 *  if you know i, the total number of subarries upto i is i+1;
 * 
 *  and for an array of size n = n(n+1)/2
 * Key Idea: Contribution Principle + Monotonic Stack (Single Pass)
 *
 * 1. **Contribution Principle**
 *    Total sum of subarray ranges = Sum(contribution of each element as max)
 *                                    - Sum(contribution of each element as min)
 *
 * 2. **How to compute contribution**
 *    For each element nums[i]:
 *      - Find nearest smaller element to left (prevSmaller) and right (nextSmaller)
 *      - Find nearest greater element to left (prevGreater) and right (nextGreater)
 *    Then:
 *      - Count of subarrays where nums[i] is the minimum = (i - prevSmaller) * (nextSmaller - i)
 *      - Count of subarrays where nums[i] is the maximum = (i - prevGreater) * (nextGreater - i)
 *    Multiply count by nums[i] to get total contribution.
 *
 * 3. **Monotonic Stacks**
 *    - Use **increasing stack** to find min contributions efficiently.
 *    - Use **decreasing stack** to find max contributions efficiently.
 *
 * 4. **Single Pass Optimization**
 *    - Traverse the array once, maintaining both stacks.
 *    - Pop elements violating monotonicity, compute contribution, and update total.
 *    - Use sentinel values (Integer.MIN_VALUE / MAX_VALUE) at the end to flush stacks.
 *
 * ==========================================================
 * COMPLEXITY
 * ==========================================================
 *
 * Time  : O(n) → each element enters/leaves stack once
 * Space : O(n) → two stacks storing indices
 *
 * ==========================================================
 */

public class SumSubarrayRanges {

    public static long sumOfSubarrayRanges(int[] arr) {
        int n = arr.length;
        long total = 0;


        Deque<Integer> minStack = new ArrayDeque<>();
        Deque<Integer> maxStack = new ArrayDeque<>();

        for (int i = 0; i <= n; i++) {
            // The sentinel ensures that all elements get processed.
            int currMin = (i == n) ? Integer.MIN_VALUE : arr[i];
            int currMax = (i == n) ? Integer.MAX_VALUE : arr[i];

             // Process maxStack (decreasing)
            while (!maxStack.isEmpty() && currMax > arr[maxStack.peek()]) {
                int mid = maxStack.pop();
                int left = maxStack.isEmpty() ? -1 : maxStack.peek();
                int right = i;
                long count = (long)(mid - left) * (right - mid);
                //Total = Sum(max contributions) − Sum(min contributions) 
                // Contribution = nums[mid] * count
                total += count * arr[mid];
            }
            maxStack.push(i);

            // Process minStack (increasing)
            while (!minStack.isEmpty() && currMin < arr[minStack.peek()]) {
                int mid = minStack.pop();  
                int left = minStack.isEmpty() ? -1 : minStack.peek();
                int right = i;
                long count = (long)(mid - left) * (right - mid);
                //Total = Sum(max contributions) − Sum(min contributions)
                // Contribution= nums[mid] * count
               total -= count * arr[mid];
            }
            minStack.push(i);
           
        }

        return (int) total;
    }

    // Driver main
    public static void main(String[] args) {
        int[] arr1 = {1, 2, 3};
        int[] arr2 = {4, 1, 3, 2};
        int[] arr3 = {1, 3, 2, 5, 4};

        System.out.println("Sum of subarray ranges for [1,2,3]: " +
                sumOfSubarrayRanges(arr1)); // Expected: 4
        System.out.println("Sum of subarray ranges for [4,1,3,2]: " +
                sumOfSubarrayRanges(arr2)); // Expected: 17
        System.out.println("Sum of subarray ranges for [1,3,2,5,4]: " +
                sumOfSubarrayRanges(arr3)); // Example output
    }
}