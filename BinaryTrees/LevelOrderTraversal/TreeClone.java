package BinaryTrees.LevelOrderTraversal;

import java.util.ArrayDeque;
import java.util.Queue;

class TreeNode {
    public int value;
    public TreeNode left;
    public TreeNode right;

    public TreeNode(int val) {
        this.value = val;
        this.left = null;
        this.right = null;
    }
}

public class TreeClone {

    // =====================
    // 1️⃣ Recursive DFS clone
    // =====================
    public static TreeNode cloneTreeRecursive(TreeNode root) {
        if (root == null) return null;

        TreeNode newNode = new TreeNode(root.value);
        newNode.left = cloneTreeRecursive(root.left);
        newNode.right = cloneTreeRecursive(root.right);
        return newNode;
    }

    // =====================
    // 2️⃣ Iterative BFS clone
    // =====================
    public static TreeNode cloneTreeIterative(TreeNode root) {
        if (root == null) return null;

        // Map is optional for BFS because there are no cycles,
        // but using a queue to process nodes level by level
        TreeNode cloneRoot = new TreeNode(root.value);
        Queue<TreeNode[]> queue = new ArrayDeque<>(); // pair: original, clone
        queue.offer(new TreeNode[]{root, cloneRoot});

        while (!queue.isEmpty()) {
            TreeNode[] pair = queue.poll();
            TreeNode orig = pair[0];
            TreeNode clone = pair[1];

            if (orig.left != null) {
                clone.left = new TreeNode(orig.left.value);
                queue.offer(new TreeNode[]{orig.left, clone.left});
            }

            if (orig.right != null) {
                clone.right = new TreeNode(orig.right.value);
                queue.offer(new TreeNode[]{orig.right, clone.right});
            }
        }

        return cloneRoot;
    }

    // =====================
    // Test both
    // =====================
    public static void main(String[] args) {
        /*
                 1
               /   \
              2     3
             / \
            4   5
        */

        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);
        root.left.left = new TreeNode(4);
        root.left.right = new TreeNode(5);

        TreeNode cloneRec = cloneTreeRecursive(root);
        TreeNode cloneIter = cloneTreeIterative(root);

        System.out.println("Original root: " + root.value);
        System.out.println("Recursive clone root: " + cloneRec.value);
        System.out.println("Iterative clone root: " + cloneIter.value);

        System.out.println("Original left child: " + root.left.value);
        System.out.println("Recursive left child: " + cloneRec.left.value);
        System.out.println("Iterative left child: " + cloneIter.left.value);
    }
}
