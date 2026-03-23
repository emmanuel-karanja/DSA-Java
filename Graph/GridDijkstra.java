package Graph;

import java.util.*;

public class GridDijkstra {
    public static int minCost(int[][] grid) {
        int rows = grid.length;
        int cols = grid[0].length;
        
        // Directions: right, left, down, up
        int[][] dirs = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};
        
        // Distance array: store min cost to reach each cell
        int[][] dist = new int[rows][cols];
        for (int[] row : dist) {
            Arrays.fill(row, Integer.MAX_VALUE);
        }
        dist[0][0] = grid[0][0]; // starting cell cost
        
        // Priority queue: {row, col, currentDistance}
        PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a[2]));
        pq.offer(new int[]{0, 0, dist[0][0]});
        
        while (!pq.isEmpty()) {
            int[] curr = pq.poll();
            int r = curr[0], c = curr[1], d = curr[2];
            
            // Stale entry guard
            if (d > dist[r][c]) continue;
            
            for (int[] dir : dirs) {
                int nr = r + dir[0], nc = c + dir[1];
                if (nr >= 0 && nr < rows && nc >= 0 && nc < cols) {
                    int newDist = d + grid[nr][nc];
                    if (newDist < dist[nr][nc]) {
                        dist[nr][nc] = newDist;
                        pq.offer(new int[]{nr, nc, newDist});
                    }
                }
            }
        }
        
        // Return min cost to reach bottom-right cell
        return dist[rows - 1][cols - 1];
    }
    
    public static void main(String[] args) {
        int[][] grid = {
            {1, 3, 1},
            {1, 5, 1},
            {4, 2, 1}
        };
        
        System.out.println("Minimum cost: " + minCost(grid)); // Output: 7
    }
}