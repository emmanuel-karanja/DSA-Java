package BitmaskDP;

import java.util.*;

/**
 * ============================================================
 * PROBLEM: MINIMUM ELEVATOR TRIPS (Top-Down Version)
 * ============================================================
 *
 * Given:
 * - n people with individual weights w[0..n-1]
 * - An elevator with weight limit W
 * 
 * Goal:
 * - Find the minimum number of trips to carry all people.
 *
 * TOP-DOWN APPROACH (Memoization):
 * --------------------------------
 *
 * 1. STATE:
 *    f(mask) = minimum number of trips required to carry all people in 'mask'.
 *
 * 2. BASE CASE:
 *    If mask == 0 (no people left), return 0 trips.
 *
 * 3. TRANSITION:
 *    For each non-empty subset 'sub' of 'mask' whose total weight <= W:
 *      - Take this subset in the next trip (1 trip)
 *      - Recursively compute f(mask ^ sub) for remaining people
 *      - minTrips = min(minTrips, 1 + f(mask ^ sub))
 *
 * 4. OPTIMIZATION:
 *    - Precompute subset weights to quickly check if a subset fits.
 *    - Memoize computed masks to avoid recomputation.
 *
 * 5. COMPLEXITY:
 *    - O(n * 2^n) time, since there are 2^n masks and enumerating subsets of each mask.
 *    - Feasible for n <= 20.
 *
 * 6. INTUITION:
 *    - At any point, the recursive call asks: "Which subset of remaining people should I carry in the next trip?"
 *    - This top-down approach mirrors natural reasoning: choose a valid group, reduce the problem, repeat.
 */

public class MinElevatorTripsTopDown {

    public static int minElevatorTrips(int[] weights, int W) {
        int n = weights.length;
        int N = 1 << n; // total masks

        // Precompute weight of all subsets for fast lookup
        int[] subsetWeight = new int[N];
        for (int mask = 0; mask < N; mask++) {
            int sum = 0;
            for (int i = 0; i < n; i++) {
                if ((mask & (1 << i)) != 0) sum += weights[i];
            }
            subsetWeight[mask] = sum;
        }

        Map<Integer, Integer> memo = new HashMap<>();
        return dfs(N - 1, subsetWeight, W, memo);
    }

    private static int dfs(int mask, int[] subsetWeight, int W, Map<Integer, Integer> memo) {
        if (mask == 0) return 0; // base case: no people left
        if (memo.containsKey(mask)) return memo.get(mask);

        int minTrips = Integer.MAX_VALUE;

        // Enumerate all subsets of current mask
        for (int sub = mask; sub > 0; sub = (sub - 1) & mask) {
            if (subsetWeight[sub] <= W) {
                int remaining = mask ^ sub; // remove chosen subset
                int trips = 1 + dfs(remaining, subsetWeight, W, memo);
                minTrips = Math.min(minTrips, trips);
            }
        }

        memo.put(mask, minTrips);
        return minTrips;
    }

    public static void main(String[] args) {
        int[] weights = {2, 3, 5};
        int W = 5;
        System.out.println(minElevatorTrips(weights, W)); // Output: 2

        int[] weights2 = {1, 2, 3, 4};
        int W2 = 5;
        System.out.println(minElevatorTrips(weights2, W2)); // Output: 3
    }
}