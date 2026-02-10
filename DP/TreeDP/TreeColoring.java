package DP.TreeDP;

/**
 * Tree Coloring DP (Counting Version)
 *
 * Problem:
 * You are given a tree with N nodes.
 * Each node must be colored using one of K colors.
 * Constraint: No two adjacent nodes may have the same color.
 *
 * Goal:
 * Count the total number of valid colorings of the tree.
 *
 * ----------------------------------------
 * STEP-BY-STEP DP BREAKDOWN (RUBRIC)
 * ----------------------------------------
 *
 * 1️⃣ Goal
 * Count the number of valid colorings of the entire tree.
 * This is a COUNT DP (not min/max).
 *
 * 2️⃣ State
 * dp[node][c] = number of valid colorings of the subtree rooted at `node`
 *               if `node` is colored with color `c`
 *
 * Why this state?
 * - The parent MUST know which color the child used.
 * - A binary (used / not used) state loses critical information.
 * - Color identity directly affects feasibility.
 *
 * 3️⃣ Choices / Decisions
 * For each node:
 *   - Choose one color `c` (0 .. K-1)
 * For each child:
 *   - The child may choose ANY color except `c`
 *
 * 4️⃣ Recurrence Relation
 * For a fixed node and color c:
 *
 * dp[node][c] =
 *     ∏ over all children child:
 *         ( sum over all colors cc != c of dp[child][cc] )
 *
 * Explanation:
 * - Fix node’s color
 * - For each child, count all valid colorings that do NOT use the same color
 * - Multiply because subtrees are independent
 *
 * 5️⃣ Base Cases
 * - Leaf node:
 *     dp[leaf][c] = 1 for all colors c
 *     (A single node colored with c is one valid coloring)
 *
 * Note:
 * - This is a BASE STATE, not just a base case
 * - Leaves must explicitly contribute to DP combinations
 *
 * 6️⃣ Evaluation Order Rule
 * - Post-order traversal (children first)
 * - A node’s dp depends only on its children’s dp
 *
 * 7️⃣ Solution
 * - Root has no parent, so it may use ANY color
 * - Answer = sum over all colors c of dp[root][c]
 *
 * ----------------------------------------
 * DP PRINCIPLES INVOLVED
 * ----------------------------------------
 * - Optimal Substructure: subtree colorings combine independently
 * - Overlapping Subproblems: subtree colorings reused via memoization
 * - State Completeness: dp[node][c] fully captures subtree boundary
 * - Counting DP Zero-State: leaf contributes 1 valid way
 *
 * Complexity:
 * - Time: O(N * K^2)
 *   (for each node, for each color, iterate over child colors)
 * - Space: O(N * K)
 *
 * ----------------------------------------
 * WHY MORE THAN 2 STATES ARE REQUIRED
 * ----------------------------------------
 * A binary state (colored / not colored) is insufficient because:
 * - Parent must know WHICH color was used, not just that a color was used
 * - Different colors impose different constraints on neighbors
 *
 * This is a canonical example where Tree DP strictly requires >2 states.
 */
class TreeColoring {

    static class Node {
        int id;
        java.util.List<Node> children = new java.util.ArrayList<>();

        Node(int id) {
            this.id = id;
        }
    }

    static int K; // number of colors
    static java.util.Map<Node, long[]> memo = new java.util.HashMap<>();

    public static long countColorings(Node root, int colors) {
        K = colors;
        long[] dpRoot = dfs(root, null);

        long result = 0;
        for (long ways : dpRoot) {
            result += ways;
        }
        return result;
    }

    // Returns dp[node][c]
    private static long[] dfs(Node node, Node parent) {
        if (memo.containsKey(node)) return memo.get(node);

        long[] dp = new long[K];

        // Initialize: assume node is colored with c
        for (int c = 0; c < K; c++) {
            dp[c] = 1;
        }

        // Combine children
        for (Node child : node.children) {
            if (child == parent) continue;

            long[] childDP = dfs(child, node);
            long[] next = new long[K];

            for (int c = 0; c < K; c++) {
                long sum = 0;
                for (int cc = 0; cc < K; cc++) {
                    if (cc != c) {
                        sum += childDP[cc];
                    }
                }
                next[c] = dp[c] * sum;
            }

            dp = next;
        }

        memo.put(node, dp);
        return dp;
    }

    // Example usage
    public static void main(String[] args) {
        Node root = new Node(1);
        Node a = new Node(2);
        Node b = new Node(3);
        Node c = new Node(4);

        root.children.add(a);
        root.children.add(b);
        a.children.add(root);
        b.children.add(root);

        a.children.add(c);
        c.children.add(a);

        int colors = 3;
        System.out.println("Number of valid colorings: " + countColorings(root, colors));
    }
}
