import java.util.Arrays;

/**
 * PROBLEM: Maximum Weighted Invitations (2D Bitmask DP)
 *
 * SCENARIO:
 * - M invitees, N tables (N ≤ 20)
 * - Each invitee has a profit/cost for each table
 * - Each table can hold at most one invitee
 *
 * GOAL:
 * - Assign invitees to tables to maximize total profit
 *
 * DP STATE:
 * - dp[i][mask] = max profit using the first i invitees
 *   and the table assignment represented by 'mask'
 *
 * TRANSITION:
 * - Skip invitee i → dp[i+1][mask] = max(dp[i+1][mask], dp[i][mask])
 * - Assign invitee i to a free table j:
 *     if ((mask & (1 << j)) == 0):
 *         dp[i+1][mask | (1 << j)] = max(dp[i+1][mask | (1 << j)], dp[i][mask] + profit[i][j])
 *
 * COMPLEXITY:
 * - Time: O(M * N * 2^N)
 * - Space: O(M * 2^N)
 */
public class MaxWeightedInvitations {

    public int solve(int[][] profit) {
        int M= profit.length;    // invitees
        int N = profit[0].length; // tables
        int totalMask = 1 << N;

        // Initialize DP array
        int[][] dp = new int[M + 1][totalMask];
        for (int i = 0; i <= M; i++) {
            Arrays.fill(dp[i], Integer.MIN_VALUE);
        }
        dp[0][0] = 0; // base case: no invitees, no tables

        for (int i = 0; i < M; i++) { // for each invitee
            for (int mask = 0; mask < totalMask; mask++) {
                if (dp[i][mask] == Integer.MIN_VALUE) continue;

                // Option 1: skip invitee i
                dp[i + 1][mask] = Math.max(dp[i + 1][mask], dp[i][mask]);

                // Option 2: assign invitee i to any available table j
                for (int j = 0; j < N; j++) {
                    if (((mask >> j) & 1) == 0 && profit[i][j] > 0) {
                        int newMask = mask | (1 << j);
                        dp[i + 1][newMask] = Math.max(dp[i + 1][newMask], dp[i][mask] + profit[i][j]);
                    }
                }
            }
        }

        // Find maximum profit after all invitees processed
        int result = 0;
        for (int mask = 0; mask < totalMask; mask++) {
            result = Math.max(result, dp[M][mask]);
        }
        return result;
    }

    public static void main(String[] args) {
        MaxWeightedInvitations solver = new MaxWeightedInvitations();

        int[][] profit = {
            {10, 5, 0},
            {7, 0, 0},
            {0, 8, 9}
        };

        System.out.println("Max total profit: " + solver.solve(profit));
        // Expected Output: 26
        // Assignment: Invitee0->Table0, Invitee1->Table1, Invitee2->Table2
    }
}