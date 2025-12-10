package BinaryTrees;

/**A tree diameter is the longest path between any two nodes in the tree. And it doesn't necessarily
 * go through the root.
 * 
 * How tomeasure? Clarify if we are counting nodes or counting edges.
 * 
 * Why? It impacts how height is calculated:
 *   height in edges when node==null is -1
 *   height in nodes when node==null is 0;
 */

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
public class TreeDiameter {

    public static int maxDiameter=0;

    private static int dfs(TreeNode node){

        if(node==null) return -1 ; //for height counted in edges

        int leftHeight=dfs(node.left);
        int rightHeight=dfs(node.right);

        int localDiameter=leftHeight+rightHeight+2;  //height in edges
        maxDiameter=Math.max(localDiameter,maxDiameter);

        return Math.max(rightHeight,leftHeight)+1; //calculates the height
    }

    public static int getDiameter(TreeNode root){
        if(root == null) return -1;

        
        dfs(root);

        return maxDiameter;
    }

    public static void main(String[] args) {
        /*
                  1
                 / \
                2   3
               / \
              4   5
                   \
                    6
        */
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);
        root.left.left = new TreeNode(4);
        root.left.right = new TreeNode(5);
        root.left.right.right = new TreeNode(6);

        int diameter = getDiameter(root);
        System.out.println("Diameter of the tree: " + diameter);
    }
    
}
