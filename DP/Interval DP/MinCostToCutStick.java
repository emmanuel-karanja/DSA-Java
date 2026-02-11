

/**
 * DP BREAKDOWN: Minimum Cost to Cut a Stick
 *
 * PROBLEM STATEMENT:
 * Given a stick of length `n` and an array `cuts` representing positions 
 * where the stick needs to be cut, return the minimum total cost to perform 
 * all cuts. The cost of making a cut is equal to the length of the stick being cut.
 *
 * You may perform the cuts in any order. Each cut splits the stick into two parts, 
 * which can be further cut. The goal is to minimize the total cutting cost.
 *
 * 16.12.2025 at 1224 hrs
 *
 * First thing.
 *
 * 1. GOAL:
 *    Minimize the total cost to cut a stick into given positions. 
 *    Cost of cutting a stick = length of the stick being cut at that step.
 *
 * 2. STATE:
 *    dp[i][j] = Minimum cost to cut the sub-stick between positions i and j
 *    
 *
 * 3. CHOICES / DECISIONS:
 *    For each sub-stick between i and j
 *      - Choose a cut[k] where i < k < j
 *      - Cost = length of stick (cut[j] - cut[i]) + dp[i][k] + dp[k][j]
 *    We take the minimum over all possible k
 *
 * 4. BASE CASE:
 *    dp[i][i+1] = 0, no cuts to make between consecutive cuts
 *
 * 5. RECURRENCE:
 *    dp[i][j] = min { dp[i][k] + dp[k][j] + (cut[j] - cut[i]) } for all i < k < j
 *
 * 6. EVALUATION ORDER:
 *    Process intervals of increasing length (bottom-up),  i.e we do smaller segments first.
 *    from length 2 to cut.length. This ensures dp[i][k] and dp[k][j] are already computed.
 *
 * 7. TRANSITION COST:
 *    O(j-i) for each interval (number of k choices)
 *    Total Complexity = O(n^3) where n = number of cuts
 *
 * 8. OPTIMAL SUBSTRUCTURE:
 *    The minimum cost for an interval depends only on optimal costs of sub-intervals.
 *
 * 9. WHY DP AND NOT GREEDY:
 *    - A single cut does not dominate the solution space.
 *    - Choosing a cut locally minimal might increase future costs.
 *    - Must explore all valid choices to guarantee global optimality.
 */
public class MinCostToCutStick {

    public int minCost(int n, int[] cuts) {
        int m = cuts.length;
         /**We sort allCuts to ensure intervals in the DP correspond to actual contiguous stick segments. 
        * 1. Without sorting, the recurrence allCuts[right] - allCuts[left] and the subinterval DP would break,
        * giving incorrect results. */
        java.util.Arrays.sort(cuts);            // sort the actual cuts

        //2. Add sentinels at the end
        int[] allCuts = new int[m + 2];
        allCuts[0] = 0;               // left sentinel
        System.arraycopy(cuts, 0, allCuts, 1, m);
        allCuts[m + 1] = n;           // right sentinel


        int[][] dp = new int[m + 2][m + 2];

      
        for (int len = 2; len < m + 2; len++) {
            for (int left = 0; left + len < m + 2; left++) {
                int right = left + len;
                dp[left][right] = Integer.MAX_VALUE;
                for (int k = left + 1; k < right; k++) {
                    int cost = allCuts[right] - allCuts[left] + dp[left][k] + dp[k][right];
                    dp[left][right] = Math.min(dp[left][right], cost);
                }
            }
        }

        return dp[0][m + 1];  //since we sorted
    }

    // -----------------------------
    // Driver / Test code
    // -----------------------------
    public static void main(String[] args) {
        MinCostToCutStick solver = new MinCostToCutStick();

        int n = 7;
        int[] cuts = {1, 3, 4, 5};
        int minCost = solver.minCost(n, cuts);
        System.out.println("Minimum cost to cut the stick: " + minCost);
        // Expected output: 16
    }
}
