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
 * i.e. We know the first day of flooding is day 1 and the outermost is cells.length i.e. each cell is a day.
 * 
 * So:  WE find a value between [1...cells.length] and find all the land cells i.e. the only possible starting points.
 *  We flood the grid until day d between [1...cells.length], and do a BFS to find out whether it's possible to reach the
 * bottom row (first day cells), cells are 1 index based we convert them to 0 based.
 * 
 * 
 * MENTAL MODEL:
 * 
 * 
 * 
 * 1. At first the entire grid given by row and col is land. 
 * 2. The cells in the days array give you the co-ordinates of the flooding cells ordered day to day.
 * 3. In java, doing a int[][] grid=new int[][]; effectively creates an all land grid where each value is initialized
 *    to 0. 
 * 4. We traverse days arrays from 0 to day-1 and take those cells and flood them in the grid. But first we reset them
 *    from 1 based to 0 index based.
 * 5. We then do a BFS from the top row adding all the cells that are land and expand out to see if we can reach the
 *    last cell i.e. row-1,col-1. If we can't, we can't reach the target on that day i.e.
 * 
 *      - On this day from 0 to day-1, flood all the cells from an all land grid
 *      - try to reach the target via BFS, if we can't, we try a smaller day
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
        int left = 1;
        int right = cells.length;  //last day of flooding
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
         // Note that the grid cells not visited will remain as 0 since Java initializes them to 0. So initlally cells are land
         // we flood a few in the list.
        int[][] grid = new int[row][col]; 

        // Mark flooded cells up to current day, iterate over the cells and change the indexing and
        // flood the corresponding positions on the grid.We only look at the cells we need to Flood.
        for (int i = 0; i < day; i++) {
            // flood it row by row
            int r = cells[i][0]-1; // convert to 0-indexed
            int c = cells[i][1]-1;
            grid[r][c] = 1; // water
        }

       
        Queue<int[]> q = new ArrayDeque<>();
        boolean[][] visited = new boolean[row][col];

        // Start from all land cells in the top row
        for (int c = 0; c < col; c++) {
            if (grid[0][c] == 0) {  // If it's land
                q.offer(new int[]{0,c});
                visited[0][c] = true;
            }
        }

        while (!q.isEmpty()) {
            int[] cell = q.poll();
            int r = cell[0];
            int cc = cell[1];

            if (r == row - 1) return true; // reached bottom

            for (int[] d : DIRS) {
                int nr = r + d[0];
                int nc = cc + d[1];

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
