package Graph;

/**
 * PROBLEM: Shortest Path in a Node-Colored Graph.
 * Rule: Adjacent nodes must have different colors.
 * * REASONING:
 * 1. IMMUTABLE PROPERTY: Since color is a property of the node, not the path,
 * the state space does not expand. State is just (u).
 * 2. GRAPH FILTERING: We simply treat edges between nodes of the same color
 * as if they don't exist.
 * 3. ENGINE: Standard Dijkstra.
 */

import java.util.*;

public class ShortestPathWithAlternatingColors {

    public int findShortestPath(int n, int[] nodeColors, List<int[]> edges, int start, int end) {
        // 1. Build Adjacency List: adj[u] -> {neighbor, weight}
        List<int[]>[] adj = new ArrayList[n];
        for (int i = 0; i < n; i++) adj[i] = new ArrayList<>();
        
        for (int[] edge : edges) {
            int u = edge[0];
            int v = edge[1];
            int weight = edge[2];
            
            // Only add the edge if the nodes have different colors
            if (nodeColors[u] != nodeColors[v]) {
                adj[u].add(new int[]{v, weight});
                adj[v].add(new int[]{u, weight}); // Assuming undirected
            }
        }

        // 2. Standard Dijkstra
        int[] dist = new int[n];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[start] = 0;

        PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a[1]));
        pq.add(new int[]{start, 0});

        while (!pq.isEmpty()) {
            int[] curr = pq.poll();
            int u = curr[0];
            int d = curr[1];

            if (u == end) return d;
            if (d > dist[u]) continue;

            for (int[] neighbor : adj[u]) {
                int v = neighbor[0];
                int weight = neighbor[1];

                if (dist[u] + weight < dist[v]) {
                    dist[v] = dist[u] + weight;
                    pq.add(new int[]{v, dist[v]});
                }
            }
        }

        return -1; // No valid path
    }
}