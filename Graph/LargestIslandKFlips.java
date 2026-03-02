package Graph;

import java.util.*;

/**
 * ============================================================
 * PROBLEM STATEMENT: Making a Large Island with up to K Flips
 * ============================================================
 *
 * You are given an n x n binary grid where:
 *   - 1 represents land
 *   - 0 represents water
 *
 * An island is a group of 1s connected 4-directionally.
 *
 * You may flip at most K water cells (0 → 1).
 * After performing up to K flips, return the maximum possible
 * area of an island.
 *
 * ------------------------------------------------------------
 * IMPORTANT NOTES (INTERVIEW REALITY CHECK):
 * ------------------------------------------------------------
 * - When K = 1, there is a well-known O(n^2) solution.
 * - When K > 1 (general case), an optimal solution is NOT
 *   polynomial unless K is small.
 * - This implementation assumes K is SMALL (e.g., ≤ 5–7),
 *   which is a standard and acceptable interview constraint.
 *
 * ============================================================
 * CORE IDEA / REASONING
 * ============================================================
 *
 * Instead of enumerating all subsets of K water cells (which is
 * exponential), we reframe the problem:
 *
 *   Flipping K water cells means expanding an island through
 *   at most K layers of water.
 *
 * So the problem becomes:
 *
 *   For each island:
 *     - Perform a BFS expansion into water
 *     - Allow at most K water cells to be crossed
 *     - Collect all distinct islands reached
 *
 * The total area is:
 *
 *   sum(size of all connected islands) + number of flips used
 *
 * This works because:
 * - BFS naturally explores connected regions
 * - The number of flips is monotonic (never decreases)
 * - We never double-count island areas
 *
 * ============================================================
 * TIME & SPACE COMPLEXITY
 * ============================================================
 *
 * Let n = grid size
 *
 * - Island labeling:     O(n^2)
 * - BFS per island:      O(n^2 * K)
 * - Total:               O(n^2 * K)
 *
 * Space:
 * - Visited states:      O(n^2 * K)
 *
 * This is efficient and acceptable for SMALL K.
 *
 * ============================================================
 */
public class LargestIslandKFlips {

    private static final int[][] DIRS = {{1,0},{-1,0},{0,1},{0,-1}};
    private int n;

    /**
     * Main entry method.
     * Labels islands first, then attempts BFS expansion
     * from each island using up to K flips.
     */
    public int largestIsland(int[][] grid, int K) {
        n = grid.length;

        // Map: islandId -> islandSize
        Map<Integer, Integer> islandSize = new HashMap<>();

        // Island IDs start from 2 (0 and 1 already used)
        int islandId = 2;

        // --------------------------------------------------
        // STEP 1: Label all islands with unique IDs
        // --------------------------------------------------
        for (int r = 0; r < n; r++) {
            for (int c = 0; c < n; c++) {
                if (grid[r][c] == 1) {
                    int size = dfsLabel(grid, r, c, islandId);
                    islandSize.put(islandId, size);
                    islandId++;
                }
            }
        }

        int maxArea = 0;

        // --------------------------------------------------
        // STEP 2: Try expanding each island using BFS
        // --------------------------------------------------
        for (int id : islandSize.keySet()) {
            maxArea = Math.max(
                maxArea,
                bfsExpand(grid, id, islandSize, K)
            );
        }

        return maxArea;
    }

    /**
     * BFS expansion from a given island.
     *
     * State = (row, col, flipsUsed)
     *
     * We allow crossing water cells (0s) up to K times.
     * Every time we touch a new island, we add its size once.
     */
    private int bfsExpand(int[][] grid,
                          int startIslandId,
                          Map<Integer,Integer> islandSize,
                          int K) {

        // BFS queue holds: row, col, flipsUsed
        Queue<int[]> q = new ArrayDeque<>();

        // visited[r][c][k] means:
        // have we visited cell (r,c) using exactly k flips?
        boolean[][][] visited = new boolean[n][n][K + 1];

        // To avoid double-counting island areas
        Set<Integer> connectedIslands = new HashSet<>();

        // Start with the original island's area
        int area = islandSize.get(startIslandId);

        // --------------------------------------------------
        // Multi-source BFS:
        // enqueue ALL cells belonging to this island. From each frontier cell,we'll attempt to move k steps
        // 
        // --------------------------------------------------
        for (int r = 0; r < n; r++) {
            for (int c = 0; c < n; c++) {
                if (grid[r][c] == startIslandId) {
                    q.offer(new int[]{r, c, 0});
                    visited[r][c][0] = true;
                }
            }
        }

        // --------------------------------------------------
        // BFS expansion
        // --------------------------------------------------
        while (!q.isEmpty()) {
            int[] cur = q.poll();
            int r = cur[0], c = cur[1], used = cur[2];

            for (int[] d : DIRS) {
                int nr = r + d[0];
                int nc = c + d[1];

                // Bounds check
                if (nr < 0 || nr >= n || nc < 0 || nc >= n) continue;

                // If next cell is water, we consume a flip
                int nextUsed = used + (grid[nr][nc] == 0 ? 1 : 0);

                // Exceeded flip limit or already visited, allows us to move from each frontier K steps 
                if (nextUsed > K || visited[nr][nc][nextUsed]) continue;

                visited[nr][nc][nextUsed] = true;

                // If we reached a NEW island, add its size once
                if (grid[nr][nc] > 1 && !connectedIslands.contains(grid[nr][nc])) {
                    connectedIslands.add(grid[nr][nc]);
                    area += islandSize.get(grid[nr][nc]);
                }

                q.offer(new int[]{nr, nc, nextUsed});
            }
        }

        // All flips contribute 1 cell each
        return area + K;
    }

    /**
     * DFS used only for labeling islands and computing size.
     */
    private int dfsLabel(int[][] grid, int r, int c, int id) {
        if (r < 0 || r >= n || c < 0 || c >= n || grid[r][c] != 1) {
            return 0;
        }

        grid[r][c] = id;
        int size = 1;

        for (int[] d : DIRS) {
            size += dfsLabel(grid, r + d[0], c + d[1], id);
        }

        return size;
    }

    /**
     * Driver code
     */
    public static void main(String[] args) {
        LargestIslandKFlips sol = new LargestIslandKFlips();

        int[][] grid = {
            {1,0,0},
            {0,1,0},
            {0,0,1}
        };

        int K = 2;

        System.out.println(
            "Maximum island area with K flips: " +
            sol.largestIsland(grid, K)
        );
    }
}