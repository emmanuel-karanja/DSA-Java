package DP;

/**
 * DP BREAKDOWN: Tree DP (House Robber III)
 *
 * 1. GOAL:
 *    Maximize the sum of node values in a binary tree, where you cannot rob two directly-linked nodes.
 *
 * 2. STATE:
 *    int[] dp where:
 *      - dp[0] = Maximum sum excluding the current node.
 *      - dp[1] = Maximum sum including the current node.
 *
 * 3. CHOICES / TRANSITIONS:
 *    - Rob current node: node.val + leftChild[0] + rightChild[0]
 *    - Skip current node: max(leftChild) + max(rightChild)
 *
 * 4. EVALUATION ORDER:
 *    Post-order traversal (bottom-up), because each node's decision depends on its children.
 *
 * 5. TIME & SPACE COMPLEXITY:
 *    - Transition cost: O(1) per node.
 *    - Total Time: O(N), where N is the number of nodes.
 *    - Space: O(H) for recursion stack, H = height of tree.
 */
public class HouseRobberIII {

    public int rob(TreeNode root) {
        int[] result = dfs(root);
        return Math.max(result[0], result[1]);
    }

    private int[] dfs(TreeNode node) {
        if (node == null) return new int[]{0, 0};

        int[] left = dfs(node.left);
        int[] right = dfs(node.right);

        int skipNode = Math.max(left[0], left[1]) + Math.max(right[0], right[1]);
        int robNode = node.val + left[0] + right[0];

        return new int[]{skipNode, robNode};
    }

    // ------------------------------
    // Driver / Test code
    // ------------------------------
    public static void main(String[] args) {
        /*
         Example tree:
                 3
                / \
               2   3
                \    \
                 3    1
         */

        TreeNode root = new TreeNode(3);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);
        root.left.right = new TreeNode(3);
        root.right.right = new TreeNode(1);

        HouseRobberIII solver = new HouseRobberIII();
        int maxSum = solver.rob(root);
        System.out.println("Maximum sum that can be robbed: " + maxSum);
        // Expected output: 7
        // Explanation: Rob nodes 3 + 3 + 1 = 7 (cannot rob directly connected nodes)
    }
}

// Simple TreeNode class
class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;

    TreeNode(int val) { this.val = val; }
}
