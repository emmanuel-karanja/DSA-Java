package Graph;

/*
Problem Statement:

You are given n houses. For each house i (0-based index):
- You can build a well at house i for a cost W[i].
- You can lay a pipe between house i and house j for a cost P[i][j].

Your goal is to supply water to every house at the minimum total cost.  
You can either dig wells or connect houses with pipes.  

Example:
W = [1, 2, 2]
P = [
  [0, 1, 2],
  [1, 0, 1],
  [2, 1, 0]
]
Minimum cost: 3 (well at house 0 + pipes connecting house 1 and 2)

Reasoning / Approach:

1. Treat each house as a node in a graph.
2. Introduce a virtual node "n" representing the water source (the option to dig a well).
3. Add an edge from virtual node n to house i with weight W[i].
   - Choosing this edge in MST means digging a well at house i.
4. Add edges between houses i and j with weight P[i][j].
   - Choosing this edge in MST means laying a pipe between i and j.
5. Use Minimum Spanning Tree (MST) algorithm (Kruskal's) to select edges that connect all houses with minimal cost.
6. The MST will automatically choose the optimal combination of wells and pipes.

This reduces the problem to a classical MST problem on a graph with n+1 nodes (houses + virtual well node).
*/

import java.util.*;

public class MSTWaterSupply {

    // Edge class representing a connection with a cost
    static class Edge implements Comparable<Edge> {
        int u, v, cost;
        Edge(int u, int v, int cost) {
            this.u = u;
            this.v = v;
            this.cost = cost;
        }
        public int compareTo(Edge other) {
            return this.cost - other.cost;
        }
    }

    // Union-Find (Disjoint Set) to detect cycles in Kruskal's
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

    // Function to compute minimum water supply cost
    public static int minCost(int[] W, int[][] P) {
        int n = W.length;
        List<Edge> edges = new ArrayList<>();

        // Connect virtual node (n) to each house with well cost
        for (int i = 0; i < n; i++) {
            edges.add(new Edge(n, i, W[i]));
        }

        // Connect houses with pipe costs
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                edges.add(new Edge(i, j, P[i][j]));
            }
        }

        Collections.sort(edges); // Sort edges by cost

        
        UnionFind uf = new UnionFind(n + 1); // Include virtual node
        int totalCost = 0;

        for (Edge e : edges) {
            if (uf.union(e.u, e.v)) {
                totalCost += e.cost;
            }
        }
        return totalCost;
    }

    // Driver main function
    public static void main(String[] args) {
        int[] W = {1, 2, 2}; // cost to build wells
        int[][] P = {        // cost to lay pipes
            {0, 1, 2},
            {1, 0, 1},
            {2, 1, 0}
        };

        int result = minCost(W, P);
        System.out.println("Minimum total cost to supply water: " + result);
    }
}