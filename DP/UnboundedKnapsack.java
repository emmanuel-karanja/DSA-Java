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
        int[][] dp = new int[n+1][W + 1]; // dp[i][w], all initialized to 0

        for (int i = 1; i <= n; i++) {          // items
            for (int w = 1; w <= W; w++) {      // capacity
                if (weight[i-1] <= w) {
                    dp[i][w] = Math.max(
                        dp[i-1][w],                        // skip item i-1
                        value[i-1] + dp[i][w - weight[i-1]] // take item i-1 again
                    );
                } else {
                    dp[i][w] = dp[i-1][w]; // cannot take item
                }
            }
        }

        return dp[n][W];
    }


    public static void main(String[] args) {
        int[] weight = {2, 3, 4};
        int[] value = {3, 4, 5};
        int W = 7;

        System.out.println("Maximum value: " + unboundedKnapsack(weight, value, W));
        // Output: 10 (e.g., items 3+4 or 2+2+3)
    }
}
