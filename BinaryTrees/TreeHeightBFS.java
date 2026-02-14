package BinaryTrees;

/**
 * Problem Statement:
 * Compute the height of a binary tree in terms of edges using level-order traversal (BFS).
 *
 * Intuition:
 * - Height of a tree (edges) = number of edges on the longest path from root to any leaf.
 * - Level of a node = distance from the root (root is level 1 or 0 depending on convention).
 * - Number of levels along the longest path = number of nodes from root to leaf.
 * - Therefore, height (edges) = number of levels - 1.
 * 
 * Approach:
 * 1. Use BFS (level-order traversal) to traverse the tree level by level.
 * 2. Count the number of levels while traversing.
 * 3. Subtract 1 from the total levels to get the height in edges.
 * 
 * Notes:
 * - If the tree is empty, height = -1 when counting edges.
 * - BFS is convenient because it naturally visits nodes level by level.
 * - This approach works for any binary tree (not necessarily complete or balanced).
 */

import java.util.*;

class TreeNode {
    int val;
    TreeNode left, right;
    TreeNode(int val) { this.val = val; }
}

public class TreeHeightBFS {

    // Returns height of tree in edges
    public static int height(TreeNode root) {
        if (root == null) return -1; // height of empty tree

        Queue<TreeNode> q = new ArrayDeque<>();
        q.offer(root);
        int levels = 0;

        while (!q.isEmpty()) {
            int size = q.size();
            for (int i = 0; i < size; i++) {
                TreeNode node = q.poll();
                if (node.left != null) q.offer(node.left);
                if (node.right != null) q.offer(node.right);
            }
            levels++; // finished processing one level
        }

        return levels - 1; // convert levels to edges
    }

    public static void main(String[] args) {
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);
        root.left.left = new TreeNode(4);
        root.left.right = new TreeNode(5);

        System.out.println("Height of tree (edges) = " + height(root));
    }
}
