package DP;

/**
 * 0/1 Knapsack - DP Breakdown
 *
 * Goal:
 *   Maximize total value without exceeding knapsack capacity.
 *
 * State:
 * dp[i][w] = the maximum value you can get using only the first i items,
 *            where each of those items can be used at most once, and the total weight is ≤ w.
 *
 * “If I take item i, then everything else must come from the world where item i does not exist anymore.”
 * 
 * Choices / Decisions:
 *   Take item i (if weight allows) or leave it.
 *   How do we take an item? Have we used it before? And is its weight <= remainingCapacity?
 *   Take an item:
 * 
 *   “If I take item i, then everything else must come from the world where item i does not exist anymore.”
 *
 * Recurrence:
 *   dp[i][w] = max(dp[i-1][w], 
 *                 value[i] + dp[i-1][w - weight[i]])   //i is not considered anywhere here.
 * 
 *   Consider the yield case i.e.w e take the item.
 * 
 *    What values[i]+ dp[i-1][w-weights[i]]  "“I take item i (so I add values[i]), and then I add the best value I 
 *    can get from the first i-1 items using the remaining capacity, which is w - weights[i].”"
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
 * 
 * 
 * MY BIG CONFUSION: 
 * 
 *  When i is used within dp[i][w] it means the first i items. and when used with weights[i] it means the
 *  weight of the ith item.
 * 
 * Also realize that DP is 1 index and the arrays are 0 indexed meaning that when considering item i, 
 *  we are really looking at i-1.
 */
public class O1Knapsack {

    public static int knapsack(int[] weight, int[] value, int W) {
        int n = weight.length;
        int[][] dp = new int[n + 1][W + 1];  //initialized to 0 implicitly all of them. I'd like to make it explicit.

        // Fill DP table why start at i=1, and w=1 well because we are doing the i-1 inside.
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
