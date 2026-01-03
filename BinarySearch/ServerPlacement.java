package BinarySearch;

import java.util.Arrays;

/**
 * PROBLEM: Server Placement – Minimize Maximum Distance
 *
 * GIVEN:
 * - An array `positions[]` representing fixed positions where servers can be placed
 * - An integer `servers` representing the number of servers to place
 *
 * RULES:
 * 1. Servers must be placed at positions[i] only (no free placement in between)
 * 2. All servers must be placed
 * 3. The goal is to minimize the **maximum distance** between any two consecutive servers
 *
 * INTUITION:
 * - Maximum distance between servers is determined by the **largest gap** between consecutive placed servers
 * - We can binary search on candidate distances:
 *      - Lower bound = 0 (minimum possible max distance)
 *      - Upper bound = positions[n-1] - positions[0] (span of all positions)
 * - Feasibility check: Can we place all servers such that no gap exceeds candidate distance?
 *
 * MONOTONICITY:
 * - If a candidate distance is feasible (can place all servers ≤ maxDistance), then any larger distance is also feasible
 * - If a candidate distance is not feasible, any smaller distance is impossible
 * ⇒ Binary search applies
 *
 * TIME COMPLEXITY:
 * - O(n log(span)) where n = positions.length, span = max(positions) - min(positions)
 *
 * SPACE COMPLEXITY:
 * - O(1)
 */
public class ServerPlacement {

    public static int minimizeMaxDistance(int[] positions, int servers) {
        Arrays.sort(positions);
        int left = 0;  // minimum possible distance
        int right = positions[positions.length - 1] - positions[0];  // maximum possible distance
        int answer = right;

        while (left <= right) {
            int mid = left + (right - left) / 2;

            if (canPlaceServers(positions, servers, mid)) {
                // Feasible: try smaller max distance
                answer = mid;
                right = mid - 1;
            } else {
                // Not feasible: increase allowed max distance
                left = mid + 1;
            }
        }

        return answer;
    }

    // Check if we can place all servers so that no consecutive gap exceeds maxDistance
    private static boolean canPlaceServers(int[] positions, int servers, int maxDistance) {
        int placedServers = 1;
        int lastPos = positions[0];

        for (int i = 1; i < positions.length; i++) {
            if (positions[i] - lastPos > maxDistance) {
                // Need to place a server here
                placedServers++;
                lastPos = positions[i];
            }
        }

        return placedServers <= servers;
    }

    // Example usage
    public static void main(String[] args) {
        int[] positions = {1, 2, 4, 8, 9};
        int servers = 3;

        int minMaxDist = minimizeMaxDistance(positions, servers);
        System.out.println("Minimum maximum distance = " + minMaxDist);
        // Expected: 4 (place servers at 1, 4, 8 or 1, 4, 9)
    }
}
