package BinarySearch;

/**
 * PROBLEM STATEMENT:
 * Given a network of N nodes and M cables with specific latencies, find a path 
 * from Node A to Node B such that the MAXIMUM latency of any single cable on 
 * the path is MINIMIZED.
 * * DETAILED REASONING :
 * 1. THE SIGNAL: "Minimize the Maximum." This is the primary indicator for BSOA.
 * 2. MONOTONICITY: If we can reach B from A using cables with latency <= K, 
 * we can certainly reach it using cables <= K + 1. There is a clear "pass/fail" 
 * threshold.
 * 3. ELIMINATION: 
 * - Greedy/Dijkstra: Could work (modified), but BSOA is more robust for 
 * bottleneck problems.
 * - DP: Fails. No overlapping subproblems or "summation" logic required.
 * 4. FEASIBILITY GATE: A simple BFS. We ignore all cables > current "guess".
 * * TIME COMPLEXITY: O(log(Max_Latency) * (N + M))
 * SPACE COMPLEXITY: O(N + M) for the Adjacency List.
 */

import java.util.*;

public class NetworkJitterMinimizer {
    
    static class Edge {
        int to, latency;
        Edge(int to, int latency) {
            this.to = to;
            this.latency = latency;
        }
    }

    public int findMinMaxLatency(int n, int[][] cables, int startNode, int endNode) {
        // 1. Build Adjacency List
        List<List<Edge>> adj = new ArrayList<>();
        int maxL = 0, minL = Integer.MAX_VALUE;
        for (int i = 0; i <= n; i++) adj.add(new ArrayList<>());
        

        for (int[] cable : cables) {
            adj.get(cable[0]).add(new Edge(cable[1], cable[2]));
            adj.get(cable[1]).add(new Edge(cable[0], cable[2]));
            maxL = Math.max(maxL, cable[2]);
            minL = Math.min(minL, cable[2]);
        }

        // 2. Binary Search on the Latency value
        int low = minL, high = maxL;
        int result = -1;

        while (low <= high) {
            int mid = low + (high - low) / 2;
            
            if (canReach(n, adj, startNode, endNode, mid)) {
                result = mid;      // This bottleneck works, try to find a smaller one
                high = mid - 1;
            } else {
                low = mid + 1;     // Cannot reach, need to allow higher latency cables
            }
        }
        return result;
    }

    // Feasibility Test: Basic BFS that ignores cables > limit
    private boolean canReach(int n, List<List<Edge>> adj, int start, int end, int limit) {
        Queue<Integer> queue = new ArrayDeque<>();
        boolean[] visited = new boolean[n + 1];

        queue.add(start);
        visited[start] = true;

        while (!queue.isEmpty()) {
            int curr = queue.poll();
            if (curr == end) return true;

            for (Edge edge : adj.get(curr)) {
                // Latency check
                if (!visited[edge.to] && edge.latency <= limit) {
                    visited[edge.to] = true;
                    queue.add(edge.to);
                }
            }
        }
        return false;
    }

    public static void main(String[] args) {
        NetworkJitterMinimizer solver = new NetworkJitterMinimizer();
        int[][] cables = {
            {1, 2, 10}, {2, 3, 50}, {1, 3, 100}, {3, 4, 20}
        };
        // Path 1-2-3-4 has max latency 50. Path 1-3-4 has max latency 100.
        // Minimized max latency should be 50.
        System.out.println("Minimized Bottleneck Latency: " + solver.findMinMaxLatency(4, cables, 1, 4));
    }
}