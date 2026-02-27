package Graph;

import java.util.Arrays;
/***The Problem: You are driving a truck from city A to city B. Each road has a weight limit.
 *  You want to find a path such that the minimum weight limit on the path is maximized 
 * (so you can carry the heaviest load possible).
The Insight: This is essentially finding the Maximum Spanning Tree. In any MST (or MaxST),
 the unique path between two nodes in the tree is the path that optimizes the "bottleneck" edge. */
public class MSTKruskalHighwayWeight {
    public int maxWeightPath(int n, int[][] edges, int start, int end) {
    // 1. Sort edges descending by weight limit
        Arrays.sort(edges, (a, b) -> b[2] - a[2]);

        UnionFind uf = new UnionFind(n);

        for (int[] edge : edges) {
            int u = edge[0];
            int v = edge[1];
            int weight = edge[2];

            uf.union(u, v);

            // 2. The moment start and end are connected, we found the path
            if (uf.find(start) == uf.find(end)) {
                return weight;
            }
        }

        return 0; // Or -1 if no path exists
    }
}
