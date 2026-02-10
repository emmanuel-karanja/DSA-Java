package Graph;

/*
 * A* (A-Star) Pathfinding Algorithm — High-Level Explanation
 * ---------------------------------------------------------
 *
 * GOAL:
 * Find the shortest path from a START node to a GOAL node in a graph or grid.
 *
 * CORE IDEA:
 * A* always expands the node with the smallest estimated total cost:
 *
 *      f(n) = g(n) + h(n)
 *
 * where:
 *  - g(n): actual cost from START to node n (cost so far)
 *  - h(n): heuristic estimate from n to GOAL (never overestimates)
 *  - f(n): estimated total cost of a path going through n
 *
 * INTUITION:
 * - g(n) ensures correctness (like Dijkstra)
 * - h(n) provides direction (like Greedy search)
 * - Together, they give optimal + efficient search
 *
 * IMPORTANT PROPERTY:
 * If the heuristic h(n) is ADMISSIBLE (never overestimates),
 * A* is GUARANTEED to find the optimal shortest path.
 *
 * SPECIAL CASES:
 * - If h(n) = 0 for all nodes → A* becomes Dijkstra
 * - If h(n) is too aggressive → faster but may be incorrect
 *
 * DATA STRUCTURES:
 * - PriorityQueue (min-heap) ordered by f(n)
 * - Map / array for best known g(n)
 * - Parent map to reconstruct the path
 *
 * TIME COMPLEXITY:
 * Depends on the heuristic.
 * Worst-case: O(E log V) (same as Dijkstra)
 * Practical performance is usually much better.
 *
 * WHEN TO USE:
 * - Shortest path problems
 * - Grids, maps, mazes
 * - Navigation, routing, game AI
 */



import java.util.*;

/**
 * Heuristics for Grid / Pathfinding Problems
 * 
 * 1. Manhattan Distance (4-direction grid, only horizontal/vertical moves)
 *    h = |x1 - x2| + |y1 - y2|
 * 
 * 2. Chebyshev Distance (8-direction grid, diagonal allowed, cost = 1)
 *    h = max(|x1 - x2|, |y1 - y2|)
 * 
 * 3. Euclidean Distance (straight-line distance)
 *    h = sqrt((x1 - x2)^2 + (y1 - y2)^2)
 * 
 * 4. Octile Distance (8-direction grid, diagonal cost = sqrt(2), straight = 1)
 *    dx = |x1 - x2|
 *    dy = |y1 - y2|
 *    h = min(dx, dy) * sqrt(2) + |dx - dy|
 */
  
public class AStar {

    static class Node {
        int r, c;
        int g; // cost so far
        int f; // g + h

        Node(int r, int c, int g, int f) {
            this.r = r;
            this.c = c;
            this.g = g;
            this.f = f;
        }
    }

    static class Heuristics {

    public static int manhattan(int x1, int y1, int x2, int y2) {
            return Math.abs(x1 - x2) + Math.abs(y1 - y2);
        }

        public static int chebyshev(int x1, int y1, int x2, int y2) {
            return Math.max(Math.abs(x1 - x2), Math.abs(y1 - y2));
        }

        public static double euclidean(int x1, int y1, int x2, int y2) {
            int dx = x1 - x2;
            int dy = y1 - y2;
            return Math.sqrt(dx * dx + dy * dy);
        }

        public static double octile(int x1, int y1, int x2, int y2) {
            int dx = Math.abs(x1 - x2);
            int dy = Math.abs(y1 - y2);
            return Math.min(dx, dy) * Math.sqrt(2) + Math.abs(dx - dy);
        }
    }

    public static int aStar(int[][] grid, int sr, int sc, int tr, int tc) {
        int m = grid.length, n = grid[0].length;

        // Initialize the state
        int[][] bestG = new int[m][n];
        for (int[] row : bestG) {
            Arrays.fill(row, Integer.MAX_VALUE);
        }

        PriorityQueue<Node> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a.f));

        int h0 = Heuristics.manhattan(sr, sc, tr, tc);
        pq.offer(new Node(sr, sc, 0, h0));
        bestG[sr][sc] = 0;

        int[][] dirs = {{1,0},{-1,0},{0,1},{0,-1}};

        while (!pq.isEmpty()) {
            Node cur = pq.poll();

            if (cur.r == tr && cur.c == tc) {
                return cur.g; // shortest path found
            }

            for (int[] d : dirs) {
                int nr = cur.r + d[0];
                int nc = cur.c + d[1];

                if (nr < 0 || nc < 0 || nr >= m || nc >= n || grid[nr][nc] == 1)
                    continue;

                int newG = cur.g + 1;

                if (newG < bestG[nr][nc]) {
                    bestG[nr][nc] = newG;
                    // Converges on the target fast.
                    int h = Heuristics.manhattan(nr, nc, tr, tc);
                    int newF=newG+h;
                    pq.offer(new Node(nr, nc, newG, newF));
                }
            }
        }

        return -1; // no path
    }

    public static void main(String[] args) {
        int[][] grid = {
            {0,0,0},
            {1,1,0},
            {0,0,0}
        };

        System.out.println(aStar(grid, 0, 0, 2, 2)); // Output: 4
    }
}
