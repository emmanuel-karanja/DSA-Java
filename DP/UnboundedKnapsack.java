package DP;

/**
 * Unbounded Knapsack - DP Breakdown
 *
 * GOAL:
 *   Maximize the total value of items in the knapsack without exceeding capacity W.
 *   Each item can be taken unlimited times.
 * 
 
 *
 * STATE:
 *   dp[w] = maximum value achievable with total weight ≤ w
 *   - Represents the **best solution for weight w** considering all items.
 * 
 * * WE DON'T CARE ABOUT THE index of the item, here. We only care about the weight.
 * 
 *
 * CHOICES / DECISIONS:
 *   For each weight w:
 *     - Try taking any item i (if weight[i] <= w)
 *     - Each item can be used **multiple times**, so we stay at the same item row in the DP
 *
 * TRANSITION (RECURRENCE):
 *   dp[w] = max(dp[w], value[i] + dp[w - weight[i]])  for all i where weight[i] <= w
 *   - Either don’t take item i → dp[w] stays the same
 *   - Or take item i → add its value and look at remaining capacity (dp[w - weight[i]])
 *
 * BASE CASE:
 *   dp[0] = 0  // No weight → no value
 *
 * EVALUATION ORDER:
 *   We compute dp[w] from 0 → W so that all subproblems for smaller weights are available
 *
 * GREEDY CHECK:
 *   ❌ Cannot use greedy. Must explore all items at each capacity.
 *
 * DIFFERENCE FROM 0/1 KNAPSACK:
 *   - In 0/1 Knapsack, we reference dp[i-1][w - weight[i-1]] → cannot reuse item
 *   - In Unbounded Knapsack, we reference dp[w - weight[i]] → can reuse item multiple times
 */
public class UnboundedKnapsack {

    public static int unboundedKnapsack(int[] weight, int[] value, int W) {
        int n = weight.length;
        int[] dp = new int[W + 1]; // dp[0..W], initialized to 0

        // Bottom-up DP, we start from item with w 0 and item 0. In the 0/1 Knapsack, we start at 1.
        for (int w = 0; w <= W; w++) {
            for (int i = 0; i < n; i++) {
                if (weight[i] <= w) {
                    // Transition: take item i or not
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
        // Output: 10 (e.g., items 3+4 or 2+2+3)
    }
}
