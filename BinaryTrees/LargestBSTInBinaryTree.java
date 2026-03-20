package BinaryTrees;

/**
 * ============================================================
 * PROBLEM: Largest BST in a Binary Tree
 * ============================================================
 *
 * Given a binary tree, find the size of the largest subtree
 * that is also a Binary Search Tree (BST).
 *
 * BST Property:
 *      left subtree values < node value < right subtree values
 *    
 * INTUTION:
 *  Do a post-order traversal and check the binary tree property
 *    Binary Tree Property:
 * 
 *  1. That the left and right children are binary trees
 *  2. leftmax<node.val<right.min
 *  
 * Track the max size encountred so far.
 *  siz=left.size+right.size+1 (size of the left and right child plus 1 for the current node)
 *
 * Example:
 *
 *          10
 *         /  \
 *        5    15
 *       / \     \
 *      1   8     7
 *
 * Largest BST subtree:
 *
 *        5
 *       / \
 *      1   8
 *
 * Size = 3
 *
 * ------------------------------------------------------------
 * APPROACH
 * ------------------------------------------------------------
 *
 * We solve this using bottom-up traversal (postorder).
 *
 * For every node we compute:
 *
 *      1. isBST
 *      2. size
 *      3. min value in subtree
 *      4. max value in subtree
 *
 * A node forms a BST if:
 *
 *      left.isBST
 *      right.isBST
 *      left.max < node.val < right.min
 *
 * If true:
 *
 *      size = left.size + right.size + 1
 *
 * Otherwise:
 *
 *      subtree is not BST
 *
 * Track the maximum size encountered.
 *
 * ------------------------------------------------------------
 * TIME COMPLEXITY
 *      O(n)
 *
 * SPACE COMPLEXITY
 *      O(h) recursion stack
 * ============================================================
 * 
 * Note: In any situation where you need to evaluate the children first before the node, 
 * you will be looking at post-order traversal. e.g. verifying the children first.
 */

public class LargestBSTInBinaryTree {

    static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int v) {
            val = v;
        }
    }

    static class NodeInfo {
        boolean isBST;
        int size;
        int min;
        int max;

        NodeInfo(boolean isBST, int size, int min, int max) {
            this.isBST = isBST;
            this.size = size;
            this.min = min;
            this.max = max;
        }
    }

    int maxBSTSize = 0;

    public int largestBST(TreeNode root) {
        dfsHelper(root);
        return maxBSTSize;
    }

    private NodeInfo dfsHelper(TreeNode node) {

        if (node == null) {
            // A null node is a BST.
            return new NodeInfo(true, 0, Integer.MAX_VALUE, Integer.MIN_VALUE);
        }

        NodeInfo left = dfsHelper(node.left);
        NodeInfo right = dfsHelper(node.right);

        // Binary tree property.
        if (left.isBST && right.isBST && node.val > left.max && node.val < right.min) {

            int size = left.size + right.size + 1;

            maxBSTSize = Math.max(maxBSTSize, size);

            int min = Math.min(node.val, left.min);
            int max = Math.max(node.val, right.max);

            return new NodeInfo(true, size, min, max);
        }
       // else notice that we return false,0,0,0
        return new NodeInfo(false, 0, 0, 0);
    }

    // Utility inorder print
    static void inorder(TreeNode root) {
        if (root == null) return;

        inorder(root.left);
        System.out.print(root.val + " ");
        inorder(root.right);
    }

    // Driver
    public static void main(String[] args) {

        /**
         * Construct tree
         *
         *          10
         *         /  \
         *        5    15
         *       / \     \
         *      1   8     7
         */

        TreeNode root = new TreeNode(10);
        root.left = new TreeNode(5);
        root.right = new TreeNode(15);

        root.left.left = new TreeNode(1);
        root.left.right = new TreeNode(8);

        root.right.right = new TreeNode(7);

        LargestBSTInBinaryTree solver = new LargestBSTInBinaryTree();

        int result = solver.largestBST(root);

        System.out.println("Largest BST size: " + result);
    }
}