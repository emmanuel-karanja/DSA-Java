package Graph;

/*
Problem Statement:

You are given a weighted directed graph with 'n' nodes labeled from 0 to n-1 and a list of edges
 where each edge is represented as (u, v, w) meaning there is an edge from node u to node v with weight w. 

You are also given an integer K, representing the maximum number of edges you are allowed to "skip".
 Skipping an edge means traversing that edge for free (weight 0) instead of its actual weight. 

Your task is to find the minimum possible distance from node 0 to node n-1 while being allowed to skip 
up to K edges along the path. 

Return -1 if there is no valid path.

Reasoning:

This is an extension of Dijkstra's shortest path with an additional state: number of edges skipped so far. 

- Use a 2D distance array: dist[node][skipsUsed] stores the shortest distance to 'node' with 'skipsUsed' edges skipped. 
- Use a priority queue to always expand the state with the smallest current distance.
- For each edge u->v with weight w:
    1. Relax normally: if dist[u][used] + w < dist[v][used], update dist[v][used].
    2. Relax by skipping: if used < K and dist[u][used] + 0 < dist[v][used+1], update dist[v][used+1].
*/

import java.util.*;

public class ShortestPathWithSkips {

    static class Edge {
        int to, weight;
        Edge(int to, int weight) { this.to = to; this.weight = weight; }
    }

    static class State implements Comparable<State> {
        int node, skipsUsed, dist;
        State(int node, int skipsUsed, int dist) {
            this.node = node; this.skipsUsed = skipsUsed; this.dist = dist;
        }
        public int compareTo(State other) { return Integer.compare(this.dist, other.dist); }
    }

    public static int shortestPathWithSkips(int n, int[][] edges, int K) {
        List<List<Edge>> graph = new ArrayList<>();
        for (int i = 0; i < n; i++) graph.add(new ArrayList<>());
        for (int[] e : edges) graph.get(e[0]).add(new Edge(e[1], e[2]));

        int[][] dist = new int[n][K+1];
        for (int i = 0; i < n; i++) Arrays.fill(dist[i], Integer.MAX_VALUE);
        dist[0][0] = 0;

        PriorityQueue<State> pq = new PriorityQueue<>();
        pq.offer(new State(0, 0, 0));

        while (!pq.isEmpty()) {
            State curr = pq.poll();
            int u = curr.node, used = curr.skipsUsed, d = curr.dist;

            if (d > dist[u][used]) continue;

            for (Edge e : graph.get(u)) {
                int v = e.to, w = e.weight;

                // Relax normally: u + w < v
                if (dist[u][used] + w < dist[v][used]) {
                    dist[v][used] = dist[u][used] + w;
                    pq.offer(new State(v, used, dist[v][used]));
                }

                // Relax by skipping: u + 0 < v (if skips left)
                if (used < K && dist[u][used] < dist[v][used + 1]) {
                    dist[v][used + 1] = dist[u][used];
                    pq.offer(new State(v, used + 1, dist[v][used + 1]));
                }
            }
        }

        int ans = Integer.MAX_VALUE;
        for (int used = 0; used <= K; used++) ans = Math.min(ans, dist[n-1][used]);
        return ans == Integer.MAX_VALUE ? -1 : ans;
    }

    public static void main(String[] args) {
        int n = 5;
        int[][] edges = {{0,1,10},{1,2,10},{2,4,10},{0,3,5},{3,4,100}};
        int K = 1;
        System.out.println(shortestPathWithSkips(n, edges, K)); // Output: 25
    }
}
