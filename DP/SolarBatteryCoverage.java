package DP;

/**
 * PROBLEM STATEMENT:
 * You are tasked with providing solar power to a row of N houses. 
 * - Installing a battery at House i costs costs[i].
 * - A battery installed at House i covers three houses: i-1, i, and i+1.
 * - Goal: Find the MINIMUM total cost to ensure every single house is covered 
 * by at least one battery.
 *
 * DETAILED REASONING (The Elimination Strategy):
 * 1. BSOA CHECK: Fails. 
 * - While we are minimizing cost, the problem is not monotonic in a way 
 * that a simple feasibility check can solve. Knowing we can cover the 
 * row for $100 doesn't make it trivial to check if we can do it for $99 
 * without re-solving the combinatorial problem.
 *
 * 2. GREEDY CHECK: Fails. 
 * - The "Amnesia Test": If I pick the cheapest battery (local optimal), 
 * I might leave a gap that requires two very expensive batteries to fix 
 * later. Choosing a battery at house 'i' affects the 'menu' of needs for 
 * houses 'i-1' and 'i+1'.
 *
 * 3. DP CHECK: Passes.
 * - OVERLAPPING SUBPROBLEMS: To solve for house 'i', we only need to know 
 * the status of house 'i-1'. Multiple paths of battery placements could 
 * result in house 'i-1' being "covered but without its own battery," 
 * which is a recurring state.
 * - STATE DEPENDENCY: This is the clincher. Whether you MUST place a 
 * battery at house 'i' depends entirely on whether house 'i-1' is 
 * currently "naked" (uncovered).
 *
 * STATE DEFINITIONS:
 * - dp[i][0]: House i has its own battery. (Everything behind i is safe).
 * - dp[i][1]: House i has no battery, but is covered by a battery at i-1.
 * - dp[i][2]: House i has no battery and is NOT covered yet. It is "naked," 
 * relying entirely on a future battery at i+1 to be valid.
 */



public class SolarBatteryCoverage {
    public int minCost(int[] costs) {
        int n = costs.length;
        if (n == 0) return 0;
        if (n == 1) return costs[0];

        // Using long to prevent overflow during intermediate Math.min operations
        long[][] dp = new long[n][3];

        // --- BASE CASE: House 0 ---
        dp[0][0] = costs[0];          // We put a battery here.
        dp[0][1] = Integer.MAX_VALUE; // Impossible: No house -1 exists to cover 0.
        dp[0][2] = 0;                 // 0 is naked, hoping house 1 will have a battery.

        // --- TRANSITION LOGIC ---
        for (int i = 1; i < n; i++) {
            // State 0: House i gets a battery.
            // It doesn't matter what state i-1 was in; a battery at i covers 
            // the current house and satisfies the "naked" debt of i-1 if it existed.
            dp[i][0] = costs[i] + Math.min(dp[i-1][0], Math.min(dp[i-1][1], dp[i-1][2]));

            // State 1: House i has no battery, but is covered by i-1.
            // This is only legal if house i-1 actually HAD a battery (State 0).
            dp[i][1] = dp[i-1][0];

            // State 2: House i is naked.
            // This is only legal if house i-1 was already covered by i-2.
            // i-1 cannot be naked (State 2) because i is refusing to provide a battery.
            dp[i][2] = dp[i-1][1]; 
        }

        // --- FINAL ACCOUNTING ---
        // The last house (n-1) cannot be naked (State 2) because there is no 
        // house n to save it. It must either have its own battery or be 
        // covered by the previous house.
        long result = Math.min(dp[n-1][0], dp[n-1][1]);
        
        return (int) result;
    }

    public static void main(String[] args) {
        SolarBatteryCoverage solver = new SolarBatteryCoverage();
        
        // Example: Small costs in the middle and end should be picked.
        int[] costs = {10, 2, 2, 10, 2}; 
        
        int minTotalCost = solver.minCost(costs);
        System.out.println("The minimum cost to cover all houses is: " + minTotalCost);
        // Expected output: 4 (Batteries at index 1 and 4)
    }
}