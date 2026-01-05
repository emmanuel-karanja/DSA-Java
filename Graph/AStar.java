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

    // Manhattan distance heuristic (admissible for 4-direction grid)
    private static int heuristic(int r1, int c1, int r2, int c2) {
        return Math.abs(r1 - r2) + Math.abs(c1 - c2);
    }

    public static int aStar(int[][] grid, int sr, int sc, int tr, int tc) {
        int m = grid.length, n = grid[0].length;

        int[][] bestG = new int[m][n];
        for (int[] row : bestG) Arrays.fill(row, Integer.MAX_VALUE);

        PriorityQueue<Node> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a.f));

        int h0 = heuristic(sr, sc, tr, tc);
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
                    int h = heuristic(nr, nc, tr, tc);
                    pq.offer(new Node(nr, nc, newG, newG + h));
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
