package DP.GraphDP;

import java.util.*;
/**
 * Problem: Longest Path in a Directed Acyclic Graph (DAG)
 *
 * Explanation:
 * This is a classic Graph DP problem where we compute the longest path starting from each node.
 * 
 * DP State:
 * - dp[u] = length of the longest path starting from node u
 *
 * Transition:
 * - For each neighbor v of u: dp[u] = max(dp[u], 1 + dp[v])
 * - "1" accounts for the current node in the path
 *
 * Approach:
 * 1. Use DFS to explore all paths starting from u.
 * 2. Use memoization to store dp[u] once computed, preventing recomputation.
 * 3. Optionally, you can use topological sorting to compute dp[u] iteratively.
 *
 * Base Case:
 * - If a node has no outgoing edges, dp[u] = 1
 *
 * Time Complexity: O(V + E), V = number of nodes, E = number of edges
 * Space Complexity: O(V) for memoization + recursion stack
 *
 * Insight:
 * - This is a generalization of Tree DP: in Tree DP, each node depends on its children.
 * - In Graph DP on a DAG, each node depends on its outgoing neighbors.
 * - Array DP (House Robber) and Tree DP are special cases of Graph DP on simpler structures.
 */

public class LongestPathInDAGDP {

    static class Graph {
        int n;
        List<Integer>[] adj;

        Graph(int n) {
            this.n = n;
            adj = new List[n];
            for (int i = 0; i < n; i++) adj[i] = new ArrayList<>();
        }

        void addEdge(int u, int v) {
            adj[u].add(v); // directed edge u -> v
        }
    }

    static int[] dp;

    static int dfs(Graph g, int u) {
        if (dp[u] != -1) return dp[u]; // memoization
        int maxLength = 1; // path includes u
        
        for (int v : g.adj[u]) {
            maxLength = Math.max(maxLength, 1 + dfs(g, v));
        }
        return dp[u] = maxLength;
    }

    public static void main(String[] args) {
        int n = 5;
        Graph g = new Graph(n);
        g.addEdge(0, 1);
        g.addEdge(0, 2);
        g.addEdge(1, 3);
        g.addEdge(2, 3);
        g.addEdge(3, 4);

        dp = new int[n];
        Arrays.fill(dp, -1);

        for (int i = 0; i < n; i++) {
            System.out.println("Longest path from node " + i + ": " + dfs(g, i));
        }
    }
}
