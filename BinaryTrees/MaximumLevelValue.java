import java.util.*;

/**
 * PROBLEM: Maximum Value at Any Level of a Binary Tree
 *
 * You are given the root of a binary tree (BST or not).
 * The task is to find the maximum node value that appears
 * in any single level of the tree.
 *
 * A "level" is defined as all nodes at the same depth:
 * - Level 1: root
 * - Level 2: children of the root
 * - Level 3: grandchildren, etc.
 *
 * GOAL:
 * Return the maximum node value across all levels.
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
 *      - Update `levelMax` using Math.max
 *      - Enqueue its non-null children
 * 5. After processing the level, compare `levelMax` with `globalMax`.
 * 6. Continue until the queue is empty.
 *
 * NOTE:
 * - The BST ordering property is NOT used.
 * - This works for any binary tree.
 *
 * ------------------------------------------------------------
 * TIME COMPLEXITY:
 * - O(n), where n is the number of nodes
 *
 * SPACE COMPLEXITY:
 * - O(w), where w is the maximum width of the tree
 *
 * ------------------------------------------------------------
 */

public class MaximumLevelValue {

    // Definition for a binary tree node
    static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int val) {
            this.val = val;
        }
    }

    public static int maxLevelValue(TreeNode root) {
        if (root == null) return Integer.MIN_VALUE;

        Queue<TreeNode> queue = new ArrayDeque<>();
        queue.offer(root);

        int globalMax = Integer.MIN_VALUE;

        while (!queue.isEmpty()) {
            int levelSize = queue.size();
            int levelMax = Integer.MIN_VALUE;

            // Process exactly one level
            for (int i = 0; i < levelSize; i++) {
                TreeNode node = queue.poll();
                levelMax = Math.max(levelMax, node.val);

                if (node.left != null) queue.offer(node.left);
                if (node.right != null) queue.offer(node.right);
            }

            globalMax = Math.max(globalMax, levelMax);
        }

        return globalMax;
    }

    // Driver code
    public static void main(String[] args) {
        /*
                 1
                / \
               7   0
              / \
             7  -8

            Level max values:
            Level 1 -> 1
            Level 2 -> 7
            Level 3 -> 7

            Global max = 7
         */

        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(7);
        root.right = new TreeNode(0);
        root.left.left = new TreeNode(7);
        root.left.right = new TreeNode(-8);

        System.out.println(maxLevelValue(root)); // Output: 7
    }
}
