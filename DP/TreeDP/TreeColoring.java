package TreeDP;

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
 *  * ================================================================
 * INTUITIVE EXPLANATION (MENTAL MODEL)
 * ================================================================
 *
 * This Tree DP works exactly like a PROBABILITY TREE,
 * except we count the number of valid outcomes instead of probabilities.
 *
 * Core idea:
 * ----------
 * Ask this question at every node:
 *
 *   “If THIS node is colored with color c,
 *    how many valid colorings exist in its entire subtree?”
 *
 * That number is dp[node][c].
 *
 * ------------------------------------------------
 * SUM vs MULTIPLY (the most important intuition)
 * ------------------------------------------------
 *
 * - SUM  → different choices (OR)
 * - MULTIPLY → independent events (AND)
 *
 * This is the same rule you used in probability:
 *
 *   P(Head AND Six) = (1/2) × (1/6)
 *
 * Here, instead of probabilities, we count valid colorings.
 *
 * ------------------------------------------------
 * How a node computes its dp
 * ------------------------------------------------
 *
 * 1) Fix the color of the current node to c.
 *
 * 2) For EACH child:
 *    - The child may use ANY color except c.
 *    - So the number of valid ways for that child is:
 *
 *        sum(dp[child][cc]) for all cc != c
 *
 *    This is a SUM because the child has multiple allowed choices.
 *
 * 3) All children are independent once the parent’s color is fixed,
 *    so we MULTIPLY the results from each child.
 *
 * Result:
 *
 *   dp[node][c] =
 *     ∏ over all children:
 *        ( Σ dp[child][cc] where cc ≠ c )
 *
 * ------------------------------------------------
 * Why leaves start with dp = 1
 * ------------------------------------------------
 *
 * A leaf node has no children.
 * If it is colored with a fixed color c, that represents
 * exactly ONE complete valid coloring.
 *
 * This is like reaching a leaf in a probability tree:
 * it counts as one finished outcome.
 *
 * Therefore:
 *
 *   dp[leaf][c] = 1
 *
 * ------------------------------------------------
 * One-sentence summary
 * ------------------------------------------------
 *
 * Fix my color → ask each child how many ways they can adapt →
 * multiply the results → pass the count upward.
 *
 * ----------------------------------------
 * STEP-BY-STEP DP BREAKDOWN (RUBRIC)
 * ----------------------------------------
 *
 * 1️ Goal
 * Count the number of valid colorings of the entire tree.
 * This is a COUNT DP (not min/max).
 *
 * 2️ State
 * dp[node][c] = number of valid colorings of the subtree rooted at `node`
 *               if `node` is colored with color `c`
 *
 * Why this state?
 * - The parent MUST know which color the child used.
 * - A binary (used / not used) state loses critical information.
 * - Color identity directly affects feasibility.
 *
 * 3️ Choices / Decisions
 * For each node:
 *   - Choose one color `c` (0 .. K-1)
 * For each child:
 *   - The child may choose ANY color except `c`  i.e. cc !=c
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

        // Initialize: assume node is colored with c i.e.assume leaf case
        for (int c = 0; c < K; c++) {
            dp[c] = 1;   // Multiplicative identity
        }

        // Combine children
        for (Node child : node.children) {
            // I had not noticed this but
            if (child == parent) continue;

            long[] childDP = dfs(child, node);
            long[] next = new long[K];  // current node state

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
