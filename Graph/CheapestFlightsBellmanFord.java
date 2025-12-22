package Graph;

import java.util.*;

/**
 * Problem Statement:
 * Given n cities connected by flights flights[i] = [u, v, w], find the cheapest flight from src to dst
 * with at most K stops.
 * Return -1 if no such route exists.
 * 
 * Approach (Bellman-Ford style with extra state):
 * - Construct Edge objects from the input array.
 * - Use a 2D bestCost table: bestCost[city][stops] = min cost to reach city using exactly `stops` stops.
 * - Relax all edges repeatedly for K+1 iterations (0 to K stops allowed).
 * - Each relaxation: if reaching city v with k stops improves cost, update bestCost[v][k].
 * - After K+1 relaxations, pick the minimum cost to reach dst with <= K stops.
 */

class Edge {
    int u, v, w;
    Edge(int u, int v, int w) {
        this.u = u;
        this.v = v;
        this.w = w;
    }
}

public class CheapestFlightsBellmanFord {

    public static int findCheapestPrice(int n, int[][] flightsArr, int src, int dst, int K) {
        final int INF = Integer.MAX_VALUE / 2;

        // Construct edges
        List<Edge> edges = new ArrayList<>();
        for (int[] f : flightsArr) {
            edges.add(new Edge(f[0], f[1], f[2]));
        }

        // Initialize bestCost table: bestCost[city][stopsUsed]
        int[][] bestCost = new int[n][K + 2];
        for (int i = 0; i < n; i++) {
            Arrays.fill(bestCost[i], INF);
        }
        
        bestCost[src][0] = 0;

        // Relax edges up to K+1 times (0..K stops)
        for (int stops = 0; stops <= K; stops++) {
            for (Edge e : edges) {
                if (bestCost[e.u][stops] != INF) {
                    bestCost[e.v][stops + 1] = Math.min(bestCost[e.v][stops + 1], bestCost[e.u][stops] + e.w);
                }
            }
        }

        // Find the minimum cost to dst using <= K+1 edges (i.e., <= K stops)
        int minCost = INF;
        for (int stops = 1; stops <= K + 1; stops++) {
            minCost = Math.min(minCost, bestCost[dst][stops]);
        }

        return minCost == INF ? -1 : minCost;
    }

    public static void main(String[] args) {
        int n = 3;
        int[][] flightsArr = {
            {0, 1, 100},
            {1, 2, 100},
            {0, 2, 500}
        };
        int src = 0, dst = 2, K = 1;

        int cheapest = findCheapestPrice(n, flightsArr, src, dst, K);
        System.out.println("Cheapest flight cost: " + cheapest); // Output: 200
    }
}