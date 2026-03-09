package BinaryTrees;

/**
 * PROBLEM:
 * Flatten a Binary Tree to a Linked List.
 *
 * Given the root of a binary tree, flatten the tree into a linked list in-place.
 *
 * The linked list should follow the same order as a preorder traversal:
 *
 *      root -> left -> right
 *
 * After flattening:
 * 1. Each node's right pointer should point to the next node in preorder.
 * 2. Each node's left pointer should be null.
 *
 * Example:
 *
 * Input Tree:
 *          1
 *        /   \
 *       2     5
 *      / \     \
 *     3   4     6
 *
 * Preorder Traversal:
 *      1 2 3 4 5 6
 *
 * Output (Linked List using right pointers):
 *
 *      1
 *       \
 *        2
 *         \
 *          3
 *           \
 *            4
 *             \
 *              5
 *               \
 *                6
 *
 *
 * INTUITION:
 *
 * The key observation is that preorder traversal gives us the exact order
 * required for the linked list.
 *
 * For each node:
 *
 * 1. Recursively flatten the left subtree.
 * 2. Recursively flatten the right subtree.
 * 3. Attach the flattened left subtree to the right of the current node.
 * 4. Move the original right subtree to the end of this newly attached chain.
 *
 * Steps:
 *
 * current
 *    / \
 *   L   R
 *
 * becomes
 *
 * current
 *    \
 *     L -> ... -> last -> R
 *
 * Complexity:
 * Time  : O(n)   (each node visited once)
 * Space : O(h)   (recursion stack, h = tree height)
 */

class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;

    TreeNode(int val) {
        this.val = val;
    }
}

public class BSTToLinkedList {

    public static void flatten(TreeNode root) {
        if (root == null) return;

        flatten(root.left);
        flatten(root.right);

        TreeNode leftSubtree = root.left;
        TreeNode rightSubtree = root.right;

        root.left = null;
        root.right = leftSubtree;

        TreeNode current = root;

        // Walk the entire right side of it until the end and then attach the rightSubtre there.
        while (current.right != null) {
            current = current.right;
        }

        current.right = rightSubtree;
    }

    public static void printLinkedList(TreeNode root) {
        TreeNode curr = root;

        while (curr != null) {
            System.out.print(curr.val + " -> ");
            curr = curr.right;
        }

        System.out.println("null");
    }

    public static void main(String[] args) {

        /**
         * Construct the tree
         *
         *        1
         *      /   \
         *     2     5
         *    / \     \
         *   3   4     6
         */

        TreeNode root = new TreeNode(1);

        root.left = new TreeNode(2);
        root.right = new TreeNode(5);

        root.left.left = new TreeNode(3);
        root.left.right = new TreeNode(4);

        root.right.right = new TreeNode(6);

        flatten(root);

        System.out.println("Flattened Linked List:");
        printLinkedList(root);
    }
}