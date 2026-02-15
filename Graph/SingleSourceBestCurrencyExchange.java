package Graph;

import java.util.*;

/**
 * PROBLEM: "In-Game Currency Exchange"
 *
 * INTUITION:
 * - We want the best possible exchange rate from a source currency to all others.
 * - Multiplying rates along paths can overflow/underflow, so we use log-space:
 *      product of rates → sum of logs
 * - Now the problem becomes "max sum along a path" → can use Bellman-Ford.
 * - No arbitrage detection needed for pure conversion.
 */

public class SingleSourceBestCurrencyExchange {

    public double[] findBestRates(int n, double[][] rates, int source) {
        // Step 1: Initialize distances (log-space)
        double[] dist = new double[n];
        Arrays.fill(dist, Double.NEGATIVE_INFINITY);
        dist[source] = 0.0; // log(1) = 0

        // Step 2: Bellman-Ford relaxation
        for (int k = 0; k < n - 1; k++) {
            boolean updated = false;
            for (double[] edge : rates) {
                int u = (int) edge[0];
                int v = (int) edge[1];
                double rate = edge[2];
                if (dist[u] != Double.NEGATIVE_INFINITY) {
                    double newDist = dist[u] + Math.log(rate);
                    if (newDist > dist[v]) {
                        dist[v] = newDist;
                        updated = true;
                    }
                }
            }
            // Early exit if no updates
            if (!updated) break;
        }

        // Step 3: Convert back to normal rates
        double[] maxRate = new double[n];
        for (int i = 0; i < n; i++) {
            maxRate[i] = dist[i] == Double.NEGATIVE_INFINITY ? 0.0 : Math.exp(dist[i]);
        }

        return maxRate;
    }

    public static void main(String[] args) {
        SingleSourceBestCurrencyExchange ce = new SingleSourceBestCurrencyExchange();

        double[][] rates = {
            {0, 1, 1.2}, // USD -> GOLD = 1.2
            {1, 2, 0.5}, // GOLD -> SILVER = 0.5
            {2, 0, 2.0}  // SILVER -> USD = 2.0
        };

        int sourceCurrency = 0; // USD
        double[] bestRates = ce.findBestRates(3, rates, sourceCurrency);

        System.out.println("Best conversion rates from currency " + sourceCurrency + ":");
        for (int i = 0; i < bestRates.length; i++) {
            System.out.println(sourceCurrency + " -> " + i + " = " + bestRates[i]);
        }
    }
}
