package BinaryTrees.PathSum;
/**
 * Given a binary tree where each node contains an integer value (which can be negative), 
 * find the maximum sum of values along any path from the root to a leaf (or from any node downward along
 *  parent→child edges, without branching). The path cannot fork — you can only move from a node to one of its
 *  children at each step.

Return the largest sum among all possible downward paths.

INTUITION:

DFS
 */
class TreeNode{
    public TreeNode left;
    public TreeNode right;
    public int value;
    public TreeNode(int value){
        this.value=value;
    }
}

public class PathSum {
    private static int dfsPure(TreeNode node){
        if(node == null) return 0;

        int leftSum = dfsPure(node.left);
        int rightSum = dfsPure(node.right);

        // Choose the larger branch
        return node.value + Math.max(leftSum, rightSum);
    }

    public static int getPurePathSum(TreeNode root){
        if(root == null) return 0;
        return dfsPure(root);
    }

}
