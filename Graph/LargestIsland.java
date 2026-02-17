package Graph;

/**
 * PROBLEM: Making A Large Island
 * DOMAIN: Graph (2D Grid)
 * STRATEGY: 
 * 1. Label each connected component of 1s with a unique ID (Island ID).
 * 2. DFS and find all islands and store their id and size in a hashmap.
 *     Store the size of each Island ID in a Map.Do 
 * 3. Iterate through each 0 (water) and check its 4 neighbors.
 * 4. Sum the sizes of unique neighboring islands + 1 (for the flipped cell).
 * TIME COMPLEXITY: O(N * M) - One pass to label, one pass to check 0s.
 * SPACE COMPLEXITY: O(N * M) - To store island IDs.
 */
import java.util.*;

public class LargestIsland {
    private int n;

    public int largestIsland(int[][] grid) {
        this.n = grid.length;
        Map<Integer, Integer> islandSizes = new HashMap<>();
        int islandId = 2; // Start from 2 because 0 and 1 are already used in grid
        int maxArea = 0;

        // Step 1: Label islands and calculate sizes
        for (int r = 0; r < n; r++) {
            for (int c = 0; c < n; c++) {
                if (grid[r][c] == 1) {
                    int size = dfs(grid, r, c, islandId);
                    islandSizes.put(islandId, size);
                    // We keep track of maxArea for island here because a single island could be larger than any
                    // combination of the smaller.
                    maxArea = Math.max(maxArea, size); // Handle case with no 0s
                    islandId++;
                }
            }
        }

        // Step 2: Try flipping each 0
        for (int r = 0; r < n; r++) {
            for (int c = 0; c < n; c++) {
                if (grid[r][c] == 0) {
                    Set<Integer> neighborIslands = new HashSet<>();
                    // Check 4 directions and get all the neighbours, we modifid the grid insite to reflect the island_id.
                    if (r > 0) neighborIslands.add(grid[r - 1][c]);
                    if (r < n - 1) neighborIslands.add(grid[r + 1][c]);
                    if (c > 0) neighborIslands.add(grid[r][c - 1]);
                    if (c < n - 1) neighborIslands.add(grid[r][c + 1]);

                    int currentArea = 1; // The flipped 0 itself the area is 1 unit.
                    for (int id : neighborIslands) {
                        if (id > 1) { // Only count actual island IDs
                            currentArea += islandSizes.get(id);
                        }
                    }
                    maxArea = Math.max(maxArea, currentArea);
                }
            }
        }

        return maxArea;
    }

    private int dfs(int[][] grid, int r, int c, int id) {
        if (r < 0 || r >= n || c < 0 || c >= n || grid[r][c] != 1) {
            return 0;
        }
        grid[r][c] = id; // Label the cell
        // notice how we calculate the area
        return 1 + dfs(grid, r - 1, c, id) +
                   dfs(grid, r + 1, c, id) +
                   dfs(grid, r, c - 1, id) +
                   dfs(grid, r, c + 1, id);
    }

    public static void main(String[] args) {
        LargestIsland sol = new LargestIsland();
        int[][] grid = {{1, 0}, {0, 1}};
        System.out.println("Max Area: " + sol.largestIsland(grid)); // Output: 3
    }
}