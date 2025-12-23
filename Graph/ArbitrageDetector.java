package Graph;

/**
 * PROBLEM: Detect Arbitrage Opportunities
 * 
 * You are given exchange rates between N currencies in a 2D array `rates`.
 * rates[i][j] represents the exchange rate from currency i to currency j.
 * 
 * Task: Determine if there exists a sequence of exchanges (a cycle) such that
 *       starting with 1 unit of some currency and exchanging along the cycle
 *       results in more than 1 unit of the starting currency.
 * 
 * In other words, find a cycle where:
 *       rate1 * rate2 * ... * rateN > 1
 * 
 * MODELING THE STATE (Using User Rubric):
 * 1. GOAL: Find a cycle where (rate1 * rate2 * ... * rateN) > 1.
 * 2. TYPE: Negative Cycle Detection using Bellman-Ford.
 * 3. MATH HACK:
 *    - To use Bellman-Ford (additive), transform multiplication to addition:
 *      log(r1 * r2) = log(r1) + log(r2)
 *    - To detect product > 1, find sum(log(rates)) > 0
 *    - To detect negative cycle: use -log(rate)
 *      If sum(-log(rates)) < 0, then product(rates) > 1
 * ALGORITHM: Bellman-Ford
 * - Edge List: {source, destination, -log(rate)}
 * - Iterations: V-1 passes to relax edges, V-th pass to detect negative cycle
 * - Complexity: O(V * E)
 */

import java.util.*;

public class ArbitrageDetector {

    static class Edge {
        int u, v;
        double weight;

        Edge(int u, int v, double weight) {
            this.u = u;
            this.v = v;
            this.weight = weight;
        }
    }

    public boolean hasArbitrage(int numCurrencies, double[][] rates) {
        // 1. Build the graph (Edge List)
        List<Edge> edges = new ArrayList<>();
        for (int i = 0; i < numCurrencies; i++) {
            for (int j = 0; j < numCurrencies; j++) {
                if (i != j && rates[i][j] > 0) {  //valid and directed ie. i,j and j,i ain't the same
                    edges.add(new Edge(i, j, -Math.log(rates[i][j]))); //we find the negative logs?
                }
            }
        }

        // 2. Initialize distances (Bellman-Ford)
        double[] dist = new double[numCurrencies];
        Arrays.fill(dist, 1e9); // Infinity
        
        dist[0] = 0; // Start at any node

        // 3. Relax edges V-1 times
        for (int i = 0; i < numCurrencies - 1; i++) {
            for (Edge e : edges) {
                if (dist[e.u] + e.weight < dist[e.v]) {
                    dist[e.v] = dist[e.u] + e.weight;
                }
            }
        }

        // 4. Check for negative cycle (V-th pass)
        for (Edge e : edges) {
            if (dist[e.u] + e.weight < dist[e.v] - 1e-9) { // handle floating point precision
                return true; // Arbitrage detected
            }
        }

        return false; // No arbitrage
    }

    public static void main(String[] args) {
        ArbitrageDetector detector = new ArbitrageDetector();

        // Example: USD=0, EUR=1, GBP=2
        // USD -> EUR (0.9), EUR -> GBP (1.2), GBP -> USD (1.1)
        // Product: 0.9 * 1.2 * 1.1 = 1.188 (> 1.0)
        double[][] rates = {
            {1.0, 0.9, 0.0},
            {0.0, 1.0, 1.2},
            {1.1, 0.0, 1.0}
        };

        System.out.println("Arbitrage possible? " + detector.hasArbitrage(3, rates));
    }
}
