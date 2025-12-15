package BinaryTrees;
/**Problem

Given a binary tree, determine if it is a valid Binary Search Tree (BST).
A BST satisfies:

1. The left subtree of a node contains only nodes with values less than the node’s value.
2. The right subtree of a node contains only nodes with values greater than the node’s value.
3. Both left and right subtrees must also be BSTs.
4. No duplicate values are allowed (typically, strictly < and > are used). */
class TreeNode{
    public TreeNode left;
    public TreeNode right;
    public int value;

    public TreeNode(int value){
        this.left=null;
        this.right=null;
        this.value=value;
    }
}
public class ValidateBST {
  public static boolean isValidBST(TreeNode root) {
      //start off with null values for max and min they are for convenience dealing with
      //left and right subtree. You use Integer the class so that we can have 'null' since any
      //ordinal is potentially a value in the tree.
      return validate(root, null, null);
  }

  private static boolean validate(TreeNode node, Integer min, Integer max) {
      if (node == null) return true;

      if ((min != null && node.value <= min) || (max != null && node.value >= max)) {
          return false;
      }

      // For left subtree,the max is the node.value and for right subtree, the min is the current value
      return validate(node.left, min, node.value) &&
            validate(node.right, node.value, max);
  }
}
