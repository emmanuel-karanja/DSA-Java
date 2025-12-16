package DP;

/**
 * Unbounded Knapsack - DP Breakdown
 *
 * Goal:
 *   Maximize total value without exceeding knapsack capacity.
 *
 * State:
 *   dp[w] = maximum value achievable with total weight ≤ w
 *
 * Choices / Decisions:
 *   For each weight w, try taking any item i (multiple times if needed)
 *
 * Recurrence:
 *   dp[w] = max(dp[w], value[i] + dp[w - weight[i]]) for all i where weight[i] <= w
 * 
 * Difference from 0/1 Knapsack:
    We do not move to the next item, because the same item can be used again.
 *
 * Base Case:
 *   dp[0] = 0
 *
 * Greedy Check:
 *   ❌ Cannot use greedy; must explore all options.
 */
public class UnboundedKnapsack {

    public static int unboundedKnapsack(int[] weight, int[] value, int W) {
        int n = weight.length;
        int[] dp = new int[W + 1];

        // Bottom-up DP
        for (int w = 0; w <= W; w++) {
            for (int i = 0; i < n; i++) {
                if (weight[i] <= w) {
                    dp[w] = Math.max(dp[w], value[i] + dp[w - weight[i]]);
                }
            }
        }

        return dp[W];
    }

    public static void main(String[] args) {
        int[] weight = {2, 3, 4};
        int[] value = {3, 4, 5};
        int W = 7;
        System.out.println("Maximum value: " + unboundedKnapsack(weight, value, W));
        // Output: 10 (items 2+2+3 or 3+4 depending on combination)
    }
}
