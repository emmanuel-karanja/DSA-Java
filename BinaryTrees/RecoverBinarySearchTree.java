package BinaryTrees;

/**
 * ============================================================
 * PROBLEM: Recover Binary Search Tree
 * ============================================================
 *
 * Two nodes of a Binary Search Tree (BST) are swapped by mistake.
 * Recover the tree without changing its structure.
 *
 *  INTUITION
 * 
 * Think of the tree as an inorder sorted array.
 * 
 * Inorder traversal of BST must be sorted.
   Two swapped nodes create inversions.
   Track first node where prev > curr and last curr where violation occurs.
   Swap them.

   Two swapped nods cause two inversions i.e. where prev > current 

   Think of first as the marker for the first violation and second as the marker for the second violation.
   Find these two and then swap them. 

   Inorder traversal is basically walking the tree as if it was an array. (left-->root-->right)

 * TIME COMPLEXITY
 *      O(n)
 *
 * SPACE COMPLEXITY
 *      O(h) recursion stack
 *
 * (Optimal Morris traversal version can achieve O(1) space)
 * ============================================================
 */

public class RecoverBinarySearchTree {

    static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int v) {
            val = v;
        }
    }

    TreeNode first = null;   //first violation 
    TreeNode second = null;   // second violation why first and second? Because it means two nodes have been swapped.
    TreeNode prev = null;

    public void recoverTree(TreeNode root) {
        inorder(root);

        // Swap
        int temp = first.val;
        first.val = second.val;
        second.val = temp;
    }

    private void inorder(TreeNode node) {
        if (node == null) return;

        inorder(node.left);

        if (prev != null && prev.val > node.val) {

            if (first == null) {
                first = prev;
            }

            second = node;
        }

        prev = node;

        inorder(node.right);
    }

    // Utility: Inorder print
    static void printInorder(TreeNode root) {
        if (root == null) return;

        printInorder(root.left);
        System.out.print(root.val + " ");
        printInorder(root.right);
    }

    // Driver
    public static void main(String[] args) {

        /**
         * Construct swapped BST
         *
         *         3
         *        / \
         *       1   4
         *          /
         *         2
         *
         * 2 and 3 are swapped
         */

        TreeNode root = new TreeNode(3);
        root.left = new TreeNode(1);
        root.right = new TreeNode(4);
        root.right.left = new TreeNode(2);

        System.out.println("Before Recovery (Inorder):");
        printInorder(root);

        RecoverBinarySearchTree solver = new RecoverBinarySearchTree();
        solver.recoverTree(root);

        System.out.println("\nAfter Recovery (Inorder):");
        printInorder(root);
    }
}