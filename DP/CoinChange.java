package DP;

/**
 * Coin Change (Minimum Coins) - DP Breakdown
 *
 * Goal:
 *   Find the minimum number of coins needed to make a given amount.
 *
 * State:
 *   dp[x] = minimum coins needed to make amount x
 *
 * Choice / Decision:
 * For the current amount x, iterate over the coins and find the min. 
 *  Do we take the current coin or not? 
 * 
 *   At amount x, try every coin c such that c <= x.
 *   Take 1 coin c and add the solution for dp[x - c].
 * 
 *   i.e. we take the current coin and then add it to the number of coins to represent the balance
 *    i.e. x-c
 *
 * Recurrence:
 *   dp[x] = min(dp[x - c] + 1) for all c in coins where x - c >= 0
 * 
 * dp[x-c] is basically, the number of coins it takes to form an amount x-c
 * 
 * where c is the current coin value.
 *
 * Base Case:
 *   dp[0] = 0  // zero coins needed to make amount 0
 *
 * Greedy Check:
 *   ❌ Cannot use greedy (largest coin first fails in some cases).
 */
public class CoinChange {

    public static int coinChange(int[] coins, int amount) {
        int INF = Integer.MAX_VALUE; // use as infinity
        int[] dp = new int[amount + 1];
        // Initialize dp array with "infinity", INF is used a  place holder state for cases wehre
        // WE need to do minimization.
        for (int i = 1; i <= amount; i++) {
            dp[i] =INF;
        }
        dp[0] = 0; // base case

        // Bottom-up DP
        for (int x = 1; x <= amount; x++) {
            // find if any coin can satisfy th current amount 
            for (int coin : coins) {
                if (x - coin >= 0) {  // currentCoinValue <=x
                    dp[x] = Math.min(dp[x], dp[x - coin] + 1);
                }
            }
        }

        return dp[amount] == INF ? -1 : dp[amount];
    }

    public static void main(String[] args) {
        int[] coins = {1, 3, 4};
        int amount = 6;
        System.out.println("Minimum coins needed: " + coinChange(coins, amount)); 
        // Output: 2 (using coins 3+3 or 2+4 depending on approach)
    }
}
