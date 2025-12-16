package DP;
/**What does dp[x] mean? -->minimum number of coins required to form the amount x 
 * 
 * GOAL: Get the minimum number of coins
 * State: (subproblem statement) we get the minimal count for amount less than the current amount.
 *        What is dp[x]? minimum number of couns required to represent amount x
 * Choices: Pick the next coin whose value >=remainingAmount. How to pick the optimal sub-solution from the solution space.
 * Recurrence Relation:
 *      dp[x]=Min(dp[x],1+dp[x-coinValue])
 * Base Case: when amount < lowestCoin value dp[0]=0
 *  
*/
import java.util.Arrays;

public class CoinChange {

    public int coinChange(int[] coins, int amount) {

    int INF = amount + 1;  //when it's impossible to represent the amount in coins.
    int[] dp = new int[amount + 1];
    Arrays.fill(dp, INF);

    dp[0] = 0; 

    for (int x = 1; x <= amount; x++) {
        for (int coin : coins) {
            if (x - coin >= 0) {
                dp[x] = Math.min(dp[x], dp[x - coin] + 1);
            }
        }
    }

    return dp[amount] == INF ? -1 : dp[amount];
}

}
