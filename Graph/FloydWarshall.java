package Graph;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * PROBLEM: All-Pairs Shortest Paths using Floyd–Warshall
 * 
 * DESCRIPTION:
 * Given a weighted directed graph (can have negative edges but no negative cycles),
 * compute the shortest distance between every pair of vertices.
 * 
 * This is basically Dynamic Programming. 
 * 
 * USE CASES:
 * 1. Find shortest path distances between all pairs of nodes.
 * 2. Detect negative cycles (if dist[i][i] < 0 after the algorithm, negative cycle exists). NOTE THIS!
 * 3. Path reconstruction (with a small modification) to find actual shortest paths.
 * 4. Transitive closure / reachability problems (can be adapted).
 * 
 * COMPLEXITY:
 * Time: O(V^3), Space: O(V^2)
 * V = number of vertices
 * 
 * EXAMPLE:
 * Graph:
 *   0 -> 1 (weight 3)
 *   1 -> 2 (weight 4)
 *   0 -> 2 (weight 10)
 * Result:
 *   dist[0][2] = 7 (via 1), which is the shortest path
 */

class Edge {
    int u, v, w;
    public Edge(int u, int v, int w) {
        this.u = u;
        this.v = v;
        this.w = w;
    }
}

public class FloydWarshall {

    public static int[][] floydWarshall(Edge[] edges) {
        if (edges == null || edges.length == 0) return null;

        //  Correct way to compute number of nodes
        int n = 0;
        for (Edge e : edges) {
            n = Math.max(n, Math.max(e.u, e.v));
        }
        n += 1;

        int[][] dist = new int[n][n];
        final int INF = Integer.MAX_VALUE / 2;

        // Initialize matrix
        for (int i = 0; i < n; i++) {
            Arrays.fill(dist[i], INF);
            dist[i][i] = 0;
        }

        //  Fill edges
        for (Edge e : edges) {
            dist[e.u][e.v] = Math.min(dist[e.u][e.v], e.w);
        }

        //  Floyd-Warshall core
        for (int k = 0; k < n; k++) {
            for (int i = 0; i < n; i++) {
                if (dist[i][k] == INF) continue; // small optimization
                for (int j = 0; j < n; j++) {
                    if (dist[k][j] == INF) continue;

                    dist[i][j] = Math.min(
                        dist[i][j],
                        dist[i][k] + dist[k][j]
                    );
                }
            }
        }

        // Detect negative cycle
        for (int i = 0; i < n; i++) {
            if (dist[i][i] < 0) {
                System.out.println("Negative cycle detected");
                break;
            }
        }

        return dist;
    }
}