package BitmaskDP;



import java.util.Arrays;

/**
 * Traveling Salesman Problem (TSP) — Bitmask DP Implementation
 *
 * PROBLEM STATEMENT:
 * -----------------
 * Given 'n' cities labeled 0..n-1 and a distance matrix 'dist' where
 * dist[i][j] represents the cost to travel from city i to city j, find
 * the minimum cost to start at city 0, visit all cities exactly once,
 * and return to city 0.
 *
 * REASONING / APPROACH:
 * --------------------
 * 1. State-space:
 *    - We need to track which cities have been visited and the current city.
 *    - Represent visited cities as a bitmask (integer), where bit i = 1 if city i is visited.
 *      e.g., mask = 0b0110 means cities 1 and 2 are visited.
 *
 * 2. DP State:
 *    - dp[mask][i] = minimum cost to reach city 'i' after visiting all cities in 'mask'.
 *    - mask = bitmask of visited cities
 *    - i = current city
 *
 * 3. Base Case:
 *    - Start at city 0 with only city 0 visited: dp[1 << 0][0] = 0
 *
 * 4. Transition:
 *    - From state (mask, i), try to go to every unvisited city j:
 *      if ((mask & (1<<j)) == 0):
 *          dp[mask | (1<<j)][j] = min(dp[mask | (1<<j)][j], dp[mask][i] + dist[i][j])
 *
 * 5. Result:
 *    - After filling dp, the answer is the minimum of dp[fullMask][i] + dist[i][0] for all i != 0
 *      (returning to the starting city 0)
 *
 * 6. Complexity:
 *    - States: 2^n * n
 *    - Transitions per state: O(n)
 *    - Total: O(n^2 * 2^n) → feasible for n <= 20
 *
 * 7. Intuition:
 *    - This is like a graph traversal over a virtual "state-space graph" where each node
 *      is (mask, currentCity) and edges go to unvisited cities with weight dist[i][j].
 *    - The bitmask compresses the visited set efficiently and avoids recomputation.
 * 
 * 
 * WHY BITMASK? Without them, we'd need to maintain a boolean array of size n per dp[i][j] as extra state!
 * now it's just an int.
 */
public class TSPBitmaskDP {

    public static int tsp(int[][] dist) {
        int n = dist.length;
        int fullMask = (1 << n) - 1; // All cities visited
        int[][] dp = new int[1 << n][n];

        for (int[] row : dp) {
            Arrays.fill(row, Integer.MAX_VALUE);
        }
        dp[1][0] = 0; // Start at city 0 

        // Iterate over all masks
        for (int mask = 1; mask <= fullMask; mask++) {
            for (int i = 0; i < n; i++) {
                if ((mask & (1 << i)) == 0 || dp[mask][i] == Integer.MAX_VALUE) continue; // If i is not yet visited
                for (int j = 0; j < n; j++) {
                    if ((mask & (1 << j)) != 0) continue; // Already visited
                    int nextMask = mask | (1 << j);
                    dp[nextMask][j] = Math.min(dp[nextMask][j], dp[mask][i] + dist[i][j]);
                }
            }
        }

        // Find minimum cost to return to city 0
        int ans = Integer.MAX_VALUE;
        for (int i = 1; i < n; i++) {
            if (dp[fullMask][i] != Integer.MAX_VALUE)
                ans = Math.min(ans, dp[fullMask][i] + dist[i][0]);
        }
        return ans;
    }

    /** DRIVER FUNCTION **/
    public static void main(String[] args) {
        // Example: 4 cities with the following distance matrix
        int[][] dist = {
            {0, 10, 15, 20},
            {10, 0, 35, 25},
            {15, 35, 0, 30},
            {20, 25, 30, 0}
        };

        int minCost = tsp(dist);
        System.out.println("Minimum cost of visiting all cities and returning to start: " + minCost);
        // Expected output: 80
        // Explanation: optimal path is 0 -> 1 -> 3 -> 2 -> 0 with cost 10+25+30+15=80
    }
}
