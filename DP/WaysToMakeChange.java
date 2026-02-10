package DP;
/**
/**
 * PROBLEM:
 * You are given:
 *  - An integer array coins[] of size n, where each element represents a coin denomination
 *  - An integer amount representing the total sum
 *  - You have an unlimited supply of each coin
 *
 * GOAL:
 * Count the number of DISTINCT ways to make up the given amount.
 * (Order does NOT matter: {1,2} is the same as {2,1})
 *
 * EXAMPLE:
 * coins = [1, 2, 5]
 * amount = 5
 * Output = 4
 *
 * INTUITION:
 * This is an Unbounded Knapsack(You can use each coin more than once) / Coin Change (count ways) problem.
 *
 * DP STATE:
 * dp[x][i] = number of ways to make amount x using the first i coins
 *            (coins[0 .. i-1])
 *
 * CHOICES:
 * For coin coins[i-1]:
 *
 * 1. Exclude the coin:
 *    dp[x][i - 1]
 *
 * 2. Include the coin (unlimited times):
 *    dp[x - coins[i - 1]][i]
 * 
 *  And we are finding ways:
 * 
 *  so
 * 
 *  dp[x][i]+=dp[x=coins[i-1]][i] 
 *
 * RECURRENCE:
 * dp[x][i] = dp[x][i - 1]
 * if (x >= coins[i - 1]) {
 *     dp[x][i] += dp[x - coins[i - 1]][i];
 * }
 *
 * BASE CASES:
 * dp[0][i] = 1  // One way to make amount 0: choose no coins
 * dp[x][0] = 0  // No way to make positive amount with 0 coins
 *
 * ANSWER:
 * dp[amount][n]
 *
 * TIME COMPLEXITY:
 * O(amount * n)
 *
 * SPACE COMPLEXITY:
 * O(amount * n)  (can be optimized to O(amount))
 */



public class WaysToMakeChange {

    public static int getWays(int[] coins, int amount) {
        int n = coins.length;
        int[][] dp = new int[amount + 1][n + 1];

        // Base state: amount = 0 → 1 way (choose nothing)
        for (int i = 0; i <= n; i++) {
            dp[0][i] = 1;
        }

        for (int x = 1; x <= amount; x++) {
            for (int i = 1; i <= n; i++) {

                // Exclude current coin
                dp[x][i] = dp[x][i - 1];

                // Include current coin (unlimited)
                // Current amount >= currnt coin value
                if (x >= coins[i - 1]) {
                    dp[x][i] = dp[x][i]+dp[x - coins[i - 1]][i];
                }
            }
        }

        return dp[amount][n];
    }

    public static void main(String[] args) {
        int[] coins = {1, 2, 5};
        int amount = 5;
        System.out.println(getWays(coins, amount)); // 4
    }
}
