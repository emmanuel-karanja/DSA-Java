package DP;

/**
 * DP BREAKDOWN: Max Sum with One-Time Adjacent "Cheat"
 *
 * 1. GOAL:
 *    Maximize the sum of selected numbers with the constraint that
 *    no two selected numbers are adjacent, except for a one-time
 *    “cheat” that allows picking an adjacent pair once.
 *
 * 2. STATE:
 *    dp[i][j] 
 *    - i: index in nums
 *    - j: binary state
 *        0 -> No cheat used yet
 *        1 -> Cheat already used or used now
 *
 * 3. CHOICES:
 *    If j = 0 (No Cheat Yet):
 *      a) Skip nums[i]: dp[i-1][0]
 *      b) Take nums[i]: nums[i] + dp[i-2][0]
 *
 *    If j = 1 (Cheat Used / Using Cheat Now):
 *      a) Skip nums[i]: dp[i-1][1]
 *      b) Take nums[i] alone: nums[i] + dp[i-2][1]
 *      c) Cheat now (take nums[i] and nums[i-1] together): nums[i] + nums[i-1] + dp[i-3][0]
 *
 * 4. EVALUATION ORDER:
 *    Forward from index 0 to n-1, because each state depends on i-1, i-2, i-3.
 *
 * 5. SPACE OPTIMIZATION:
 *    Only store the last 2 states for j=0 and last 3 states for j=1,
 *    since the recurrence only looks back at most 3 steps.
 *
 * 6. DIAGRAM EXAMPLE (nums = [3, 2, 5, 10]):
 *
 *    Index i:    0    1    2     3
 *    nums[i]:    3    2    5    10
 *
 *    j=0 (no cheat):
 *    dp[0][0] = 3
 *    dp[1][0] = max(3, 2) = 3
 *    dp[2][0] = max(3, 5+3) = 8
 *    dp[3][0] = max(8, 10+3) = 13
 *
 *    j=1 (cheat allowed/used):
 *    dp[0][1] = 3
 *    dp[1][1] = max(dp[0][1], nums[1]+dp[-1][1], nums[1]+nums[0]+dp[-2][0])
 *               = max(3, 2+0, 2+3+0) = 5  // cheat now
 *    dp[2][1] = max(dp[1][1], nums[2]+dp[0][1], nums[2]+nums[1]+dp[-1][0])
 *               = max(5, 5+3, 5+2+0) = 8
 *    dp[3][1] = max(dp[2][1], nums[3]+dp[1][1], nums[3]+nums[2]+dp[0][0])
 *               = max(8, 10+5, 10+5+3) = 18
 *
 *    Final Answer: max(dp[n-1][0], dp[n-1][1]) = max(13, 18) = 18
 */
public class MaxSumWithTwist {

    public int getMaxSumWithOneCheat(int[] nums) {
        if (nums == null || nums.length == 0) return 0;
        int n = nums.length;
        if (n == 1) return nums[0];
        if (n == 2) return nums[0] + nums[1]; // Take both if cheat helps

        // ------------------------
        // j=0: No cheat used yet
        // Need only 2 steps of history
        // ------------------------
        int prevNoCheat2 = 0;
        int prevNoCheat1 = 0;

        // ------------------------
        // j=1: Cheat path
        // Need 3 steps of history (i-3) for "cheat now"
        // ------------------------
        int prevCheat3 = 0; // dp[i-3][0]
        int prevCheat2 = 0; // dp[i-2][1]
        int prevCheat1 = 0; // dp[i-1][1]

        for (int i = 0; i < n; i++) {

            // --- Calculate j=0 (No cheat yet) ---
            int currentNoCheat = Math.max(prevNoCheat1, nums[i] + prevNoCheat2);

            // --- Calculate j=1 (Cheat path) ---
            // Option 1: Already cheated
            int optionAlreadyCheated = Math.max(prevCheat1, nums[i] + prevCheat2);

            // Option 2: Cheat NOW (take nums[i] + nums[i-1])
            int optionCheatNow = 0;
            if (i > 0) {
                optionCheatNow = nums[i] + nums[i-1] + prevCheat3;
            }

            int currentCheated = Math.max(optionAlreadyCheated, optionCheatNow);

            // --- Shift history variables ---
            prevCheat3 = prevNoCheat2; // dp[i-3][0] for next iteration
            prevCheat2 = prevCheat1;
            prevCheat1 = currentCheated;

            prevNoCheat2 = prevNoCheat1;
            prevNoCheat1 = currentNoCheat;
        }

        return Math.max(prevNoCheat1, prevCheat1);
    }
}
