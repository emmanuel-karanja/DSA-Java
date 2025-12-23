package BinarySearch;

/**
 * PROBLEM: Last Day to Cross
 * 
 * You are given a 2D grid of land (1) and water (0). 
 * Every day, the water level rises and turns one specific land cell into water. 
 * You are given the list `cells` which indicates the order of cells that turn into water:
 *     cells[i] = [row, col]  => cell at (row, col) becomes water on day i+1.
 * 
 * Task: Determine the last day you can start at the top row and cross to the bottom row
 *       moving only through land (1s) and in 4 directions (up/down/left/right) 
 *       before the path is blocked by water.
 * 
 * EXAMPLE:
 * Grid: 3x3, cells = [[1,1],[2,1],[3,1],[1,2],[2,2],[3,2],[1,3],[2,3],[3,3]]
 * Output: last day you can cross = 3
 * 
 * WHY THIS IS A BINARY SEARCH ON ANSWER (BSOA):
 * - Observation: If it is possible to cross on day D, it is also possible on any day < D.
 *   Conversely, if it is impossible to cross on day D, it will also be impossible on any day > D.
 * - This creates a **monotonic property** over days: possible → impossible.
 * - Monotonic property allows **binary search over the day number** to find the last day
 *   a path exists efficiently.
 * 
 * MODELING THE STATE (Using User Rubric):
 * 1. GOAL: Find the last day you can cross from top to bottom.
 * 2. TYPE: Binary Search on Answer + BFS (or DFS)
 * 3. INSIGHT:
 *    - Instead of simulating all days sequentially, use **binary search on days**.
 *    - For a given day, simulate flooded cells and check if a path exists from top to bottom.
 * 4. ALGORITHM:
 *    a. Binary search on day number:
 *       - mid = (lo + hi) / 2
 *       - Simulate flooded cells up to day `mid`
 *       - Use BFS/DFS to check if top-bottom path exists
 *       - If path exists, move lo = mid + 1; else hi = mid - 1
 *    b. Continue until last possible day is found.
 * 5. COMPLEXITY:
 *    - O(log(days) * R*C) where R,C = rows, columns
 */

import java.util.*;

public class LastDayToCross {

    private static final int[][] DIRS = {{1,0},{-1,0},{0,1},{0,-1}};

    public int latestDayToCross(int row, int col, int[][] cells) {
        int left = 1, right = cells.length;
        int ans = 0;

        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (canCross(row, col, cells, mid)) {
                ans = mid;     // possible to cross on day mid
                left = mid + 1; 
            } else {
                right = mid - 1; // impossible to cross
            }
        }

        return ans;
    }

    private boolean canCross(int row, int col, int[][] cells, int day) {
        int[][] grid = new int[row][col];

        // Mark flooded cells up to current day
        for (int i = 0; i < day; i++) {
            int r = cells[i][0]-1; // convert to 0-indexed
            int c = cells[i][1]-1;
            grid[r][c] = 1; // water
        }

        Queue<int[]> q = new LinkedList<>();
        boolean[][] visited = new boolean[row][col];

        // Start from all land cells in the top row
        for (int c = 0; c < col; c++) {
            if (grid[0][c] == 0) {
                q.offer(new int[]{0,c});
                visited[0][c] = true;
            }
        }

        while (!q.isEmpty()) {
            int[] cell = q.poll();
            int r = cell[0], cc = cell[1];

            if (r == row - 1) return true; // reached bottom

            for (int[] d : DIRS) {
                int nr = r + d[0], nc = cc + d[1];
                if (nr >= 0 && nr < row && nc >= 0 && nc < col 
                    && !visited[nr][nc] && grid[nr][nc] == 0) {
                    visited[nr][nc] = true;
                    q.offer(new int[]{nr,nc});
                }
            }
        }

        return false;
    }

    public static void main(String[] args) {
        LastDayToCross solver = new LastDayToCross();
        int row = 3, col = 3;
        int[][] cells = {
            {1,1},{2,1},{3,1},{1,2},{2,2},{3,2},{1,3},{2,3},{3,3}
        };
        System.out.println("Last day to cross: " + solver.latestDayToCross(row, col, cells));
    }
}
