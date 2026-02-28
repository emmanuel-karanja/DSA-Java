package BinarySearch;

import java.util.*;

/**
 * Problem:
 * You are given a graph of n cities (0-indexed) connected by roads.
 * Each road has a weight limit representing the maximum load a truck can carry.
 * You want to find a path from city 'start' to city 'end' such that the
 * minimum weight limit along the path (bottleneck) is maximized.
 *
 * Approach:
 * 1. Identify the search space: minEdgeWeight .. maxEdgeWeight.
 * 2. Binary search on candidate weight 'mid':
 *      - Check if there is a path from start to end using only edges with weight >= mid.
 *      - If feasible, try higher (lo = mid + 1). If not, try lower (hi = mid - 1).
 * 3. BFS or DFS is used for the feasibility check.
 *
 * Complexity:
 * - Let W = maxEdgeWeight, V = number of nodes, E = number of edges
 * - O((V + E) * log W)
 */

public class MaxBottleneckPathBSOA {

    static class Edge {
        int to, weight;
        Edge(int to, int weight) {
            this.to = to;
            this.weight = weight;
        }
    }

    public static int maxBottleneck(int n, int[][] edges, int start, int end) {
        // Build adjacency list
        List<List<Edge>> graph = new ArrayList<>();
        for (int i = 0; i < n; i++) graph.add(new ArrayList<>());
        int minWeight = Integer.MAX_VALUE, maxWeight = Integer.MIN_VALUE;

        for (int[] e : edges) {
            int u = e[0], v = e[1], w = e[2];
            graph.get(u).add(new Edge(v, w));
            graph.get(v).add(new Edge(u, w)); // undirected
            minWeight = Math.min(minWeight, w);
            maxWeight = Math.max(maxWeight, w);
        }

        // Binary search on answer
        int lo = minWeight, hi = maxWeight;
        int ans = minWeight;

        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            if (canReach(start, end, graph, mid, n)) {
                ans = mid;        // feasible, try higher
                lo = mid + 1;
            } else {
                hi = mid - 1;     // not feasible, try lower
            }
        }
        return ans;
    }

    private static boolean canReach(int start, int end, List<List<Edge>> graph, int weightLimit, int n) {
        boolean[] visited = new boolean[n];
        Queue<Integer> q = new ArrayDeque<>();
        visited[start] = true;
        q.offer(start);

        while (!q.isEmpty()) {
            int u = q.poll();
            if (u == end) return true;
            for (Edge e : graph.get(u)) {
                if (!visited[e.to] && e.weight >= weightLimit) {
                    visited[e.to] = true;
                    q.offer(e.to);
                }
            }
        }
        return false;
    }

    // Driver
    public static void main(String[] args) {
        int n = 5;
        int[][] edges = {
            {0, 1, 10},
            {1, 2, 8},
            {2, 4, 10},
            {0, 3, 5},
            {3, 4, 100}
        };
        int start = 0, end = 4;

        int maxLoad = maxBottleneck(n, edges, start, end);
        System.out.println("Maximum load the truck can carry: " + maxLoad); // Expected: 8
    }
}