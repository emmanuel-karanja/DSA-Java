package BitmaskDP;

import java.util.*;

/**
 * ============================================================
 * PROBLEM: BANDWIDTH ALLOCATION WITH PER-USER PER-SERVER OVERHEAD
 * ============================================================
 *
 * You are given:
 *   - N users with bandwidth demands U[i]
 *   - M servers with capacities C[j]
 *   - A fixed overhead X incurred EVERY time a user uses a server
 *
 * Rules:
 *   - A user's demand may be split across multiple servers
 *   - Each (user, server) connection consumes X bandwidth on that server
 *   - Servers are unordered
 *
 * Goal:
 *   - Find the MINIMUM number of servers needed to satisfy ALL users
 *   - Return -1 if impossible 
 * 
 * 
 * REASONING:
 * 
 *  What we want is the minimum number of servers that are required to handle all user workloads.
 * 
 * 1. Sort the usersin reverse order and prioritise the big workloads.
 * 2. In the loop
 *      - Can we assign the current user workload into the current server represented in
 *        the dp[mask][u] i.e. do we have enough capacity in dp[mask][u] to accomodate the
 *        currentworkload users[u]+X? and if we do we increment assigned user count by 1
 *      - if not, iterate over all unassigned servers and find one that can take the current
 *        workload i.e. server is not fully assigned yet(not set to 1 in mask) and can
 *        accomodate users[u]+X (not u+1, but u), if so assign.
 * 
 * 
 *  3. Now we will iterate over all the dp[mask][N]--N indicats that all user workloads
 *     are fully assigned, and find the min.
 *
 * DP STATE:
 *   dp[mask][u] = maximum remaining capacity in the last added server
 *                  after fully assigning users 0..u-1 using exactly the servers in `mask`
 *
 * Invariant:
 *   - All users < u are fully assigned
 *   - Overhead X is applied exactly once per (user, server) usage
 */

public class BandwidthAllocation {

    public static int minServers(int[] users, int[] servers, int X) {
        int N = users.length;
        int M = servers.length;

        // Sort users descending to reduce symmetry
        Integer[] U = new Integer[N];
        for (int i = 0; i < N; i++){
           U[i] = users[i];
        }
        Arrays.sort(U, Collections.reverseOrder());

        int maxMask = 1 << M;
        long[][] dp = new long[maxMask][N + 1];
        for (int i = 0; i < maxMask; i++) {
            Arrays.fill(dp[i], -1);
        }

        // Base state
        dp[0][0] = 0;

        for (int mask = 0; mask < maxMask; mask++) {
            for (int u = 0; u < N; u++) { // 0-based user index
                if (dp[mask][u] < 0) continue; 

                long demandWithOverhead = U[u] + X;

                // Case 1: assign to current server if possible
                if (mask != 0 && dp[mask][u]>= demandWithOverhead) {
                    // Workload splitting is allowed in dp[mask][u]-demandWithOverhead
                    dp[mask][u + 1] = Math.max(dp[mask][u + 1], dp[mask][u] - demandWithOverhead);
                }

                // Case 2: start a new server. Find the next available server that can accomodate the user
                // Doing it like this okay since we have a small value of M.
                for (int j = 0; j < M; j++) {
                    // Find the next server that can accomodate the workload
                    if ((mask & (1 << j)) == 0 && servers[j] >= demandWithOverhead) {
                        int nextMask = mask | (1 << j);
                        dp[nextMask][u + 1] = Math.max(dp[nextMask][u + 1], servers[j] - demandWithOverhead);
                    }
                }
            }
        }

        int answer = Integer.MAX_VALUE;
        for (int mask = 0; mask < maxMask; mask++) {
            if (dp[mask][N] >= 0) {
                int serverCount=Integer.bitCount(mask);
                answer = Math.min(answer, serverCount);
            }
        }

        return answer == Integer.MAX_VALUE ? -1 : answer;
    }

    public static void main(String[] args) {
        int[] users1 = {100, 100, 100};
        int[] servers1 = {150, 150, 150};
        int X1 = 20;
        System.out.println(minServers(users1, servers1, X1)); // 3

        int[] users2 = {50, 50, 50};
        int[] servers2 = {60, 60, 60, 60};
        int X2 = 20;
        System.out.println(minServers(users2, servers2, X2)); // -1

        int[] users3 = {5, 5};
        int[] servers3 = {25};
        int X3 = 10;
        System.out.println(minServers(users3, servers3, X3)); // -1
    }
}