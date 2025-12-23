package Graph;

import java.util.*;

public class ConnectedComponentsUF {

    // Union-Find (Disjoint Set Union) class
    static class UnionFind {
        int[] parent;
        int[] rank;

        // Initialize n nodes, each node is its own parent
        UnionFind(int n) {
            parent = new int[n];
            rank = new int[n];
            for (int i = 0; i < n; i++) parent[i] = i;
        }

        // Find with path compression
        int find(int x) {
            if (parent[x] != x) parent[x] = find(parent[x]);
            return parent[x];
        }

        // Union by rank
        void union(int x, int y) {
            int px = find(x);
            int py = find(y);
            if (px == py) return; // Already in the same component

            if (rank[px] < rank[py]) parent[px] = py;
            else if (rank[px] > rank[py]) parent[py] = px;
            else {
                parent[py] = px;
                rank[px]++;
            }
        }
    }

    // Function to get connected components
    public static List<List<Integer>> getConnectedComponents(int n, int[][] edges) {
        UnionFind uf = new UnionFind(n);

        // Merge nodes according to edges
        for (int[] edge : edges) {
            uf.union(edge[0], edge[1]);
        }

        // Map from root to list of nodes in the component
        Map<Integer, List<Integer>> componentsMap = new HashMap<>();
        for (int i = 0; i < n; i++) {
            int root = uf.find(i);
            componentsMap.computeIfAbsent(root, k -> new ArrayList<>()).add(i);
        }

        // Convert map values to list of components
        return new ArrayList<>(componentsMap.values());
    }

    public static void main(String[] args) {
        // Example: 7 nodes (0 to 6) with edges
        int n = 7;
        int[][] edges = {
            {0, 1},
            {1, 2},
            {3, 4},
            {5, 6}
        };

        List<List<Integer>> components = getConnectedComponents(n, edges);

        System.out.println("Connected Components:");
        for (List<Integer> comp : components) {
            System.out.println(comp);
        }
    }
}
