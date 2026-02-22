/**
 * PROBLEM: TSP with Fuel Mode
 * * STATEMENT:
 * Find the minimum cost to visit all N cities. 
 * MODE: You have a limited 'fuel' capacity. Each move consumes 1 unit.
 * You can only refill fuel by visiting City 0 (The Hub).
 * * GENERATIVE DP LOGIC:
 * 1. STATE: dp[mask][u][fuel] 
 * - mask: cities visited
 * - u: current city
 * - fuel: remaining capacity
 * 2. TRANSITION:
 * - For every state, try moving to every unvisited city 'v'.
 * - IF v == 0: nextFuel = MAX_FUEL (Refill)
 * - ELSE: nextFuel = fuel - 1
 * - Only move if nextFuel >= 0.
 * 3. BASE CASE: dp[1 << 0][0][MAX_FUEL] = 0 (Start at City 0 with full fuel).
 */
import java.util.Arrays;

public class TSPWithFuel {

    public int solve(int[][] dist, int maxFuel) {
        int n = dist.length;
        int numMasks = 1 << n;
        
        // dp[mask][current_city][current_fuel]
        int[][][] dp = new int[numMasks][n][maxFuel + 1];
        
        for (int[][] maskLayer : dp) {
            for (int[] cityLayer : maskLayer) {
                Arrays.fill(cityLayer, 1000000); // Large sentinel
            }
        }

        // Base Case: Start at city 0, visited city 0, with max fuel
        dp[1][0][maxFuel] = 0;

        // Iterative DP (Topological order via mask)
        for (int mask = 1; mask < numMasks; mask++) {
            for (int u = 0; u < n; u++) {
                for (int f = 0; f <= maxFuel; f++) {
                    if (dp[mask][u][f] >= 1000000) continue;

                    // Try moving to city v
                    for (int v = 0; v < n; v++) {
                        int nextMask = mask | (1 << v);
                        int nextFuel = (v == 0) ? maxFuel : f - 1;

                        if (nextFuel >= 0) {
                            int newCost = dp[mask][u][f] + dist[u][v];
                            if (newCost < dp[nextMask][v][nextFuel]) {
                                dp[nextMask][v][nextFuel] = newCost;
                            }
                        }
                    }
                }
            }
        }

        // Goal: All cities visited, must end back at city 0
        int minTotalCost = 1000000;
        for (int f = 0; f <= maxFuel; f++) {
            minTotalCost = Math.min(minTotalCost, dp[numMasks - 1][0][f]);
        }

        return minTotalCost >= 1000000 ? -1 : minTotalCost;
    }

    public static void main(String[] args) {
        TSPWithFuel solver = new TSPWithFuel();
        
        // 4 Cities
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