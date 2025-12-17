package BinaryTrees;
/**Path sum is the total sum of values from any node to any other node.
 * 
 * Find the maxuimum of this given a binary tree.
 * 
 * INTUTION:
 * 
 * Use DFS and then keeptrack of the maxSum and use a diameter like computation . i.e.
 *   currentPathSum=leftPathSum+rightPathsum+currentNode.value
 * 
 * 
 * 
 * Take into account that a given node can have negative pathsum value
 * 
 * To return the bestPath, keep track of it.
 */

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

public class MaximumPathSum {
    
    public static int maxPathSum=Integer.MIN_VALUE; //to care for negative values

    //you can use final int[] maxPathSum={Integer.MIN_VALUE} and have it as an array of one element to
    //avoid static state issues

    private static int dfs(TreeNode node){
        if(node==null) return 0;

        int leftGain=Math.max(dfs(node.left),0); //do this to account for negative sums
        int rightGain=Math.max(dfs(node.right),0); //

        int currentPathSum=node.value+leftGain+rightGain;

        maxPathSum=Math.max(currentPathSum,maxPathSum);

        return node.value+Math.max(leftGain,rightGain);
    }

    public static int getPathSum(TreeNode root){
        if(root==null) return 0;

        maxPathSum = Integer.MIN_VALUE;//reset since maxPathSum is static
        dfs(root);
        return maxPathSum;
    }

     public static void main(String[] args) {
        /*
         * Constructing the following binary tree:
         * 
         *           10
         *          /  \
         *         2   10
         *        / \    \
         *      20   1   -25
         *                 / \
         *                3   4
         */

        // Leaf nodes
        TreeNode node20 = new TreeNode(null, null, 20);
        TreeNode node1  = new TreeNode(null, null, 1);
        TreeNode node3  = new TreeNode(null, null, 3);
        TreeNode node4  = new TreeNode(null, null, 4);

        // Intermediate nodes
        TreeNode node2   = new TreeNode(node20, node1, 2);
        TreeNode nodeNeg = new TreeNode(node3, node4, -25);
        TreeNode node10R = new TreeNode(null, nodeNeg, 10);

        // Root node
        TreeNode root = new TreeNode(node2, node10R, 10);

        int maxSum = MaximumPathSum.getPathSum(root);
        System.out.println("Maximum Path Sum: " + maxSum);
    }
}
