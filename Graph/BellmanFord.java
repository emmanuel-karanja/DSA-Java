package Graph;

import java.util.*;

/**
 * Problem Statement:
 * Given a directed weighted graph with possibly negative edge weights, find the shortest path from a source node to all nodes.
 * If a negative cycle exists, detect it.
 * 
 * Approach:
 * - Initialize distance array with Integer.MAX_VALUE for all nodes except source.
 * - Relax all edges V-1 times (V = number of vertices).
 * - After relaxation, check all edges again to detect negative cycles.
 * - Bellman-Ford works even with negative weights, unlike Dijkstra.
 */

class Edge {
    int u, v, w;
    Edge(int u, int v, int w) {
        this.u = u;
        this.v = v;
        this.w = w;
    }
}

public class BellmanFord {

    public static int[] bellmanFord(int V, int[][] edgesArray, int src) {

        // Construct the edges

        List<Edge> edges = new ArrayList<>();
        for (int[] e : edgesArray) {
            edges.add(new Edge(e[0], e[1], e[2]));
        }
    
        
       // Initialize the state. i.e. V nodes, we have V states 
        int[] dist = new int[V];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[src] = 0;

        // Relax all edges V-1 times
        for (int i = 0; i < V - 1; i++) {
            for (Edge e : edges) {
                if (dist[e.u] != Integer.MAX_VALUE && 
                    dist[e.u] + e.w < dist[e.v]) {
                    
                    dist[e.v] = dist[e.u] + e.w;
                }
            }
        }

        // Check for negative-weight cycles
        for (Edge e : edges) {
            if (dist[e.u] != Integer.MAX_VALUE && dist[e.u] + e.w < dist[e.v]) {
                System.out.println("Graph contains a negative-weight cycle");
                return null; // or throw exception
            }
        }

        return dist;
    }


    public static void main(String[] args) {
        int V = 5;
        int[][] edgeArray = {
            {0, 1, 6},
            {0, 2, 7},
            {1, 2, 8},
            {1, 3, 5},
            {1, 4, -4},
            {2, 3, -3},
            {2, 4, 9},
            {3, 1, -2},
            {4, 0, 2},
            {4, 3, 7}
        };

        int src = 0;

        int[] distances = bellmanFord(V, edgeArray, src);

        if (distances != null) {
            System.out.println("Shortest distances from node " + src + ":");
            for (int i = 0; i < distances.length; i++) {
                System.out.println("Node " + i + " -> " + distances[i]);
            }
        }
    }
}
