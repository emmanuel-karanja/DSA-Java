/**
 * PROBLEM STATEMENT (House Robber III):
 *
 * You are given a binary tree where each node represents a house containing some amount of money.
 * If you rob a house, you cannot rob its direct children (adjacent houses cannot both be robbed).
 *
 * Determine the maximum amount of money you can rob without violating this rule.
 *
 *
 * TREE DP EXPLANATION (INTERVIEW-LEVEL):
 *
 * This is a classic Tree Dynamic Programming problem.
 *
 * Key idea:
 * - Once a tree is rooted, DP dependencies become one-directional (children → parent).
 * - Each node represents an independent subproblem.
 * - There are no cyclic or sibling dependencies, so the dependency graph is a DAG.
 *
 * DP State:
 * For each node, compute TWO values:
 *
 *   dp[0] → maximum money if we DO NOT rob this node
 *   dp[1] → maximum money if we DO rob this node
 *
 * Why two states?
 * - If we rob the current node, we cannot rob its children.
 * - If we do not rob the current node, children may be robbed or not.
 *
 * Recurrence:
 * Let left and right be the DP results from the children.
 *
 *   rob    = node.val + left[0] + right[0]
 *   notRob = max(left[0], left[1]) + max(right[0], right[1])
 *
 * Traversal:
 * - Post-order DFS ensures children are processed before parents.
 *
 * Complexity:
 * - Time: O(n), where n is the number of nodes
 * - Space: O(h), where h is the height of the tree (recursion stack)
 *
 * Why this is Tree DP:
 * - The tree structure defines the recurrence
 * - Dependencies flow strictly in one direction (children → parent)
 * - Each subtree is solved exactly once
 */
class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;

    TreeNode(int val) {
        this.val = val;
    }
}

class HouseRobberTreeIII {

    public static int rob(TreeNode root) {
        int[] res = dfs(root);
        return Math.max(res[0], res[1]);
    }

    // Returns:
    // [0] = max money if this node is NOT robbed
    // [1] = max money if this node IS robbed
    private static int[] dfs(TreeNode node) {
        if (node == null) {
            return new int[]{0, 0};
        }

        int[] left = dfs(node.left);
        int[] right = dfs(node.right);

        int rob = node.val + left[0] + right[0];
        int notRob = Math.max(left[0], left[1])
                   + Math.max(right[0], right[1]);

        return new int[]{notRob, rob};
    }

    public static void main(String[] args) {

        // Build the tree
        TreeNode root = new TreeNode(3);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);
        root.left.right = new TreeNode(3);
        root.right.right = new TreeNode(1);

        // Run solution
        int result = rob(root);

        // Print result
        System.out.println("Maximum money that can be robbed: " + result);
    }
}

/**
 * Definition for a binary tree node.
 */

/**
 * DRIVER CODE
 *
 * Builds a sample tree and runs the solution.
 *
 * Example tree:
 *         3
 *        / \
 *       2   3
 *        \   \
 *         3   1
 *
 * Expected output: 7
 * (Rob nodes with values 3 + 3 + 1)
 */

