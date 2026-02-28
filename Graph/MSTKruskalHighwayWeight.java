package Graph;

import java.util.Arrays;
/***
 * Problem Statement:
 * 
 * You are given a graph of n cities (nodes) connected by roads (edges). Each road has a weight limit,
 * representing the maximum load a truck can carry on that road. You are asked to find a path from 
 * city 'start' to city 'end' such that the **minimum weight limit along the path is maximized**. 
 * In other words, you want the path that allows the truck to carry the heaviest load possible.
 * 
 * Example:
 * n = 4
 * edges = {{0,1,10}, {1,2,5}, {0,2,7}, {2,3,8}}
 * start = 0, end = 3
 * Maximum bottleneck path: 0 -> 2 -> 3, minimum edge = 7
 * Answer = 7
 * 
 * Reasoning / Approach:
 * 
 * 1. **Maximum Spanning Tree Insight**:
 *    - In a Maximum Spanning Tree (MaxST), the unique path between any two nodes is the path that maximizes
 *      the minimum edge weight (the "bottleneck").
 *    - Therefore, if we process edges in descending order and greedily merge connected components,
 *      the first moment 'start' and 'end' are connected gives us the optimal path.
 *
 * 2. **Greedy Edge Merging**:
 *    - Sort all edges in **descending order** by weight.
 *    - Initialize Union-Find to track connected components of nodes.
 *    - Iterate over edges:
 *        a. Merge the two nodes of the current edge in Union-Find (uf.union(u, v)).
 *        b. Check if 'start' and 'end' are now in the same component (uf.find(start) == uf.find(end)).
 *           - If yes, the current edge's weight is the **maximum possible minimum weight** along any path.
 *           - Return that weight immediately.
 *    - This works because edges are considered **heaviest first**, ensuring the first connection
 *      uses the largest possible edges and maximizes the bottleneck.
 *
 * 3. **Time Complexity**:
 *    - Sorting edges: O(E log E)
 *    - Union-Find operations: ~O(E α(n)) (α(n) ≈ 1)
 *    - Total: O(E log E)
 *
 * 4. **Space Complexity**:
 *    - Union-Find arrays: O(n)
 *    - Edge array: O(E)
 *
 * Key Idea:
 * - We do **not** need to construct the full MaxST.
 * - The first time 'start' and 'end' are connected while adding edges greedily from largest to smallest
 *   automatically gives the **maximum bottleneck path**.
 */
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
