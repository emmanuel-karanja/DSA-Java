package DP.TreeDP;

/**
 * Problem: Maximum Sum of Non-Adjacent Nodes in a Tree
 *
 * Explanation:
 * This is a classic Tree DP problem where we decide for each node whether to "pick" it or "skip" it.
 * - dp[node][0] = max sum of subtree rooted at node if we do NOT pick this node
 * - dp[node][1] = max sum of subtree rooted at node if we DO pick this node
 *
 * Recurrence:
 * - If we pick the node, we cannot pick its children: pick = node.val + sum(childDP[0] for all children)
 * - If we do not pick the node, children can be picked or skipped: notPick = sum(max(childDP[0], childDP[1]) for all children)
 *
 * Base case: Leaf nodes return [0, node.val]
 * Time Complexity: O(N), N = number of nodes
 * Space Complexity: O(H) due to recursion stack, H = height of tree
 * 
 * LOGIC:
 * 
 * At  each node, we must only compute 2 values. notPick and pick values. Only those two.
 * And the question is: 
 * 
 *  1. How do we calculate notPick?  set to 0 at first and don't use the current node value
 *  2. How do we calculate the pick? use the current node value
 * 
 * Also ensure the state of the children is computd before the evaluating the current node.
 * 
 * 
 */

import java.util.*;

class TreeNode {
    int val;
    List<TreeNode> children;

    TreeNode(int val) {
        this.val = val;
        this.children = new ArrayList<>();
    }
}

public class MaxSumNonAdjacentNodes {

    // DFS returns int[2]: [0] -> max sum if node not picked, [1] -> max sum if node picked
    private static int[] dfs(TreeNode node) {
        // take note of the base case
        if (node == null) return new int[]{0, 0};

        int pick = node.val;   // If we pick this node
        int notPick = 0;       // If we do not pick this node

        for (TreeNode child : node.children) {
            // post-order
            int[] childDP = dfs(child);

            pick += childDP[0]; // can't pick children if current is picked
            notPick += Math.max(childDP[0], childDP[1]); // can pick or skip children
        }

        return new int[]{notPick, pick};
    }

    public static int maxSum(TreeNode root) {
        int[] dp = dfs(root);
        return Math.max(dp[0], dp[1]);
    }

    public static void main(String[] args) {
        // Example tree:
        //        10
        //       /  \
        //      1    2
        //     / \
        //    3   4
        TreeNode root = new TreeNode(10);
        TreeNode node1 = new TreeNode(1);
        TreeNode node2 = new TreeNode(2);
        TreeNode node3 = new TreeNode(3);
        TreeNode node4 = new TreeNode(4);

        root.children.add(node1);
        root.children.add(node2);
        node1.children.add(node3);
        node1.children.add(node4);

        System.out.println("Max sum of non-adjacent nodes: " + maxSum(root)); // Output: 17
    }
}
