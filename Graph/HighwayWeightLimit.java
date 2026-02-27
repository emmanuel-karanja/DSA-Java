package Graph;

import java.util.*;

/**
 * Problem:
 * You are driving a truck from city A to city B. Each road has a weight limit.
 * You want to find a path such that the minimum weight limit on the path is maximized
 * (so you can carry the heaviest load possible).
 *
 * Approach:
 * - Use Binary Search on Answer (BSOA) + BFS.
 * - BSOA: The answer (maximum possible truck weight) lies between the minimum and maximum edge weights.
 * - BFS: Check if there exists a path from start to end using only edges >= candidate weight.
 */
public class HighwayWeightLimit {

    public int maximizeTruckWeight(int n, int[][] edges, int start, int end) {
        // Step 1: Find min and max edge weights to narrow BSOA
        int lo = Integer.MAX_VALUE;
        int hi = Integer.MIN_VALUE;
        for (int[] e : edges) {
            int w = e[2]; // edge = [u, v, weight]
            lo = Math.min(lo, w);
            hi = Math.max(hi, w);
        }

        // Step 2: Build adjacency list for BFS
        List<int[]>[] adj = new List[n];
        for (int i = 0; i < n; i++) adj[i] = new ArrayList<>();
        for (int[] e : edges) {
            int u = e[0], v = e[1], w = e[2];
            adj[u].add(new int[]{v, w});
            adj[v].add(new int[]{u, w}); // undirected
        }

        // Step 3: BSOA
        int ans = lo; // at least the smallest weight
        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            if (canReach(mid, start, end, adj, n)) {
                ans = mid;      // candidate works, try heavier
                lo = mid + 1;
            } else {
                hi = mid - 1;   // too heavy, reduce
            }
        }
        return ans;
    }

    // BFS: check if a path exists from start → end using edges >= weightLimit
    private boolean canReach(int weightLimit, int start, int end, List<int[]>[] adj, int n) {
        boolean[] visited = new boolean[n];
        Queue<Integer> q = new LinkedList<>();
        visited[start] = true;
        q.offer(start);

        while (!q.isEmpty()) {
            int u = q.poll();
            if (u == end) return true;
            for (int[] neighbor : adj[u]) {
                int v = neighbor[0], w = neighbor[1];
                if (!visited[v] && w >= weightLimit) {
                    visited[v] = true;
                    q.offer(v);
                }
            }
        }
        return false;
    }

    // Driver main
    public static void main(String[] args) {
        HighwayWeightLimit solver = new HighwayWeightLimit();

        int n = 5; // cities 0..4
        int[][] edges = {
            {0, 1, 5},
            {1, 2, 3},
            {2, 3, 4},
            {3, 4, 2},
            {0, 4, 1},
            {1, 3, 6}
        };

        int start = 0, end = 3;
        int maxWeight = solver.maximizeTruckWeight(n, edges, start, end);
        System.out.println("Maximum truck weight from " + start + " to " + end + " is: " + maxWeight);
    }
}