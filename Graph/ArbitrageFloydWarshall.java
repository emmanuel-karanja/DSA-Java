package Graph;

/**
 * 
 * Problem Statement:
 * Given exchange rates between n currencies, detect if there exists an arbitrage opportunity.
 * An arbitrage occurs if a sequence of exchanges can yield more than you started with.
 * 
 * Intuition:
 * Transform rates with -log(rate) to convert multiplicative profits into additive weights.
 * Use Floyd-Warshall to detect negative cycles. If dp[i][i] < 0 for any i, arbitrage exists.
 */

import java.util.*;

public class ArbitrageFloydWarshall {

    private int n;
    private double[][] rates;
    private double[][] dp;

    public ArbitrageFloydWarshall(double[][] rates) {
        this.n = rates.length;
        this.rates = rates;
        this.dp = new double[n][n];
        initializeDP();
    }

    // Initialize dp matrix with -log(rates)
    private void initializeDP() {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i == j) {
                    dp[i][j] = 0;
                }
                else {
                    dp[i][j] = -Math.log(rates[i][j]);
                }
            }
        }
    }

    // Run Floyd-Warshall algorithm
    private void floydWarshall() {
        for (int k = 0; k < n; k++) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    dp[i][j] = Math.min(dp[i][j], dp[i][k] + dp[k][j]);
                }
            }
        }
    }

    // Check if arbitrage exists
    public boolean hasArbitrage() {
        floydWarshall();
        for (int i = 0; i < n; i++) {
            if (dp[i][i] < 0) return true;
        }
        return false;
    }

    // Optional: print shortest paths matrix for debugging
    public void printDPMatrix() {
        System.out.println("DP Matrix (-log rates / shortest paths):");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                System.out.printf("%8.4f ", dp[i][j]);
            }
            System.out.println();
        }
    }

    // Driver method
    public static void main(String[] args) {
        double[][] rates = {
            {1, 0.5, 2},
            {2, 1, 4},
            {0.5, 0.25, 1}
        };

        ArbitrageFloydWarshall arb = new ArbitrageFloydWarshall(rates);

        if (arb.hasArbitrage()) {
            System.out.println("Arbitrage opportunity exists!");
        } else {
            System.out.println("No arbitrage opportunity.");
        }

        // Optional: see the transformed matrix
        arb.printDPMatrix();
    }
}
