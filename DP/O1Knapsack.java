package DP;
/**You have a list of items and each item has an associated weight and value profit. You've a knapsack of limited capacity C,
 * find how many items you can put in to maximize the profit. Each item can only be picked once. i.e. either we pick it
 * or skip it.
 * 
 * INTUTION
 * Incase you are from another planet, this is one of the most common DP problems.
 * 
 * 1D array
 * 
 * Decision: if the current item wi>=remainingWeight, we take the profitValue and add it, 
 * if not,we skip the item in dp, we simply take the profit of the previous item i.e.  
 */
public class O1Knapsack {

    public static int getMaxProfit(int[] weights, int[] values, int W){

        final int n=weights.length;
        int[][] dp= new int[n+1][W+1];// no need to fill since in java by default, they are filled in with 0

        for(int i=1;i<=n;i++){
            for(int w=1;w<=W;w++){
                //if the current item's weight is greater than remaining weight skp
                if(weights[i] > w){
                    dp[i][w]=dp[i-1][w];// pick the profit for the i-1th item
                }else{
                    //yield and skip case
                    dp[i][w]=Math.max(dp[i-1][w], values[i]+dp[i-1][w-weights[i]]);
                }
            }
        }

        return dp[n][W];
    }
    
}
