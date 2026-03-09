package Arrays;

import java.util.*;

/**
 * =====================================================================
 * PROBLEM: Longest Balanced Subarray of Odds and Evens (No Duplicates)
 * =====================================================================
 *
 * Given an integer array nums[], return the length of the longest
 * contiguous subarray that satisfies BOTH conditions:
 *
 *   1) The subarray contains an equal number of odd and even numbers.
 *   2) No element appears more than once inside the subarray.
 *
 * Example:
 *
 * nums = [2,3,5,4,6,7,8]
 *
 * Valid balanced subarray:
 * [3,5,4,6]
 *
 * odds  = 2
 * evens = 2
 * length = 4
 *
 * =====================================================================
 * KEY OBSERVATIONS
 * =====================================================================
 *
 * Balanced odd/even condition can be represented as:
 *
 *      even -> +1
 *      odd  -> -1
 *
 * If we maintain a running value:
 *
 *      balance = (#evens - #odds)
 *
 * Then a balanced subarray occurs when:
 *
 *      balance == 0
 *
 *
 * However, the additional constraint:
 *
 *      "numbers must not repeat"
 *
 * prevents us from using the typical prefix-sum hashmap solution,
 * because prefix sums allow duplicates inside the range.
 *
 *
 * =====================================================================
 * APPROACH
 * =====================================================================
 *
 * We use a SLIDING WINDOW with two pointers.
 *
 * The sliding window ensures:
 *
 *      • All numbers inside the window are unique
 *      • We dynamically expand/shrink the window
 *
 * Data Structures:
 *
 *      HashSet -> track elements currently in the window
 *      balance -> (#evens - #odds)
 *
 *
 * WINDOW RULES
 *
 * 1) Expand the window by moving the right pointer.
 *
 * 2) If a duplicate appears:
 *        shrink the window from the left until the duplicate is removed.
 *
 * 3) Update balance as numbers enter or leave the window.
 *
 * 4) Whenever:
 *
 *        balance == 0
 *
 *    the current window is balanced → update max length.
 *
 *
 * =====================================================================
 * COMPLEXITY
 * =====================================================================
 *
 * Time Complexity:  O(N)
 *      Each element enters and leaves the window at most once.
 *
 * Space Complexity: O(N)
 *      HashSet stores at most N elements.
 *
 * =====================================================================
 */

public class LongestBalancedOddEvenUnique {

    public static int longestBalanced(int[] nums) {

        Set<Integer> seen = new HashSet<>();

        int left = 0;
        int balance = 0;   // OddsCount-EvensCount
        int maxLength = 0;

        for (int right = 0; right < nums.length; right++) {

            // Maintain uniqueness by shrinking window if duplicate appears
            while (seen.contains(nums[right])) {

                if (nums[left] % 2 == 0)
                    balance--;
                else
                    balance++;

                seen.remove(nums[left]);
                left++;
            }

            // Add new element to window
            seen.add(nums[right]);

            if (nums[right] % 2 == 0)
                balance++;
            else
                balance--;

            // Check if balanced
            if (balance == 0)
                maxLength = Math.max(maxLength, right - left + 1);
        }

        return maxLength;
    }

    public static void main(String[] args) {

        int[] nums = {2, 3, 5, 4, 2,6, 7, 8};

        System.out.println(longestBalanced(nums));
    }
}