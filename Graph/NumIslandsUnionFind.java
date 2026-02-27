package Graph;
/**Here is why you would argue for Union-Find over DFS:
 * 1. Handling Dynamic Data (Streaming)
 *      This is the strongest argument.
 *      
 *      DFS: If a single pixel changes from '0' to '1', you have to re-run the entire traversal O(R.C)) 
 *      to find the new island count.
 * 
 *     Union-Find: You only process the 4 neighbors of that specific pixel. 
 *     The update is O(alpha(N)), effectively constant time.
 *     Senior Signal: "I prefer Union-Find here because it supports incremental updates. 
 *     In a system where grid data is arriving in a stream or users are clicking  to 'add land,' we don't need to recompute
 *     the entire state."
 * 
 * 2. Parallelization and MapReduce
 *     DFS: It is inherently sequential. You can't easily start a DFS in the top-left and another in the bottom-right 
 *     and "merge" them because the recursion stack doesn't communicate across threads.
 * 
 *     Union-Find: You can split the grid into 4 quadrants, have 4 threads calculate the islands in their own sectors, 
 *      and then union the boundary cells to merge the results.
 * 
 * 3. Memory Constraints:
 *     (Recursion Depth)DFS: In a very large grid (e.g., 10^5 x 10^5 with a long,
 *      snake-like island), a naive DFS will throw a StackOverflowError because the recursion depth is too high.
 * 
 *     Union-Find: It is iterative. It uses the heap for the parent array, which is only limited by your total RAM,
 *     not the thread's stack size. 
 * */
public class NumIslandsUnionFind {
        class UnionFind {
            int[] parent;
            int count; // Number of connected components

            UnionFind(int n) {
                parent = new int[n];
                for (int i = 0; i < n; i++) parent[i] = i;
                this.count = 0; 
            }

            int find(int i) {
                if (parent[i] == i) return i;
                return parent[i] = find(parent[i]); // Path compression
            }

            void union(int i, int j) {
                int rootI = find(i);
                int rootJ = find(j);
                if (rootI != rootJ) {
                    parent[rootI] = rootJ;
                    count--; // Merged two islands
                }
            }
            
            void setInitialCount(int totalLand) {
                this.count = totalLand;
            }
        }

        public int numIslands(char[][] grid) {
            if (grid == null || grid.length == 0) return 0;

            int rows = grid.length;
            int cols = grid[0].length;
            UnionFind uf = new UnionFind(rows * cols);
            
            int landCount = 0;
            for (int r = 0; r < rows; r++) {
                for (int c = 0; c < cols; c++) {
                    if (grid[r][c] == '1') landCount++;
                }
            }
            uf.setInitialCount(landCount);

            for (int r = 0; r < rows; r++) {
                for (int c = 0; c < cols; c++) {
                    if (grid[r][c] == '1') {
                        // Only need to check Right and Down to avoid redundant unions
                        int[][] neighbors = {{r + 1, c}, {r, c + 1}};
                        
                        for (int[] next : neighbors) {
                            int nr = next[0], nc = next[1];
                            if (nr < rows && nc < cols && grid[nr][nc] == '1') {
                                // The Bijection: (r, c) -> 1D index
                                int id1 = r * cols + c;
                                int id2 = nr * cols + nc;
                                uf.union(id1, id2);
                            }
                        }
                    }
                }
            }

            return uf.count;
    }
    
}
