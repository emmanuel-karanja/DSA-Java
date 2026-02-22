package BitmaskDP;

import java.util.Arrays;

/**
 * PROBLEM: Max Accepted Invitations (Matching)
 * * LOGIC:
 * 1. STATE: dp[mask] is the max invitations using a set of tables 'mask'.
 * 2. TRANSITION: For each invitee 'i', we update the DP table.
 * 3. Note: We iterate backwards through masks to use a 1D array (like Knapsack).
 * 
 * Maximum Number of Accepted InvitationsThe Scenario: You have M invitees and N tables.
 * Each invitee has a list of tables they are willing to sit at. Each table can only hold one person. 
 * Find the max invitations accepted.
 * 
 * 1. The Generative State
 *      Independent Variable: The invitee index i.
 *      The Mask: Which tables are already occupied.
 *      State: dp[i][mask] = Max invitations for first i invitees given table occupancy mask.
 * 
 * 2. The Transitions
 *   The "Skip" Case: Invitee i does not get a table.
 *     dp[i][mask] = dp[i-1][mask]
 * 
 *   The "Take" Case: Invitee i takes an available, acceptable table j.
 * 
 *   Guard: ((mask & (1 << j)) == 0) AND canSit(i, j).dp[i][mask | (1 << j)] = max(current, 1 + dp[i-1][mask])
 */
public class MaxInvitations {
 
    public int solve(int[][] grid) {
        int m = grid.length;    // Number of Invitees
        int n = grid[0].length; // Number of Tables
        
        // dp[i][mask] = max invitations using a subset of first 'i' invitees 
        // with 'mask' representing occupied tables.
        int[][] dp = new int[m + 1][1 << n];

        // Initialize with -1 to represent unreachable or uncalculated states
        for (int[] row : dp) Arrays.fill(row, -1);
        
        // Base Case: 0 invitees, 0 tables occupied, 0 invitations
        dp[0][0] = 0;

        // Generative Flow: Iterate through each invitee 'i'
        for (int i = 0; i < m; i++) {
            for (int mask = 0; mask < (1 << n); mask++) {
                if (dp[i][mask] == -1) continue; // Skip states we haven't reached

                // --- CHOICE 1: SKIP ---
                // We don't seat invitee 'i' at any table. 
                // The occupancy (mask) stays the same for the next invitee.
                dp[i + 1][mask] = Math.max(dp[i + 1][mask], dp[i][mask]);

                // --- CHOICE 2: TAKE ---
                // Try to seat invitee 'i' at every possible table 'j'.
                for (int j = 0; j < n; j++) {
                    // Check if: 
                    // 1. Invitee i is willing to sit at table j (grid[i][j] == 1)
                    // 2. Table j is currently empty (j-th bit in mask is 0)
                    if (grid[i][j] == 1 && (mask & (1 << j)) == 0) {
                        int nextMask = mask | (1 << j);
                        dp[i + 1][nextMask] = Math.max(dp[i + 1][nextMask], dp[i][mask] + 1);
                    }
                }
            }
        }

        // The result is the maximum value in the final row (after all invitees processed)
        int result = 0;
        for (int invitations : dp[m]) {
            result = Math.max(result, invitations);
        }
        return result;
    }

    public static void main(String[] args) {
        MaxInvitations solver = new MaxInvitations();
        
        // Example: 3 invitees, 3 tables
        // Invitee 0 likes Table 0, 1
        // Invitee 1 likes Table 0
        // Invitee 2 likes Table 1, 2
        int[][] grid = {
            {1, 1, 0},
            {1, 0, 0},
            {0, 1, 1}
        };
        
        System.out.println("Max Invitations: " + solver.solve(grid)); 
        // Result should be 3: (Inv 1->T0, Inv 0->T1, Inv 2->T2)
    }
}
