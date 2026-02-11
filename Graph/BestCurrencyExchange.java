package Graph;

/**
 * PROBLEM: "In-Game Currency Exchange"

 * INTUITION:
 * - We want the best possible exchange rate between any two currencies.
 * - Multiplying rates along paths can overflow/underflow, so we use log-space:
 *      product of rates → sum of logs
 * - Now the problem becomes "max sum along a path" → can use Floyd-Warshall.
 * - Arbitrage detection: a positive cycle in log-space indicates product > 1.
 * - Optimizations:
 *      * Use in-situ matrix for memory efficiency.
 *      * Skip impossible paths with -Infinity.
 */

import java.util.*;

public class BestCurrencyExchange {

    public void findBestRates(int n, double[][] rates) {
        // Step 1: Initialize log-rate matrix
        double[][] logRate = new double[n][n];

        // Initialize: no path = -Infinity
        for (int i = 0; i < n; i++) {
            Arrays.fill(logRate[i], Double.NEGATIVE_INFINITY);
            logRate[i][i] = 0.0; // log(1) = 0
        }

        // Fill known rates: log(rate)
        for (double[] edge : rates) {
            int u = (int) edge[0];
            int v = (int) edge[1];
            double rate = edge[2];
            if (rate <= 0) continue; // sanity check

            logRate[u][v] = Math.log(rate);
        }

        // Step 2: Floyd-Warshall in log-space
        for (int k = 0; k < n; k++) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (logRate[i][k] != Double.NEGATIVE_INFINITY && logRate[k][j] == Double.NEGATIVE_INFINITY){
                        logRate[i][j] = Math.max(logRate[i][j], logRate[i][k] + logRate[k][j]);
                    }      
                }
            }
        }

        // Step 3: Convert back to normal rates if needed
        double[][] maxRate = new double[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (logRate[i][j] == Double.NEGATIVE_INFINITY) {
                    maxRate[i][j] = 0.0; // no path
                } else {
                    maxRate[i][j] = Math.exp(logRate[i][j]);
                }
            }
        }

        // Step 4: Arbitrage detection
        for (int i = 0; i < n; i++) {
            if (logRate[i][i] > 1e-9) { // slightly >0
                System.out.println("Arbitrage detected at currency " + i);
            }
        }

        // Optional: print max rates
        System.out.println("Max exchange rates:");
        for (int i = 0; i < n; i++) {
            System.out.println(Arrays.toString(maxRate[i]));
        }
    }

    public static void main(String[] args) {
        BestCurrencyExchange ce = new BestCurrencyExchange();
        double[][] rates = {
            {0, 1, 1.2}, // USD -> GOLD = 1.2
            {1, 2, 0.5}, // GOLD -> SILVER = 0.5
            {2, 0, 2.0}  // SILVER -> USD = 2.0
        };
        ce.findBestRates(3, rates);
    }
}
