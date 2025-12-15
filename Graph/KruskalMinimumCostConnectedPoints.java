package Graph;

import java.util.*;

/**
 * Minimum cost to connect all points using Kruskal's MST.
 * 
 * KEY:
 * 
 * In a complete graph, every point is connected to every other point.
 * 
 * The points are an array of points.
 * 
 * So:
 * 
 * 1. Build a complete graph i.e. every point connected to every other. Meaning creating edges between points.
 * 2. Sort the edges by weight in ascending order.
 * 3. Union(e.u,e.v) and ensure edgeCount <=n-1 what we are doing is adding u to rhe same union as v
 *    if u is not alreadya part of it and only adjust weight then
 * 
 * Edge counting:
 * 
 * If you have n points, you'll need n-1 edges to connect them, for an nxn grid
 * n(n-1) but you are counting the edges twice i.e. (i,j)->(j,i) and (j,i)->(i,j) and since the
 * the graph is undirected, we divide by 2.
 * 
 * 
 * 
 * n(n-1)/2 i.e. (n^2-n)/2
 */



class Edge {
    int u;
    int v;
    int cost;

    Edge(int u, int v, int cost) {
        this.u = u;
        this.v = v;
        this.cost = cost;
    }
}

class UnionFind {
    int[] parent;
    int[] rank;

    UnionFind(int n) {
        parent = new int[n];
        rank = new int[n];
        for (int i = 0; i < n; i++) {
            parent[i] = i;
        }
    }

    int find(int x) {
        if (parent[x] != x) {
            parent[x] = find(parent[x]); // path compression
        }
        return parent[x];
    }

    boolean union(int x, int y) {
        int px = find(x);
        int py = find(y);

        if (px == py) return false; // cycle

        if (rank[px] < rank[py]) {  
            parent[px] = py;
        } else if (rank[py] < rank[px]) {
            parent[py] = px;
        } else {
            parent[py] = px;
            //we made parent of py to be px that's why we swell the rank of px
            rank[px]++;
        }
        return true;
    }
}

public class KruskalMinimumCostConnectedPoints {

    private static int manhattanDist(int[][] points, int i, int j) {
        return Math.abs(points[i][0] - points[j][0])
             + Math.abs(points[i][1] - points[j][1]);
    }

    public static int getMinCost(int[][] points) {
        //Get
        int n = points.length;
        List<Edge> edges = new ArrayList<>();

        // Build all edges (complete graph)
        // The graph is undirected: edge (i, j) is the same as (j, i). Hence we start from j=i+1 otherwise,
        // we'd generate each edge twice
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) { //from position i in the array we are only interested in the i+1 onwards to n-1
                edges.add(new Edge(i, j, manhattanDist(points, i, j)));
            }
        }

        // Sort edges by cost
        edges.sort(Comparator.comparingInt(e -> e.cost));

        UnionFind uf = new UnionFind(n);
        int totalCost = 0;
        int edgesUsed = 0;

        for (Edge e : edges) {
            if (uf.union(e.u, e.v)) {
                totalCost += e.cost;
                edgesUsed++;
                if (edgesUsed == n - 1) {
                    break; // MST complete
                }
            }
        }

        return totalCost;
    }

    public static void main(String[] args) {
        // Example 1: 5 points
        int[][] points1 = {
            {0, 0},
            {2, 2},
            {3, 10},
            {5, 2},
            {7, 0}
        };
        int minCost1 = KruskalMinimumCostConnectedPoints.getMinCost(points1);
        System.out.println("Minimum cost to connect points1: " + minCost1); // Expected: 20

        // Example 2: 3 points
        int[][] points2 = {
            {0, 0},
            {1, 1},
            {1, 0}
        };
        int minCost2 = KruskalMinimumCostConnectedPoints.getMinCost(points2);
        System.out.println("Minimum cost to connect points2: " + minCost2); // Expected: 2

        // Example 3: Single point (edge case)
        int[][] points3 = {
            {5, 5}
        };
        int minCost3 = KruskalMinimumCostConnectedPoints.getMinCost(points3);
        System.out.println("Minimum cost to connect points3: " + minCost3); // Expected: 0
    }
}
