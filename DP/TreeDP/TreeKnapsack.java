package DP.TreeDP;

/**
 * Tree Knapsack Problem Explanation
 *
 * This is a classic example of Tree DP with a knapsack constraint. Each node in the tree has:
 *   - a value (profit) `val`
 *   - a weight `weight`
 *
 * Goal:
 *   Maximize the total value of nodes selected such that the sum of weights ≤ W.
 *   This is similar to 0/1 Knapsack but applied to a tree.
 *
 * Step-by-step DP breakdown (following the DP rubric):
 *
 * 1️⃣ Goal
 *   Find the maximum total value achievable in the tree for a given knapsack capacity W.
 *
 * 2️⃣ State
 *   dp[node][w] = maximum value achievable in the subtree rooted at 'node'
 *                 using total weight ≤ w
 *   - 'node' identifies the subtree
 *   - 'w' is the remaining capacity
 *
 * 3️⃣ Choices / Decisions
 *   For each node:
 *     - Either include the current node in the knapsack (if weight ≤ remaining)
 *     - Or skip the current node
 *   Then combine with children’s DP results.
 *
 * 4️⃣ Recurrence Relation
 *   dp[node][w] = max(
 *       sum of children's dp if we skip node,
 *       node.val + sum of children's dp with remaining weight (w - node.weight)
 *   )
 *
 * 5️⃣ Base Cases
 *   - If node == null → return 0 (base case for recursion)
 *   - If remaining weight w == 0 → cannot take anything (base state in DP table)
 *
 * 6️⃣ Evaluation Order Rule
 *   - Recursively compute children first (post-order traversal)
 *   - This ensures we always have children's DP ready before combining at the current node
 *
 * 7️⃣ Solution
 *   - After filling dp[node][w] for the root node, dp[root][W] gives the global optimal
 *
 * Notes on DP principles:
 * - Optimal Substructure: max value in a subtree depends on max values of its children
 * - Overlapping Subproblems: same subtree + remaining weight pair may be encountered multiple times
 * - Base State: explicitly handled in DP array for w=0
 * - Base Case: recursion stops at null nodes
 *
 * Complexity:
 *   O(N * W) where N = number of nodes, W = knapsack capacity
 */
class TreeKnapsack {

    static class Node {
        int val;
        int weight;
        Node left, right;

        Node(int val, int weight) {
            this.val = val;
            this.weight = weight;
        }
    }

    static int W; // total knapsack capacity

    // Memoization table: Map each node to dp array over capacities
    static java.util.Map<Node, int[]> memo = new java.util.HashMap<>();

    public static int treeKnapsack(Node root, int capacity) {
        W = capacity;
        int[] dp = dfs(root);
        return dp[W];
    }

    // dfs returns dp array for subtree rooted at 'node'
    // dp[w] = max value achievable for weight w
    private static int[] dfs(Node node) {
        if (node == null) {
            int[] base = new int[W + 1]; // base state: 0 value for all capacities
            return base;
        }

        if (memo.containsKey(node)) return memo.get(node);

        int[] leftDP = dfs(node.left);
        int[] rightDP = dfs(node.right);

        int[] dp = new int[W + 1];

        // Iterate over all capacities
        for (int w = 0; w <= W; w++) {
            // Option 1: skip current node
            dp[w] = leftDP[w] + rightDP[w];

            // Option 2: take current node if weight fits
            if (w >= node.weight) {
                dp[w] = Math.max(dp[w],
                        node.val + leftDP[w - node.weight] + rightDP[w - node.weight]);
            }
        }

        memo.put(node, dp);
        return dp;
    }

    // Example usage
    public static void main(String[] args) {
        Node root = new Node(10, 5);
        root.left = new Node(5, 3);
        root.right = new Node(15, 8);
        root.left.left = new Node(7, 2);

        int maxCapacity = 10;
        System.out.println("Max value: " + treeKnapsack(root, maxCapacity));
    }
}
