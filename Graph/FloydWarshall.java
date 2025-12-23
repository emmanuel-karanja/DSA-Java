package Graph;

/**
 * PROBLEM: All-Pairs Shortest Paths using Floyd–Warshall
 * 
 * DESCRIPTION:
 * Given a weighted directed graph (can have negative edges but no negative cycles),
 * compute the shortest distance between every pair of vertices.
 * 
 * USE CASES:
 * 1. Find shortest path distances between all pairs of nodes.
 * 2. Detect negative cycles (if dist[i][i] < 0 after the algorithm, negative cycle exists).
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


public class FloydWarshall {

    private static final int INF = Integer.MAX_VALUE; // large value representing infinity

    public static int[][] floydWarshall(int[][] graph) {
        int V = graph.length;
        // Initialize distance matrix with input graph weights
        int[][] dist = new int[V][V];

        for (int i = 0; i < V; i++) {
            for (int j = 0; j < V; j++) {
                dist[i][j] = graph[i][j];
            }
        }

        // Floyd–Warshall main loop
        for (int k = 0; k < V; k++) {       // consider each intermediate vertex
            for (int i = 0; i < V; i++) {   // start vertex
                for (int j = 0; j < V; j++) { // end vertex
                    if (dist[i][k] < INF && dist[k][j] < INF) {
                        dist[i][j] = Math.min(dist[i][j], dist[i][k] + dist[k][j]);
                    }
                }
            }
        }

        return dist;
    }

    public static void main(String[] args) {
        int INF = Integer.MAX_VALUE;

        // Graph represented as adjacency matrix
        // 0 = vertex 0, 1 = vertex 1, 2 = vertex 2
        int[][] graph = {
            {0, 3, 10},
            {INF, 0, 4},
            {INF, INF, 0}
        };

        int[][] dist = floydWarshall(graph);

        // Print shortest distance matrix
        System.out.println("Shortest distance matrix:");
        for (int i = 0; i < dist.length; i++) {
            for (int j = 0; j < dist.length; j++) {
                if (dist[i][j] >= INF) {
                    System.out.print("INF ");
                } else {
                    System.out.print(dist[i][j] + " ");
                }
            }
            System.out.println();
        }

        // Detect negative cycle (optional)
        for (int i = 0; i < dist.length; i++) {
            if (dist[i][i] < 0) {
                System.out.println("Negative cycle detected!");
            }
        }
    }
}
