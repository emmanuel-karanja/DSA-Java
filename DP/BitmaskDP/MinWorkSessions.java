

import java.util.*;

/**
 * ============================================================
 * PROBLEM: Minimum Number of Work Sessions
 * ============================================================
 *
 * Problem Statement:
 * -----------------
 * You are given:
 * - tasks[] where tasks[i] = time required for task i
 * - sessionTime = maximum time allowed per work session
 *
 * Rules:
 * - You can complete multiple tasks in one session
 * - Total time of tasks in a session must be ≤ sessionTime
 * - Each task must be completed exactly once
 *
 * Goal:
 * - Return the minimum number of sessions required
 *
 * ------------------------------------------------------------
 * REASONING (Subset Bitmask DP - Batching)
 * ------------------------------------------------------------
 *
 * 1. State Representation:
 *    - Use a bitmask `mask` of size n:
 *        bit i = 1 → task i is completed
 *
 *    - dp[mask] = minimum number of sessions to complete tasks in mask
 *
 * 2. Key Insight:
 *    - In each session, we can complete a SUBSET of remaining tasks
 *    - So at each state, we:
 *        - find remaining tasks
 *        - try all subsets of them that fit within sessionTime
 *
 * 3. Precomputation:
 *    - For every subset mask:
 *        cost[mask] = total time of tasks in this subset
 *
 *    - This lets us check:
 *        "Can this subset be completed in one session?"
 *
 * 4. Transition:
 *    For each mask:
 *        remaining = (~mask) & (totalMask - 1)
 *
 *        for each sub ⊆ remaining:
 *            if cost[sub] <= sessionTime:
 *                newMask = mask | sub
 *                dp[newMask] = min(dp[newMask], dp[mask] + 1)
 *
 * 5. Base Case:
 *    dp[0] = 0 (no tasks done → no sessions needed)
 *
 * 6. Answer:
 *    dp[(1<<n) - 1]
 *
 * ------------------------------------------------------------
 * COMPLEXITY:
 * ------------------------------------------------------------
 * - States: 2^n
 * - Subset enumeration: ~3^n worst-case
 * - Works for n ≤ 14
 *
 * ------------------------------------------------------------
 */

public class MinWorkSessions {

    public static int minSessions(int[] tasks, int sessionTime) {
        int n = tasks.length;
        int totalMask = 1 << n;

        // 1️. Precompute subset costs
        int[] cost = new int[totalMask];

        for (int mask = 0; mask < totalMask; mask++) {
            int sum = 0;
            for (int i = 0; i < n; i++) {
                if ((mask & (1 << i)) != 0) {
                    sum += tasks[i];
                }
            }
            cost[mask] = sum;
        }

        // 2️. Initialize DP
        int[] dp = new int[totalMask];
        Arrays.fill(dp, Integer.MAX_VALUE / 2);
        dp[0] = 0;

        // 3️. DP transitions
        for (int mask = 0; mask < totalMask; mask++) {

            // remaining tasks
            int remaining = (~mask) & (totalMask - 1);

            // enumerate all subsets of remaining
            for (int sub = remaining; sub > 0; sub = (sub - 1) & remaining) {

                // can this subset fit in one session?
                if (cost[sub] <= sessionTime) {

                    int newMask = mask | sub;

                    dp[newMask] = Math.min(
                        dp[newMask],
                        dp[mask] + 1
                    );
                }
            }
        }

        return dp[totalMask - 1];
    }

    public static void main(String[] args) {

        // Example 1
        int[] tasks1 = {1, 2, 3};
        int sessionTime1 = 3;

        // Optimal:
        // Session 1: {1,2}
        // Session 2: {3}
        System.out.println("Minimum sessions: " + minSessions(tasks1, sessionTime1));
        // Output: 2


        // Example 2
        int[] tasks2 = {3, 1, 3, 1, 1};
        int sessionTime2 = 8;

        // One optimal grouping:
        // Session 1: {3,1,3,1} = 8
        // Session 2: {1}
        System.out.println("Minimum sessions: " + minSessions(tasks2, sessionTime2));
        // Output: 2
    }
}