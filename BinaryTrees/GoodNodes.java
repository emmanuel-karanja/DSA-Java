package BinaryTrees;
/**Given a binary tree, a node X is good if the path from the root to the node there are no nodes with
 * values greater than X.
 * 
 * Return the count of good nodes in the tree.
 * 
 * INTUTION:
 * 
 * DFS.
 * Keep track of good nodes via the node.value >= currentMax. 
 */

class TreeNode{
    public TreeNode left;
    public TreeNode right;
    public int value;
    public TreeNode(int value){
        this.value=value;
    }
}
public class GoodNodes {
    
    public static int count;
    
    public static int getGoodNodeCount(TreeNode root){
        if(root==null) return 0;

        count=0; //reset count

        //dfs 
        dfs(root,0);

        return count;
    }

    private static void dfs(TreeNode node,int maxVal){
        if(node == null) return;

        if(node.value >=maxVal) count++;
  
        int newMax=Math.max(node.value,maxVal);

        //iterate left
        dfs(node.left,newMax);
        dfs(node.right,newMax);
    }


    public static void main(String[] args) {
        // Construct the binary tree
        TreeNode root = new TreeNode(3);
        root.left = new TreeNode(1);
        root.right = new TreeNode(4);
        root.left.left = new TreeNode(3);
        root.right.left = new TreeNode(1);
        root.right.right = new TreeNode(5);

        /*
                 3
                / \
               1   4
              /   / \
             3   1   5
        */

        int goodNodes = GoodNodes.getGoodNodeCount(root);
        System.out.println("Number of good nodes: " + goodNodes);
    }
}
