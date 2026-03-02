package Arrays.MonotonicStack;

import java.util.*;

/**
 * ============================================================
 * PROBLEM: Next Greater Element II (Circular Array)
 * ============================================================
 *
 * Given a circular integer array nums (i.e., the next element of nums[n-1]
 * is nums[0]), return an array result where:
 *
 *   result[i] = the next greater element of nums[i]
 *
 * The next greater element of nums[i] is the first element encountered
 * while traversing forward (wrapping around if needed) that is strictly
 * greater than nums[i]. If no such element exists, result[i] = -1.
 *
 * ------------------------------------------------------------
 * KEY INSIGHT:
 * This is a "Next Greater Element" problem → use a MONOTONIC DECREASING STACK.
 *
 * Circularity is handled by simulating a second pass over the array
 * (i.e. iterate from 0 to 2*n - 1 and use i % n).
 *
 * ------------------------------------------------------------
 * CORE REASONING:
 * - The stack stores indices whose next greater element is NOT yet found.
 * - The stack is maintained in decreasing order of values.
 *
 * - When nums[cur] > nums[stack.top()], we have found the next greater
 *   element for the popped index.
 *
 * IMPORTANT DISTINCTION:
 * We RECORD THE ANSWER FOR THE POPPED ELEMENT INSIDE THE DOMINANCE LOOP,
 * because at pop time its right boundary is finalized forever.
 *
 * ------------------------------------------------------------
 * TIME COMPLEXITY: O(n)
 * SPACE COMPLEXITY: O(n)
 *
 * ============================================================
 */
public class NextGreaterElementII {

    public static int[] nextGreaterElements(int[] nums) {
        int n = nums.length;
        int[] result = new int[n];
        Arrays.fill(result, -1);

        Deque<Integer> stack = new ArrayDeque<>();

        // We simulate circular traversal by iterating 2*n times
        for (int i = 0; i < 2 * n; i++) {
            int idx = i % n;

            // DOMINANCE LOOP:
            // Resolve all elements smaller than current
            while (!stack.isEmpty() && nums[idx]>nums[stack.peek()]) {
                int prevIdx = stack.pop();
                result[prevIdx] = nums[idx];
            }

            // Only push indices during the FIRST pass
            // (avoid duplicates in stack)
            if (i < n) {
                stack.push(idx);
            }
        }

        return result;
    }

    // ------------------------------------------------------------
    // DRIVER
    // ------------------------------------------------------------
    public static void main(String[] args) {
        int[] nums = {1, 2, 1};

        int[] res = nextGreaterElements(nums);

        System.out.println("Input:  " + Arrays.toString(nums));
        System.out.println("Output: " + Arrays.toString(res));
    }
}