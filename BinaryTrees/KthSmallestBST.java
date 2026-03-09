package BinaryTrees;
/**Find the Kth smallest node in BST.
 * 
 *  INTUTION
 * 
 * Inorder traversal gives you the array equivalent of a tree, and it's like walking an array from left to right.
 * So we find the Kth element.
 */

class TreeNode{
    int val; TreeNode left; TreeNode right;

    public TreeNode(int val){
        this.val=val; this.left=null; this.right=null;
    }
}
public class KthSmallestBST {
    int count = 0;
    int result = -1;

    public int kthSmallest(TreeNode root, int k) {
        inorder(root, k);
        return result;
    }

    private void inorder(TreeNode node, int k) {
        if (node == null) return;

        inorder(node.left, k);

        count++;
        if (count == k) {
            result = node.val;
            return;
        }

        inorder(node.right, k);
    }
}