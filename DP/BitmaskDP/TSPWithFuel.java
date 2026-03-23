package BitmaskDP;

import java.util.Arrays;

/**
 * PROBLEM: TSP with Fuel Constraint
 * 
 * STATEMENT:
 * - You have N cities (0 to N-1), with distances given by dist[][].
 * - You start at City 0 with a fuel capacity of MAX_FUEL.
 * - Moving to any city consumes 1 fuel unit.
 * - You can only refill fuel by visiting City 0 (the Hub).
 * - Goal: Visit all cities at least once and return to City 0 with minimum total distance.
 * 
 * DP DESIGN:
 * - State: dp[mask][u][fuel]
 *      mask : bitmask of visited cities
 *      u    : current city
 *      fuel : remaining fuel units
 * - Transition:
 *      For every city v:
 *        - Skip if v != 0 and already visited
 *        - nextFuel = (v == 0) ? MAX_FUEL : fuel - 1
 *        - nextMask = mask | (1 << v)
 *        - dp[nextMask][v][nextFuel] = min(dp[nextMask][v][nextFuel], dp[mask][u][fuel] + dist[u][v])
 * - Base Case: dp[1 << 0][0][MAX_FUEL] = 0 (start at City 0 with full fuel)
 * - Final Answer: Min cost to visit all cities and return to City 0
 */

public class TSPWithFuel {

    public int solve(int[][] dist, int maxFuel) {
        int n = dist.length;
        int numMasks = 1 << n;

        // dp[mask][city][fuel] = min cost to reach 'city' with 'mask' visited and 'fuel' remaining
        int[][][] dp = new int[numMasks][n][maxFuel + 1];
        for (int[][] maskLayer : dp)
            for (int[] cityLayer : maskLayer)
                Arrays.fill(cityLayer, 1000000); // sentinel for unreachable

        // Base case: start at city 0, visited only city 0, with full fuel
        dp[1][0][maxFuel] = 0;

        // Iterate over all masks
        for (int mask = 1; mask < numMasks; mask++) {
            for (int u = 0; u < n; u++) {
                for (int f = 0; f <= maxFuel; f++) {
                    if (dp[mask][u][f] >= 1000000) continue;

                    // Try moving to each city
                    for (int v = 0; v < n; v++) {
                        // Skip already visited cities except city 0
                        if (v != 0 && ((mask & (1 << v)) != 0)) continue;

                        int nextMask = mask | (1 << v);
                        int nextFuel = (v == 0) ? maxFuel : f - 1;

                        if (nextFuel >= 0) {
                            int newCost = dp[mask][u][f] + dist[u][v];
                            dp[nextMask][v][nextFuel] = Math.min(dp[nextMask][v][nextFuel], newCost);
                        }
                    }
                }
            }
        }

        // Compute final answer: all cities visited, must return to city 0
        int minTotalCost = 1000000;
        for (int u = 0; u < n; u++) {
            for (int f = 0; f <= maxFuel; f++) {
                if (dp[numMasks - 1][u][f] < 1000000) {
                    // Need at least 1 fuel to return to 0
                    if (f >= 1) {
                        minTotalCost = Math.min(minTotalCost, dp[numMasks - 1][u][f] + dist[u][0]);
                    }
                }
            }
        }

        return minTotalCost >= 1000000 ? -1 : minTotalCost;
    }

    public static void main(String[] args) {
        TSPWithFuel solver = new TSPWithFuel();

        // Example: 4 cities
        int[][] dist = {
            {0, 10, 15, 20},
            {10, 0, 35, 25},
            {15, 35, 0, 30},
            {20, 25, 30, 0}
        };

        int maxFuel = 2; // Can only visit 2 cities before needing to return to 0

        int result = solver.solve(dist, maxFuel);
        System.out.println("Min Cost with Fuel Constraint: " + result);
    }
}