package Graph;

import java.util.*;

/**
 * PROBLEM: Making a Large Island with up to K Flips
 * DOMAIN: Graph / 2D Grid
 * STRATEGY:
 * 1. Label each connected component (island) with a unique ID.
 * 2. Store island sizes in a map.
 * 3. Collect all 0 positions (water cells).
 * 4. Enumerate all subsets of 0s of size <= K.
 * 5. For each subset, temporarily flip the zeros, merge connected islands using Union-Find.
 * 6. Compute total area of connected components.
 * 7. Track maximum area across all subsets.
 *
 * TIME COMPLEXITY:
 *  - O((#zeros choose K) * N^2) → feasible only for small K
 * SPACE COMPLEXITY:
 *  - O(N^2) for island IDs and union-find
 */
public class LargestIslandKFlips {

    private int n;

    public int largestIslandKFlips(int[][] grid, int K) {
        this.n = grid.length;
        Map<Integer, Integer> islandSizes = new HashMap<>();
        int islandId = 2; // Start from 2 since 0/1 used in grid

        // Step 1: Label islands and compute sizes
        for (int r = 0; r < n; r++) {
            for (int c = 0; c < n; c++) {
                if (grid[r][c] == 1) {
                    int size = dfs(grid, r, c, islandId);
                    islandSizes.put(islandId, size);
                    islandId++;
                }
            }
        }

        // Step 2: Collect all zero positions
        List<int[]> zeros = new ArrayList<>();
        for (int r = 0; r < n; r++)
            for (int c = 0; c < n; c++)
                if (grid[r][c] == 0)
                    zeros.add(new int[]{r, c});

        // Step 3: Enumerate all subsets of zeros up to size K
        int maxArea = 0;
        int Z = zeros.size();
        int[] zeroIndices = new int[Z];
        for (int i = 0; i < Z; i++) zeroIndices[i] = i;

        // Enumerate subsets using bitmask for small K
        for (int mask = 1; mask < (1 << Z); mask++) {
            if (Integer.bitCount(mask) > K) continue;

            // Prepare union-find
            UnionFind uf = new UnionFind(n * n);
            // Initialize islands
            for (int r = 0; r < n; r++)
                for (int c = 0; c < n; c++)
                    if (grid[r][c] > 1)
                        uf.setSize(r * n + c, islandSizes.get(grid[r][c]));

            // Temporarily flip zeros in this subset
            for (int i = 0; i < Z; i++) {
                if ((mask & (1 << i)) != 0) {
                    int r = zeros.get(i)[0];
                    int c = zeros.get(i)[1];
                    int id = r * n + c;
                    uf.setSize(id, 1); // new single-cell island

                    // Merge with neighbors if they are islands
                    int[][] dirs = {{-1,0},{1,0},{0,-1},{0,1}};
                    for (int[] d : dirs) {
                        int nr = r + d[0], nc = c + d[1];
                        if (nr >= 0 && nr < n && nc >= 0 && nc < n && (grid[nr][nc] > 1 || ((mask & (1 << zeros.indexOf(new int[]{nr,nc}))) != 0))) {
                            uf.union(id, nr * n + nc);
                        }
                    }
                }
            }

            // Compute max area in this configuration
            maxArea = Math.max(maxArea, uf.maxSize());
        }

        // Edge case: if grid is full of 1s already
        if (maxArea == 0) maxArea = n * n;

        return maxArea;
    }

    private int dfs(int[][] grid, int r, int c, int id) {
        if (r < 0 || r >= n || c < 0 || c >= n || grid[r][c] != 1) return 0;
        grid[r][c] = id;
        return 1 + dfs(grid, r-1, c, id) +
                   dfs(grid, r+1, c, id) +
                   dfs(grid, r, c-1, id) +
                   dfs(grid, r, c+1, id);
    }

    // Union-Find with component size tracking
    static class UnionFind {
        int[] parent, size;
        int n;

        UnionFind(int n) {
            this.n = n;
            parent = new int[n];
            size = new int[n];
            for (int i = 0; i < n; i++) {
                parent[i] = i;
            }
        }

        int find(int x) {
            if (parent[x] != x) {
                parent[x] = find(parent[x]);
            }
            return parent[x];
        }

        void union(int x, int y) {
            int px = find(x), py = find(y);
            if (px == py) return;
            parent[py] = px;
            size[px] += size[py];
        }

        void setSize(int x, int s) {
            size[x] = s;
        }

        int maxSize() {
            int max = 0;
            for (int s : size){
              max = Math.max(max, s);
            } 
            return max;
        }
    }

    public static void main(String[] args) {
        LargestIslandKFlips sol = new LargestIslandKFlips();
        int[][] grid = {{1,0,0},{0,1,0},{0,0,1}};
        int K = 2;
        System.out.println("Max Area with K flips: " + sol.largestIslandKFlips(grid, K)); // Example output
    }
}
