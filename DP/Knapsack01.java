package DP;

/**
 * 0/1 Knapsack - DP Breakdown
 *
 * Goal:
 *   Maximize total value without exceeding knapsack capacity.
 *
 * State:
 *   dp[i][w] = maximum value achievable using first i items with total weight ≤ w
 *
 * Choices / Decisions:
 *   Take item i (if weight allows) or leave it.
 *   How do we take an item? Have we used it before? And is its weight <= remainingCapacity?
 *   Take an item:
 *
 * Recurrence:
 *   dp[i][w] = max(dp[i-1][w], value[i] + dp[i-1][w - weight[i]])
 *
 * Base Case:
 *   dp[0][w] = 0, dp[i][0] = 0
 *
 * Greedy Check:
 *   ❌ Cannot use greedy. Must explore all subproblems.
 * 
 * NOTE:
 * That we reservce dp[0][0] for the base cases and if we try to match the same with the two weights and items arrays, 
 * where the 0 in the dp represents no item or no weight weights[0] and item[0] represent actual items?
 */
public class Knapsack01 {

    public static int knapsack(int[] weight, int[] value, int W) {
        int n = weight.length;
        int[][] dp = new int[n + 1][W + 1];  //initialized to 0 implicitly all of them. I'd like to make it explicit.

        // Fill DP table
        for (int i = 1; i <= n; i++) {
            for (int w = 1; w <= W; w++) {
                if (weight[i - 1] <= w) {
                    dp[i][w] = Math.max(
                        // By referencing the previous row, we make sure item i only taken once.
                        dp[i - 1][w], // leave item
                        value[i - 1] + dp[i - 1][w - weight[i - 1]] // take item
                    );
                } else {
                    dp[i][w] = dp[i - 1][w]; // cannot take item
                }
            }
        }

        return dp[n][W];
    }

    public static void main(String[] args) {
        int[] weight = {2, 3, 4, 5};
        int[] value = {3, 4, 5, 6};
        int W = 5;
        System.out.println("Maximum value: " + knapsack(weight, value, W)); 
        // Output: 7 (items 0 and 1)
    }
}
