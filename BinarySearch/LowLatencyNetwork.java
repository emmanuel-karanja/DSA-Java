package BinarySearch;


import java.util.*;

/**
 * Problem Statement:
 * -----------------
 * You are given a network of N computers connected by M bidirectional cables.
 * Each cable has two properties: latency and bandwidth.
 *
 * You want to send a file from Computer A to Computer B.
 * The path must support a minimum bandwidth of at least K.
 * Among all such paths, you want the path with the minimal possible maximum latency (the slowest edge in the path).
 *
 * Input:
 * - int N: number of computers
 * - int[][] edges: array of edges where each edge = [u, v, latency, bandwidth]
 * - int A, B: source and destination computers
 * - int K: minimum required bandwidth
 *
 * Output:
 * - int: minimal possible maximum latency on a feasible path, or -1 if impossible
 *
 * Intuition / Reasoning:
 * ----------------------
 * - Observation 1: We can treat bandwidth as a hard constraint (edges with bandwidth < K are unusable).
 * - Observation 2: Maximum latency along a path is the variable to minimize.
 * - Observation 3: Given a candidate latency L, we can check feasibility using BFS/DFS:
 *      - Keep only edges with latency <= L and bandwidth >= K
 *      - Can we reach B from A?
 * - Observation 4: If feasible for L, feasible for any larger L → monotone
 * - Apply Binary Search on Answer (minimum feasible maximum latency).
 * 
 * NOTE:
 * When a problem looks like a graph + DP + optimization, the trick is not to jump into coding.
Instead, ask: “Can I lock one variable, check feasibility, and use monotone search?”
This is exactly how BSOA escapes the combinatorial explosion.
 */

public class LowLatencyNetwork {

    static class Edge {
        int to, latency, bandwidth;
        Edge(int to, int latency, int bandwidth) {
            this.to = to;
            this.latency = latency;
            this.bandwidth = bandwidth;
        }
    }

    public static int minimalMaxLatency(int N, int[][] edges, int A, int B, int K) {
        List<Edge>[] graph = new ArrayList[N];
        for (int i = 0; i < N; i++) graph[i] = new ArrayList<>();

        int maxLatency = 0;

        // hi is the max latency of any path
        for (int[] e : edges) {
            int u = e[0], v = e[1];
            int latency = e[2];
            int bandwidth = e[3];
            graph[u].add(new Edge(v, latency, bandwidth));
            graph[v].add(new Edge(u, latency, bandwidth));
            maxLatency = Math.max(maxLatency, latency);
        }

        int lo = 0, hi = maxLatency;
        int result = -1;

        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            if (isFeasible(graph, N, A, B, K, mid)) {
                result = mid;
                hi = mid - 1; // try smaller maximum latency
            } else {
                lo = mid + 1;
            }
        }
        return result;
    }

    //*Basically: Without exceeding current maxLatency selected from lo to hi, can we get from node A to B? Why do we
    // use BFS here? Because the latency is positive. */
    private static boolean isFeasible(List<Edge>[] graph, int N, int A, int B, int K, int maxLatency) {
        boolean[] visited = new boolean[N];
        Queue<Integer> q = new LinkedList<>();
        q.add(A);
        visited[A] = true;

        while (!q.isEmpty()) {
            int node = q.poll();
            if (node == B) return true;
            for (Edge e : graph[node]) {
                if (!visited[e.to] && e.bandwidth >= K && e.latency <= maxLatency) {
                    visited[e.to] = true;
                    q.add(e.to);
                }
            }
        }
        return false;
    }

    // Example usage
    public static void main(String[] args) {
        int N = 5;
        int[][] edges = {
            {0, 1, 5, 100},
            {1, 2, 10, 200},
            {0, 2, 15, 150},
            {2, 3, 5, 100},
            {3, 4, 10, 200}
        };
        int A = 0, B = 4, K = 150;

        int minMaxLatency = minimalMaxLatency(N, edges, A, B, K);
        System.out.println("Minimal maximum latency: " + minMaxLatency); // Output: 15
    }
}