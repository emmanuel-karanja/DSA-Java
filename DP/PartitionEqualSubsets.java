package DP;

/**
 * PROBLEM
 * -------
 * Given an array of positive integers, determine whether it can be partitioned
 * into two subsets such that the sum of elements in both subsets is equal.
 *
 *
 * GOAL
 * ----
 * Determine if there exists a subset of nums whose sum equals:
 *      target = totalSum / 2
 * If yes, the remaining numbers automatically sum to target as well.
 *
 *
 * STATE
 * -----
 * dp[i][j] = true if it is possible to form sum j using the first i numbers (nums[0..i-1])
 * dp[i][j] = false otherwise
 *
 *
 * CHOICES / DECISIONS
 * -------------------
 * For nums[i-1], we can:
 * 1) Take nums[i-1]  → contributes to forming sum j
 * 2) Do NOT take     → sum j remains unchanged
 *
 *
 * RECURRENCE RELATION
 * -------------------
 * dp[i][j] = dp[i-1][j] || (j >= nums[i-1] && dp[i-1][j - nums[i-1]])
 *
 *
 * BASE CASE
 * ---------
 * dp[0][0] = true  → zero numbers can form sum 0
 * dp[0][j>0] = false
 *
 *
 * EVALUATION ORDER
 * ----------------
 * Fill i from 1 to n
 * Fill j from 0 to target
 *
 *
 * SOLUTION
 * --------
 * Answer is dp[n][target]
 */
public class PartitionEqualSubsets {

    public static boolean canPartition(int[] nums) {
        if (nums == null || nums.length == 0) return false;

        int n = nums.length;
        int totalSum = 0;
        for (int num : nums) totalSum += num;

        if (totalSum % 2 != 0) return false;

        int target = totalSum / 2;

        boolean[][] dp = new boolean[n + 1][target + 1];

        // Base case
        for (int i = 0; i <= n; i++) {
            dp[i][0] = true; // sum 0 is always achievable with empty subset
        }

        // Fill DP table
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= target; j++) {
                dp[i][j] = dp[i - 1][j]; // do not take nums[i-1]
                if (j >= nums[i - 1]) {
                    dp[i][j] = dp[i][j] || dp[i - 1][j - nums[i - 1]]; // take nums[i-1]
                }
            }
        }

        return dp[n][target];
    }
}
