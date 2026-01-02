

public class BurstBalloons {

    /*
     * DP Reasoning for Burst Balloons:

       1) Goal: Maximize the profit.
     * 
     * 2) State:
     *    Clear statement of sub-problem. e.g. to get the maximum profit when bursting balloon k,
     *    we must ensure that we also get the max for all neighbours left of k and right of k, and k'sprofit
     * 
     *    dp[left][right] = maximum coins obtainable by bursting all balloons
     *    strictly between positions 'left' and 'right'.
     *    (We pad the nums array with 1 at both ends, so left/right can be boundaries.)
     * 
     * *PADDING: it's done so that every balloon has a neighbour and we use 1 because as a multiplier
     *  ,it has not effect.
     * 
     * 3) Choices:
     *    For a given interval (left, right), we choose a balloon 'k' to burst LAST
     *    in this interval. Bursting it last ensures its neighbors at that moment
     *    are exactly nums[left] and nums[right], which determines the coins gained.
     * 
     *  dp[left][right]=max(dp[left][right],dp[left][k]+nums[left]*nums[k]*nums[right]+dp[k][right])
     * 
     * *WE pick the last balloon since its neighbours will be known. If you pick the first balloon
     *  we can't know yet what its neighbours are and hence we cannot know the sub-solutions.
     * 
     * *STABLE SUB-PROBLEMS-->This problem touches on an important aspect of DP where the subproblems
     *  and sub-solutions need to be known and stable.
     * 
     * 4) Recurrence:
     *    dp[left][right] = max (dp[left][right],dp[left][k]+nums[left]*nums[k]*nums[right]+dp[k][right])
     *    i.e., coins from bursting left subinterval + coins from bursting k last + coins from right subinterval
     * 
     * 5) Base Case:
     *    If there are no balloons between left and right (right - left <= 1),
     *    dp[left][right] = 0, because there is nothing to burst.
     * 
     * This is an example of interval DP, similar to matrix chain multiplication.
     * 
     * Moral: Choosing the “last” action in interval DP often fixes the boundary conditions neatly. This is a classic trick used in:
          - Burst Balloons
          - Matrix Chain Multiplication
          - Optimal BST

       Rule of thumb for "last action"
          - Whenever the effect of an action depends on its remaining neighbors, 
            consider solving the subproblem by picking the last action.
          - This makes the subproblem self-contained and avoids tracking dynamic changes.
     */

    public static int maxCoins(int[] nums) {
        int n = nums.length;
        // pad nums with 1 on both ends
        int[] arr = new int[n + 2];
        arr[0] = 1;
        arr[n + 1] = 1;
        
        for (int i = 0; i < n; i++) {
            arr[i + 1] = nums[i];
        }

        int[][] dp = new int[n + 2][n + 2];

        // We begin at dp[0].length-2 to accomodate right and k
        for (int left = n; left >= 0; left--) {
            for (int right = left + 2; right <= n + 1; right++) { // interval length >= 2
                for (int k = left + 1; k < right; k++) {
                    dp[left][right] = Math.max(dp[left][right],
                            dp[left][k] + arr[left] * arr[k] * arr[right] + dp[k][right]);
                }
            }
        }

        return dp[0][n + 1];  // We need for the entire array from i to j.
    }

    public static void main(String[] args) {
        int[] nums = {3,1,5,8};
        System.out.println("Max coins: " + maxCoins(nums)); // Output: 167
    }
}
