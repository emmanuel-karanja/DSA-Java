package Graph;

import java.util.PriorityQueue;

/**
 * ============================================================
 * PROBLEM: TRAPPING RAIN WATER II (2D / 3D terrain)
 * ============================================================
 *
 * Given a 2D grid `heightMap` of size m x n, where heightMap[i][j] is the elevation at cell (i,j),
 * compute how much water can be trapped after raining. Water can only flow to the 4 neighboring cells.
 *
 * Edge Case:
 *  - If grid has less than 3 rows or less than 3 columns, no water can be trapped.
 *
 * APPROACH:
 * 1. Use a min-heap (priority queue) to always process the **lowest boundary cell first**.
 * 2. Initialize by pushing all **boundary cells** into the heap and marking them visited.
 * 3. While the heap is not empty:
 *    - Pop the cell with the **minimum height**.
 *    - Visit its 4 neighbors:
 *        a) If neighbor is lower than the boundary, water trapped = boundaryHeight - neighborHeight
 *        b) Push neighbor into heap with height = max(neighborHeight, boundaryHeight)
 *    - Mark neighbor visited
 * 4. Sum all trapped water for all cells.
 */
public class TrappingRainWater2D {

    static class Cell implements Comparable<Cell> {
        int x, y;
        int height;

        Cell(int x, int y, int height) {
            this.x = x;
            this.y = y;
            this.height = height;
        }

        @Override
        public int compareTo(Cell other) {
            return this.height - other.height; // Min-heap based on height
        }
    }

    public static int trapRainWater(int[][] heightMap) {
        if (heightMap == null || heightMap.length < 3 || heightMap[0].length < 3)
            return 0; // Edge case: too small to trap water

        int m = heightMap.length;
        int n = heightMap[0].length;

        boolean[][] visited = new boolean[m][n];
        PriorityQueue<Cell> heap = new PriorityQueue<>();

        // Step 1: Push all boundary cells into heap
        for (int i = 0; i < m; i++) {
            heap.offer(new Cell(i, 0, heightMap[i][0]));
            heap.offer(new Cell(i, n - 1, heightMap[i][n - 1]));
            visited[i][0] = true;
            visited[i][n - 1] = true;
        }
        for (int j = 1; j < n - 1; j++) {
            heap.offer(new Cell(0, j, heightMap[0][j]));
            heap.offer(new Cell(m - 1, j, heightMap[m - 1][j]));
            visited[0][j] = true;
            visited[m - 1][j] = true;
        }

        int trappedWater = 0;
       int[][] DIRS={{0,1},{1,0},{0,-1},{-1,0}};

        // Step 2: Process cells in min-heap order
        while (!heap.isEmpty()) {
            Cell cell = heap.poll();

            // Step 3: Visit neighbors
            for (int[] dir: DIRS) {
                int nx = cell.x + dir[0];
                int ny = cell.y + dir[1];

                if (nx >= 0 && nx < m && ny >= 0 && ny < n && !visited[nx][ny]) {
                    visited[nx][ny] = true;

                    int neighborHeight = heightMap[nx][ny];
                    // Water trapped if current boundary is taller
                    trappedWater += Math.max(0, cell.height - neighborHeight);

                    // Push neighbor with updated height = max(neighbor, boundary)
                    heap.offer(new Cell(nx, ny, Math.max(neighborHeight, cell.height)));
                }
            }
        }

        return trappedWater;
    }

    // =========================
    // Driver
    // =========================
    public static void main(String[] args) {
        int[][] heightMap1 = {
            {1, 4, 3, 1, 3, 2},
            {3, 2, 1, 3, 2, 4},
            {2, 3, 3, 2, 3, 1}
        };
        System.out.println("Trapped water: " + trapRainWater(heightMap1)); // Expected: 4

        int[][] heightMap2 = {
            {3, 3, 3, 3, 3},
            {3, 2, 1, 2, 3},
            {3, 3, 3, 3, 3}
        };
        System.out.println("Trapped water: " + trapRainWater(heightMap2)); // Expected: 

        int[][] heightMapSmall = {
            {1, 2},
            {3, 4}
        };
        System.out.println("Trapped water (small grid): " + trapRainWater(heightMapSmall)); // Expected: 0
    }
}