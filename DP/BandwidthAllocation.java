package DP;

/**
 * PROBLEM STATEMENT: BANDWIDTH ALLOCATION
 * You have N users with bandwidth demands U[i] and M servers with capacities C[j].
 * You can split a user's request across multiple servers. However, each time a server 
 * is used for a specific user (whether it's the whole request or a fragment), 
 * it incurs a "Connection Overhead" of X bandwidth.
 * * GOAL: Find the minimum number of servers required to satisfy all users.
 * * REASONING & TRIAGE:
 * 1. Why not Greedy? 
 * The overhead X breaks the Greedy Choice property. Splitting a large user creates 
 * "ghost demand" (2X). A greedy choice might split a large user to fill a server, 
 * thereby burning capacity that could have fit two smaller users without overhead.
 * * 2. Why not Binary Search Over Answers (BSOA)?
 * While the number of servers is monotonic, the "Feasibility Check" (Can I fit N users 
 * into K servers?) is a variation of the NP-Hard Bin Packing problem.
 * * 3. The Winner: Bitmask DP
 * With M servers (usually small, M <= 16), we can represent the state as a bitmask.
 * State: dp[mask] = The maximum number of users fully satisfied, plus the amount 
 * of the *next* user satisfied, using the subset of servers in 'mask'.
 * * We want to maximize "Progress" for every subset of servers.
 * Progress is represented as a custom 'State' object: {count, current_user_filled}.
 */

import java.util.*;

public class BandwidthAllocation {

    static class Progress implements Comparable<Progress> {
        int usersCount;      // How many users are fully satisfied
        long currentFilled;   // How much of the NEXT user (users[usersCount]) is satisfied

        Progress(int u, long f) {
            this.usersCount = u;
            this.currentFilled = f;
        }

        // We want to maximize usersCount first, then maximize currentFilled
        @Override
        public int compareTo(Progress other) {
            if (this.usersCount != other.usersCount) {
                return Integer.compare(this.usersCount, other.usersCount);
            }
            return Long.compare(this.currentFilled, other.currentFilled);
        }
    }

    public static int minServers(int[] users, int[] servers, int X) {
        int N = users.length;
        int M = servers.length;
        
        // dp[mask] stores the best Progress achievable using servers in the mask
        Progress[] dp = new Progress[1 << M];
        Arrays.fill(dp, new Progress(-1, -1));
        dp[0] = new Progress(0, 0);

        // Iterate through all possible subsets of servers
        for (int mask = 0; mask < (1 << M); mask++) {
            if (dp[mask].usersCount == -1) continue;

            // Try adding one more server (j) that isn't in the current mask
            for (int j = 0; j < M; j++) {
                if ((mask & (1 << j)) == 0) {
                    int nextMask = mask | (1 << j);
                    Progress nextProgress = calculateNextProgress(dp[mask], servers[j], users, X);
                    
                    if (dp[nextMask].usersCount == -1 || nextProgress.compareTo(dp[nextMask]) > 0) {
                        dp[nextMask] = nextProgress;
                    }
                }
            }
        }

        // Find the smallest mask that satisfies all N users
        int minServers = Integer.MAX_VALUE;
        for (int mask = 0; mask < (1 << M); mask++) {
            if (dp[mask].usersCount == N) {
                minServers = Math.min(minServers, Integer.bitCount(mask));
            }
        }

        return minServers == Integer.MAX_VALUE ? -1 : minServers;
    }

    private static Progress calculateNextProgress(Progress current, int serverCap, int[] users, int X) {
        int uIdx = current.usersCount;
        long filled = current.currentFilled;
        long remainingCap = serverCap;

        // If we were in the middle of a user, we must continue it in this server
        // This costs X bandwidth for the overhead in THIS server.
        if (filled > 0) {
            long userNeeds = users[uIdx] - filled;
            long costWithOverhead = X; 
            
            if (remainingCap <= costWithOverhead) {
                // Not even enough to pay the overhead to start/continue
                return current; 
            }
            
            remainingCap -= costWithOverhead;
            long canTake = Math.min(userNeeds, remainingCap);
            remainingCap -= canTake;
            filled += canTake;

            if (filled == users[uIdx]) {
                uIdx++;
                filled = 0;
            } else {
                // Server exhausted while still on the same user
                return new Progress(uIdx, filled);
            }
        }

        // Try to fit new users into the remaining capacity of this server
        while (uIdx < users.length) {
            long costToStart = X + users[uIdx];
            if (remainingCap >= costToStart) {
                // Fits the whole user + overhead
                remainingCap -= costToStart;
                uIdx++;
            } else if (remainingCap > X) {
                // Can only fit a fragment (remainingCap - X)
                filled = remainingCap - X;
                return new Progress(uIdx, filled);
            } else {
                // Cannot even start a new user
                break;
            }
        }

        return new Progress(uIdx, filled);
    }

    public static void main(String[] args) {
        // Driver Test Case
        int[] users = {100, 100, 100};
        int[] servers = {150, 150, 150};
        int X = 20; // Overhead

        // Reasoning for this case:
        // Total bandwidth = 300. Total capacity = 450.
        // User 1 in Server 1: 100 + 20 (X) = 120. Server 1 has 30 left.
        // If we split User 2: 30-20(X) = 10 units.
        // User 2 now needs 90 more units + 20(X) in Server 2 = 110.
        // Server 2 has 40 left... and so on.

        int result = minServers(users, servers, X);
        System.out.println("Minimum servers required: " + (result == -1 ? "Impossible" : result));
        
        // Complex Case: Large overhead making splits very expensive
        int[] users2 = {50, 50, 50};
        int[] servers2 = {60, 60, 60, 60};
        int X2 = 20; 
        // 50+20 = 70. A single user doesn't fit in one server.
        // A split costs 20 extra on the second server. 
        System.out.println("Complex Case result: " + minServers(users2, servers2, X2));
    }
}