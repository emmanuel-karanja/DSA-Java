package Graph;

import java.util.Arrays;

/**
 * PROBLEM: Maximum Accepted Invitations (Bipartite Matching)
 *
 * SCENARIO:
 * You have M invitees and N tables.
 * Each invitee has a list of tables they are willing to sit at.
 * Each table can hold only one person.
 *
 * GOAL:
 * Find the maximum number of invitees that can be assigned to tables
 * such that:
 *   - Each invitee sits at most one table
 *   - Each table is used by at most one invitee
 *   - Assignments respect preferences (grid[i][j] == 1)
 *
 * ------------------------------------------------------------
 * CORE MODEL:
 *
 * This is a BIPARTITE GRAPH:
 *
 *   Left Set  (U) → Invitees
 *   Right Set (V) → Tables
 *   Edge (i → j) exists if invitee i can sit at table j
 *
 * The problem reduces to:
 *
 *   → MAXIMUM BIPARTITE MATCHING
 *
 * ------------------------------------------------------------
 * APPROACH: AUGMENTING PATHS (KUHN'S ALGORITHM)
 *
 * We build the matching incrementally.
 *
 * DATA STRUCTURE:
 *   match[j] = which invitee is currently assigned to table j
 *              (-1 means table is free)
 *
 * ------------------------------------------------------------
 * KEY IDEA (THE HEART OF THE ALGORITHM):
 *
 * For each invitee 'i', try to assign them a table using DFS:
 *
 *   1. Try all preferred tables 'j'
 *
 *   2. If table j is FREE:
 *        → assign it to invitee i
 *
 *   3. If table j is OCCUPIED by invitee k:
 *        → try to REASSIGN k to another table (DFS)
 *
 *        If k can move:
 *            → free table j
 *            → assign j to i
 *
 * This chain of reassignments is called an:
 *
 *   → AUGMENTING PATH
 *
 * ------------------------------------------------------------
 * INTUITION:
 *
 * We are not just finding empty tables.
 *
 * We are asking:
 *   "Can I rearrange existing assignments to make room?"
 *
 * Example chain:
 *   A wants T1 (occupied by B)
 *   B moves to T2 (occupied by C)
 *   C moves to T3 (free)
 *
 * Result:
 *   Everyone gets a seat → matching size increases
 *
 * ------------------------------------------------------------
 * WHY visited[] IS NEEDED:
 *
 * Prevents infinite loops in DFS:
 *   A → T1 → B → T2 → A → ...
 *
 * So each table is tried at most once per DFS.
 *
 * ------------------------------------------------------------
 * COMPLEXITY:
 *
 * Time:  O(V * E)
 * Space: O(N)
 *
 * Much more efficient than Bitmask DP for larger inputs.
 *
 * ------------------------------------------------------------
 * CONTRAST WITH DP:
 *
 * Bitmask DP:
 *   - Explores ALL subsets of tables
 *
 * Kuhn:
 *   - Greedily builds matching
 *   - Fixes conflicts dynamically via DFS
 *
 * Both solve the same problem, but Kuhn scales better.
 */
public class MaxInvitationsKuhn {

    public int solve(int[][] grid) {
        int m = grid.length;    // number of invitees
        int n = grid[0].length; // number of tables

        int[] match = new int[n]; // match[j] = invitee assigned to table j
        Arrays.fill(match, -1);

        int result = 0;

        // Try to match each invitee
        for (int i = 0; i < m; i++) {
            boolean[] visited = new boolean[n]; // reset per DFS

            if (dfs(i, grid, match, visited)) {
                result++;
            }
        }

        return result;
    }

    private boolean dfs(int invitee, int[][] grid, int[] match, boolean[] visited) {
        int n = grid[0].length;

        for (int table = 0; table < n; table++) {

            // Skip if:
            // - invitee doesn't like this table
            // - already tried this table in this DFS
            if (grid[invitee][table] == 0 || visited[table]) continue;

            visited[table] = true;

            // Case 1: Table is free
            // Case 2: Table is occupied, but we can reassign the current owner
            if (match[table] == -1 || dfs(match[table], grid, match, visited)) {
                match[table] = invitee;
                return true;
            }
        }

        return false;
    }

    public static void main(String[] args) {
        MaxInvitationsKuhn solver = new MaxInvitationsKuhn();

        int[][] grid = {
            {1, 1, 0},
            {1, 0, 0},
            {0, 1, 1}
        };

        System.out.println("Max Invitations: " + solver.solve(grid));
        // Expected Output: 3
    }
}