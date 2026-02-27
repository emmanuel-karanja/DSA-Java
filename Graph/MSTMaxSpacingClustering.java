package Graph;

/*
Problem Statement:

Given N points and a distance function between them, divide the points into K clusters
such that the distance between the two closest points in **different clusters** is maximized.

Goal:
- Partition points into K clusters
- Maximize the **minimum distance between points in different clusters** (maximum spacing)

Approach:
1. Treat points as nodes and distances as weighted edges.
2. Construct all edges between pairs of points with their distances.
3. Sort edges in ascending order of distance.
4. Use Kruskal's algorithm to merge points into clusters:
   - Initially, each point is its own cluster.
   - Merge clusters in order of increasing distance.
   - Stop when exactly K clusters remain.
5. The **next smallest edge** that connects two different clusters is the **maximum spacing**.
*/

import java.util.*;

public class MSTMaxSpacingClustering {

    static class Edge implements Comparable<Edge> {
        int u, v;
        double dist;
        Edge(int u, int v, double dist) {
            this.u = u;
            this.v = v;
            this.dist = dist;
        }
        public int compareTo(Edge other) {
            return Double.compare(this.dist, other.dist);
        }
    }

    // Union-Find (Disjoint Set)
    static class UnionFind {
        int[] parent, rank;
        UnionFind(int n) {
            parent = new int[n];
            rank = new int[n];
            for (int i = 0; i < n; i++) parent[i] = i;
        }
        int find(int x) {
            if (parent[x] != x) parent[x] = find(parent[x]);
            return parent[x];
        }
        boolean union(int x, int y) {
            int px = find(x), py = find(y);
            if (px == py) return false;
            if (rank[px] < rank[py]) parent[px] = py;
            else if (rank[px] > rank[py]) parent[py] = px;
            else {
                parent[py] = px;
                rank[px]++;
            }
            return true;
        }
    }

    // Function to compute maximum spacing for K clusters
    public static double maxSpacingKClustering(double[][] points, int K) {
        int n = points.length;
        List<Edge> edges = new ArrayList<>();

        // Build all pairwise edges
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                double d = distance(points[i], points[j]);
                edges.add(new Edge(i, j, d));
            }
        }

        Collections.sort(edges); // Sort edges by distance
        UnionFind uf = new UnionFind(n);
        int clusters = n;

        // Merge clusters until we have exactly K clusters
        for (Edge e : edges) {
            if (uf.find(e.u) != uf.find(e.v)) {
                if (clusters <= K) {
                    // Stop when we have K clusters
                    return e.dist; // next edge distance = maximum spacing
                }
                uf.union(e.u, e.v);
                clusters--;
            }
        }

        return -1; // if K clusters not possible (should not happen if K <= N)
    }

    // Euclidean distance in 2D
    private static double distance(double[] a, double[] b) {
        double dx = a[0] - b[0];
        double dy = a[1] - b[1];
        return Math.sqrt(dx * dx + dy * dy);
    }

    public static void main(String[] args) {
        // Example points in 2D
        double[][] points = {
            {0,0}, {0,1}, {1,0}, {1,1}, {3,3}, {4,3}
        };
        int K = 3;

        double maxSpacing = maxSpacingKClustering(points, K);
        System.out.println("Maximum spacing for " + K + " clusters: " + maxSpacing);
    }
}