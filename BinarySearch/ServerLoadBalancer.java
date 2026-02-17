package BinarySearch;
/**
 * Problem Statement:
 * -----------------
 * You are given N servers in a line, each with a capacity Ci.
 * You want to partition the servers into at most K contiguous groups (tasks).
 * The goal is to minimize the maximum total capacity of any group.
 * 
 * Input:
 * - int[] capacities: array of server capacities
 * - int K: number of tasks/groups
 * 
 * Output:
 * - int: minimal possible value of the maximum group sum
 * 
 * Intuition / Reasoning:
 * ----------------------
 * - This is a classic example of Binary Search on Answer (BSOA) with a greedy feasibility check.
 * - Observation 1: The minimal maximum load must be between max(capacities) and sum(capacities).
 * - Observation 2: Given a candidate max load L, we can greedily assign servers to groups:
 *      - Start a group, add servers until adding the next exceeds L, then start a new group.
 *      - Count how many groups are used. If <= K, L is feasible.
 * - Binary search over the range of possible L to find the minimal feasible value.
 * - DP is unnecessary because capacities are positive and greedy assignment preserves feasibility.
 */

public class ServerLoadBalancer {

    public static int minimizeMaxLoad(int[] capacities, int K) {
        int lo = 0, hi = 0;
        for (int c : capacities) {
            lo = Math.max(lo, c); // The max capacity of a single server
            hi += c;              // Sum of all servers
        }

        int result = hi;
        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            if (isFeasible(capacities, K, mid)) {
                result = mid;       // feasible, try smaller maximum
                hi = mid - 1;
            } else {
                lo = mid + 1;       // not feasible, try larger maximum
            }
        }
        return result;
    }

    private static boolean isFeasible(int[] capacities, int K, int maxLoad) {
        int groupsUsed = 1;
        int currentSum = 0;
        for (int c : capacities) {
            if (currentSum + c > maxLoad) {  //if the current combined load is greater than maxload.
                groupsUsed++;
                currentSum = c;
                if (groupsUsed > K) return false;
            } else {
                currentSum += c;
            }
        }
        return true;
    }

    // Example usage
    public static void main(String[] args) {
        int[] capacities = {10, 20, 30, 40, 50};
        int K = 3;
        int minMaxLoad = minimizeMaxLoad(capacities, K);
        System.out.println("Minimal maximum load: " + minMaxLoad); // Output: 60
    }
}