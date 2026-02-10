package BinaryTrees;

import java.util.*;

/**
 * PROBLEM: Maximum Level Sum of a Binary Tree
 *
 * You are given the root of a binary tree (it may or may not be a BST).
 * The task is to compute the maximum sum of values among all levels of the tree.
 *
 * A "level" is defined as all nodes at the same depth:
 * - Level 1 contains the root
 * - Level 2 contains the root's children
 * - Level 3 contains their children, and so on
 *
 * GOAL:
 * Return the maximum sum of node values across all levels.
 *
 * ------------------------------------------------------------
 * APPROACH (Breadth-First Search / Level-Order Traversal):
 *
 * 1. Use a queue to traverse the tree level by level.
 * 2. At the start of each level, the queue contains exactly
 *    the nodes belonging to that level.
 * 3. Capture the size of the queue as `levelSize`.
 * 4. Iterate `levelSize` times:
 *      - Dequeue a node
 *      - Add its value to `levelSum`
 *      - Enqueue its non-null children
 * 5. After processing the level, compare `levelSum` with `maxSum`.
 * 6. Continue until the queue is empty.
 *
 * NOTE:
 * - The BST property is NOT used.
 * - This solution works for any binary tree.
 *
 * ------------------------------------------------------------
 * TIME COMPLEXITY:
 * - O(n), where n is the number of nodes (each node is visited once)
 *
 * SPACE COMPLEXITY:
 * - O(w), where w is the maximum width of the tree
 *
 * ------------------------------------------------------------
 */

public class MaximumLevelSum {

    // Definition for a binary tree node
    static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int val) {
            this.val = val;
        }
    }

    public static int maxLevelSum(TreeNode root) {
        if (root == null) return 0;

        Queue<TreeNode> queue = new ArrayDeque<>();
        queue.offer(root);

        int maxSum = Integer.MIN_VALUE;

        while (!queue.isEmpty()) {
            int levelSize = queue.size();
            int levelSum = 0;

            // Process exactly one level
            for (int i = 0; i < levelSize; i++) {
                TreeNode node = queue.poll();
                levelSum += node.val;

                if (node.left != null) queue.offer(node.left);
                if (node.right != null) queue.offer(node.right);
            }

            maxSum = Math.max(maxSum, levelSum);
        }

        return maxSum;
    }

    // Driver code for testing
    public static void main(String[] args) {
        /*
                 1
                / \
               7   0
              / \
             7  -8

            Level sums:
            Level 1 -> 1
            Level 2 -> 7 + 0 = 7
            Level 3 -> 7 + (-8) = -1

            Max level sum = 7
         */

        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(7);
        root.right = new TreeNode(0);
        root.left.left = new TreeNode(7);
        root.left.right = new TreeNode(-8);

        System.out.println(maxLevelSum(root)); // Output: 7
    }
}
