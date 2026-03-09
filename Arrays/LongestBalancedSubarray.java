package Arrays;

import java.util.*;

/**
 * ======================================================
 * PROBLEM: Longest Balanced Subarray
 * ======================================================
 *
 * Given a binary array containing only 0s and 1s,
 * return the length of the longest contiguous subarray
 * with equal number of 0s and 1s.
 *
 * Key Idea:
 * Convert:
 *      0 -> -1
 *      1 -> +1
 *
 * Now the problem becomes finding the longest subarray
 * with prefix sum = 0.
 *
 * If the same prefix sum appears twice, the elements
 * between them sum to zero.
 *
 * Time Complexity:  O(N)
 * Space Complexity: O(N)
 */
public class LongestBalancedSubarray {

    public static int findMaxLength(int[] nums) {

        Map<Integer, Integer> map = new HashMap<>();
        map.put(0, -1); // prefix sum 0 seen before array starts

        int prefixSum = 0;
        int maxLength = 0;

        for (int i = 0; i < nums.length; i++) {

            if (nums[i] == 0)
                prefixSum -= 1;
            else
                prefixSum += 1;

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

        int[] nums = {0,1,0,0,1,1,0};

        int result = findMaxLength(nums);

        System.out.println("Longest balanced subarray length: " + result);
    }
}