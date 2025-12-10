package BinaryTrees;
//**Invert a binary tree.
// INTUTION
//  dfs and invert left and right
//  */

import java.util.LinkedList;
import java.util.Queue;

class TreeNode{
    public TreeNode left;
    public TreeNode right;
    public int value;

    public TreeNode(TreeNode left,TreeNode right, int value){
        this.left=left;
        this.right=right;
        this.value=value;
    }
}
public class InvertBinaryTree {

  public static TreeNode invertTree(TreeNode root){
     if(root==null) return null;

     TreeNode invertedLeft=invertTree(root.right);
     TreeNode invertedRight=invertTree(root.left);

     root.left=invertedLeft;
     root.right=invertedRight;

     return root;
  }

  // Helper to print tree in level order
    public static void printLevelOrder(TreeNode root) {
        if (root == null) return;

        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);

        while (!queue.isEmpty()) {
            TreeNode node = queue.poll();
            System.out.print(node.value + " ");

            if (node.left != null) queue.add(node.left);
            if (node.right != null) queue.add(node.right);
        }
        System.out.println();
    }

    public static void main(String[] args) {
        /*
                  1
                 / \
                2   3
               / \
              4   5
        */

        TreeNode root = new TreeNode(
                new TreeNode(new TreeNode(null, null, 4),
                             new TreeNode(null, null, 5),
                             2),
                new TreeNode(null, null, 3),
                1
        );

        System.out.println("Original tree (level-order):");
        printLevelOrder(root);

        TreeNode inverted = invertTree(root);

        System.out.println("Inverted tree (level-order):");
        printLevelOrder(inverted);
    }
}
