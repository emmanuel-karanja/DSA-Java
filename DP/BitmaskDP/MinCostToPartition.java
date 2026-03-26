

import java.util.*;

/*
PROBLEM: Minimum Cost to Partition a Set into Valid Groups

You are given:
- An integer n (n ≤ 16)
- A cost matrix cost[i][j] representing incompatibility between elements i and j

Goal:
Partition all elements {0,1,...,n-1} into ANY number of groups such that:
- Each element belongs to exactly one group
- The cost of a group is the sum of pairwise costs inside that group
- Total cost (sum of all group costs) is minimized

Return the minimum total cost.

------------------------------------------------------------
WHY THIS IS SUBMASK DP

We are NOT:
- Building by adding one element at a time
- Tracking order or permutations

Instead, we are:
 Splitting a set into smaller subsets (groups)

For a given mask (set of elements), we try:
    “What if I take a submask as ONE group,
     and solve the rest recursively?”

This leads to:

dp[mask] = min over all submasks:
    cost[submask] + dp[mask ^ submask]

------------------------------------------------------------
KEY IDEA

1. Precompute cost of every subset:
   groupCost[mask] = sum of pairwise costs inside mask

2. For each mask:
   iterate over ALL submasks

Classic trick:
    for (sub = mask; sub > 0; sub = (sub - 1) & mask)

------------------------------------------------------------
TIME COMPLEXITY

- Precompute: O(n^2 * 2^n)
- DP: O(3^n)

Works for n ≤ 16

------------------------------------------------------------
PATTERN SIGNALS

- "Partition into groups"
- "Minimize cost over partitions"
- "Combine subsets, not elements"

------------------------------------------------------------
*/

public class MinCostToPartition {

    public static int minCostPartition(int[][] cost) {
        int n = cost.length;
        int totalMasks = 1 << n;

        // Step 1: Precompute group cost for every subset
        int[] groupCost = new int[totalMasks];

        for (int mask = 0; mask < totalMasks; mask++) {
            int c = 0;
            for (int i = 0; i < n; i++) {
                if ((mask & (1 << i)) == 0) continue;
                for (int j = i + 1; j < n; j++) {
                    if ((mask & (1 << j)) != 0) {
                        c += cost[i][j];
                    }
                }
            }
            groupCost[mask] = c;
        }

        // Step 2: DP over masks
        int[] dp = new int[totalMasks];
        Arrays.fill(dp, Integer.MAX_VALUE / 2);

        dp[0] = 0; // base case: empty set costs 0

        for (int mask = 1; mask < totalMasks; mask++) {

            // iterate over all submasks
            for (int sub = mask; sub > 0; sub = (sub - 1) & mask) {

                int remaining = mask ^ sub;

                dp[mask] = Math.min(
                    dp[mask],
                    groupCost[sub] + dp[remaining]
                );
            }
        }

        return dp[totalMasks - 1];
    }

    public static void main(String[] args) {
        /*
        Example:

        n = 4
        cost matrix:
              0  1  2  3
        0     0  3  1  5
        1     3  0  4  2
        2     1  4  0  6
        3     5  2  6  0

        Interpretation:
        - Putting 0 and 1 together costs 3
        - Putting 2 and 3 together costs 6
        etc.

        The algorithm will decide optimal grouping:
        e.g. {0,2}, {1,3} or {0,1,2,3}, etc.
        */

        int[][] cost = {
            {0, 3, 1, 5},
            {3, 0, 4, 2},
            {1, 4, 0, 6},
            {5, 2, 6, 0}
        };

        int result = minCostPartition(cost);

        System.out.println("Minimum partition cost: " + result);
    }
}