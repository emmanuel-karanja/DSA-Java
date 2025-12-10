package BinaryTrees;
/**Find if a tree is balanced.
 * 
 * A tree is balanced if the heights of the left and right subtrees of every node differ by at most 1.
 * 
 * INTUTION:
 *
 * Use DFS
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
public class IsTreeBalanced {
    public static boolean isBalanced(TreeNode root){
         return dfsHeight(root)!=-1;
    }

    private static int dfsHeight(TreeNode node){
        //a null node has a height of 0
        if(node==null) return 0;

        int leftHeight=dfsHeight(node.left);
        if(leftHeight == -1) return -1;

        int rightHeight=dfsHeight(node.right);
        if(rightHeight==-1) return -1;

        //check the balanced condition
        if(Math.abs(leftHeight-rightHeight)>1) return -1;

        return Math.max(leftHeight,rightHeight)+1 ; 
    }

    public static void main(String[] args) {
        /*
              1
             / \
            2   3
           / \
          4   5
        */

        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);
        root.left.left = new TreeNode(4);
        root.left.right = new TreeNode(5);

        System.out.println("Is balanced? " + isBalanced(root));

        // Make it unbalanced
        root.left.left.left = new TreeNode(6);
        System.out.println("Is balanced after adding extra node? " + isBalanced(root));
    }
}
