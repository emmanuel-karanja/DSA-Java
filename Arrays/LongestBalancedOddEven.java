package Arrays;

import java.util.*;

/**
 * ==========================================================
 * PROBLEM: Longest Balanced Subarray of Odds and Evens
 * ==========================================================
 *
 * Given an integer array, return the length of the longest
 * contiguous subarray with equal number of odd and even numbers.
 *
 * Approach:
 * Convert:
 *      even -> +1
 *      odd  -> -1
 *
 * Now we find the longest subarray with sum = 0 using
 * prefix sum and a HashMap.
 *
 * Time Complexity:  O(N)
 * Space Complexity: O(N)
 */
public class LongestBalancedOddEven {

    public static int longestBalanced(int[] nums) {

        Map<Integer, Integer> map = new HashMap<>();
        map.put(0, -1);

        int prefixSum = 0;
        int maxLength = 0;

        for (int i = 0; i < nums.length; i++) {

            if (nums[i] % 2 == 0)
                prefixSum += 1;
            else
                prefixSum -= 1;

            if (map.containsKey(prefixSum)) {

                int prevIndex = map.get(prefixSum);
                maxLength = Math.max(maxLength, i - prevIndex);

            } else {
                map.put(prefixSum, i);
            }
        }

        return maxLength;
    }

    public static void main(String[] args) {

        int[] nums = {2, 3, 5, 4, 6, 7, 8};

        System.out.println(longestBalanced(nums));
    }
}