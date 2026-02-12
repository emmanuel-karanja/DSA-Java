package BinaryTrees.LevelOrderTraversal;

import java.util.*;

/**
 * PROBLEM: Zigzag Level Order Traversal of a Binary Tree
 *
 * You are given the root of a binary tree (BST or not).
 * Return the zigzag level order traversal of its node values.
 *
 * Zigzag traversal means:
 * - Level 1: left to right
 * - Level 2: right to left
 * - Level 3: left to right
 * - ... and so on
 *
 * Each level’s values should be returned as a separate list.
 *
 * ------------------------------------------------------------
 * APPROACH (Breadth-First Search with Direction Control):
 *
 * 1. Use a queue to perform a standard level-order (BFS) traversal.
 * 2. Maintain a boolean flag `leftToRight` to indicate traversal direction.
 * 3. At the start of each level, the queue contains exactly the nodes
 *    of that level.
 * 4. For each node in the current level:
 *      - Remove it from the queue
 *      - Insert its value into a deque:
 *          • If leftToRight → addLast(value)
 *          • If rightToLeft → addFirst(value)
 *      - Enqueue its non-null children (left then right)
 * 5. After processing the level:
 *      - Convert the deque into a list and add it to the result
 *      - Flip the direction flag
 *
 * This approach avoids reversing lists and keeps the logic clean.
 *
 * ------------------------------------------------------------
 * TIME COMPLEXITY:
 * - O(n), where n is the number of nodes
 *
 * SPACE COMPLEXITY:
 * - O(n), due to the queue and result storage
 *
 * ------------------------------------------------------------
 */

public class ZigzagLevelOrderTraversal {

    // Definition for a binary tree node
    static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int val) {
            this.val = val;
        }
    }

    public static List<List<Integer>> zigzagLevelOrder(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();
        if (root == null) return result;

        Queue<TreeNode> queue = new ArrayDeque<>();
        queue.offer(root);

        boolean leftToRight = true;

        while (!queue.isEmpty()) {
            int levelSize = queue.size();
            Deque<Integer> levelValues = new ArrayDeque<>();

            // Process exactly one level
            for (int i = 0; i < levelSize; i++) {
                TreeNode node = queue.poll();

                if (leftToRight) {
                    levelValues.addLast(node.val);
                } else {
                    levelValues.addFirst(node.val);
                }

                if (node.left != null) queue.offer(node.left);
                if (node.right != null) queue.offer(node.right);
            }

            result.add(new ArrayList<>(levelValues));
            leftToRight =! leftToRight; // flip direction
        }

        return result;
    }

    // Driver code
    public static void main(String[] args) {
        /*
                 1
                / \
               2   3
              / \   \
             4   5   6

            Zigzag traversal:
            Level 1 -> [1]
            Level 2 -> [3, 2]
            Level 3 -> [4, 5, 6]
         */

        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);
        root.left.left = new TreeNode(4);
        root.left.right = new TreeNode(5);
        root.right.right = new TreeNode(6);

        System.out.println(zigzagLevelOrder(root));
    }
}
