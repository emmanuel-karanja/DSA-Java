package Graph;

/**
 * PROBLEM: Last Day to Cross (Reverse Union-Find)
 * 
 * You are given a grid with rows x cols, initially all land (1).
 * Each day, a specific cell is flooded (becomes water, 0) in the order given by `cells`.
 * 
 * Task: Determine the **last day you can cross** from the top row to the bottom row,
 *       moving only through land (1s) and in 4 directions (up, down, left, right).
 * 
 * WHY REVERSE UNION-FIND:
 * - Forward simulation (flood day by day) would require BFS/DFS each day → slow.
 * - Reverse trick:
 *     1. Start with all water.
 *     2. Add land backwards from last day to first.
 *     3. Merge neighboring land cells using Union-Find.
 *     4. Check if top row connects to bottom row using virtual nodes.
 * 
 * Bijection-->Converts 2D co-ordinates to 1D co-ordinates(r*COLS+ c) is done since Union-Find works
 *            in 1D i.e.single node id
 */

public class LastDayToCrossReverseUnionFind {
    private static final int[][] DIRS = {{1,0},{-1,0},{0,1},{0,-1}};

    static class UnionFind {
        int[] parent, rank;
        public UnionFind(int n) {
            parent = new int[n];
            rank = new int[n];
            for (int i = 0; i < n; i++) parent[i] = i;
        }

        public int find(int x) {
            if (parent[x] != x) {
                parent[x] = find(parent[x]);
            }
            return parent[x];
        }

        public void union(int x, int y) {
            int px = find(x), py = find(y);
            if (px == py) return;
            if (rank[px] < rank[py]) 
                parent[px] = py;
            else if (rank[px] > rank[py]) 
                parent[py] = px;
            else {
                parent[py] = px;
                rank[px]++;
            }
        }

        
        public boolean connected(int x, int y) {
            return find(x) == find(y);
        }
    }

    public int latestDayToCross(int row, int col, int[][] cells) {
        int total = row * col;
        UnionFind uf = new UnionFind(total + 2); // extra 2 for virtual top & bottom
        boolean[][] land = new boolean[row][col];

        int top = total;       // virtual top node why? The first unused index after all the true grid cells.
        int bottom = total+1;  // virtual bottom node

        // Reverse simulation: add land from last day backward
        for (int day = cells.length - 1; day >= 0; day--) {
            // For this be on the lookout for the 1<=r<=rows and 1<=c<=cols otherwise you'd not need to do that.
            int r = cells[day][0] - 1;
            int c = cells[day][1] - 1;

            land[r][c] = true;
            int idx = r * col + c; // 2D → 1D mapping

            // Connect with neighboring land cells
            for (int[] d : DIRS) {
                int nr = r + d[0], nc = c + d[1];
                if (nr >= 0 && nr < row && nc >= 0 && nc < col && land[nr][nc]) {
                    uf.union(idx, nr * col + nc);
                }
            }

            // Connect top/bottom row cells to virtual nodes
            if (r == 0) 
                 uf.union(idx, top);
            if (r == row - 1) 
                uf.union(idx, bottom);

            // Check connectivity
            if (uf.connected(top, bottom)) {
                return day; // last day you could cross
            }
        }

        return 0; // never connected
    }

    public static void main(String[] args) {
        LastDayToCrossReverseUnionFind solver = new LastDayToCrossReverseUnionFind();
        int row = 3, col = 3;
        int[][] cells = {
            {1,1},{2,1},{3,1},{1,2},{2,2},{3,2},{1,3},{2,3},{3,3}
        };
        System.out.println("Last day to cross: " + solver.latestDayToCross(row, col, cells));
    }
}
