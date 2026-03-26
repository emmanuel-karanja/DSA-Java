

import java.util.*;

/**
 * ============================================================
 * PROBLEM: MINIMUM ELEVATOR TRIPS
 * ============================================================
 * 
 * Given:
 * - n people with individual weights w[0..n-1]
 * - An elevator with weight limit W
 * 
 * Goal:
 * - Find the minimum number of trips to carry all people.
 * 
 * Intuition:
 * - Represent subsets of people as bitmasks. Each bit = person already carried.
 * - DP state: dp[mask] = min trips to carry all people in 'mask'.
 * - Transition: From state 'mask', pick any subset of remaining people whose
 *   total weight <= W, and move to newMask = mask | subset.
 * - Base case: dp[0] = 0 (no one carried)
 * - Reasoning: Bitmask encodes all subsets. DP ensures minimum trips for each state.
 * 
 * Approach:
 * - Use Bitmask DP.
 * - Each DP state represents a subset of people already carried.
 *   dp[mask] = minimum number of trips needed to carry all people in 'mask'
 * - Transition:
 *   For each DP state 'mask', try all subsets of people not in 'mask' whose total weight <= W.
 *   Then move to newMask = mask | subset and update dp[newMask] = min(dp[newMask], dp[mask] + 1)
 * - Base case: dp[0] = 0 (no people require 0 trips)
 * - Optimization:
 *   Precompute all valid subsets whose total weight <= W to speed up transitions.
 * 
 * Complexity:
 * - O(n * 2^n) if generating valid subsets efficiently.
 * - Feasible for n <= 20.
 */
public class MinElevatorTrips {
    
    public static int minElevatorTrips(int[] weights, int W) {
        int n = weights.length;
        int totalMask = 1 << n; // total states (2^n)
        int[] dp = new int[totalMask];
        Arrays.fill(dp, Integer.MAX_VALUE);
        dp[0] = 0; // no people -> 0 trips

        // Precompute weight of all subsets
        int[] subsetWeight = new int[totalMask];

        // I like this approach, precalculate the subset masks to keep the transition loop simple.
        for (int mask = 0; mask < totalMask; mask++) {
            int sum = 0;
            for (int i = 0; i < n; i++) {
                if ((mask & (1 << i)) != 0) {
                    sum += weights[i];
                }
            }
            subsetWeight[mask] = sum;
        }

        for (int mask = 0; mask < totalMask; mask++) {
            if (dp[mask] == Integer.MAX_VALUE) continue;
           
            // Calculate available
            int available = (~mask) & (totalMask - 1);  

            for (int sub = available; sub > 0; sub = (sub - 1) & available) {
                // This is one way to do it. If we want count
                if (subsetWeight[sub] <= W) {
                    int newMask = mask | sub;
                    dp[newMask] = Math.min(dp[newMask], dp[mask] + 1);
                }
            }
        }

        return dp[totalMask- 1]; // all people carried
    }

    public static void main(String[] args) {
        int[] weights = {2, 3, 5,2,4};
        int W = 5;
        System.out.println("Minimum trips: " + minElevatorTrips(weights, W));
        // Output: Minimum trips: 2
    }
}