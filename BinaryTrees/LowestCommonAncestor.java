package BinaryTrees;
/**Given two binary tree nodes, find the lowest commom ancestor. i.e. the lowest parent node they have in common.
 * 
 * INTUTION
 * Use dfs to narrow down on the branch this parent is on.
 * 
 * Now for Binary Search Tree, you can use the while loop, it's highly optimal.
 */

class TreeNode{
    public TreeNode left;
    public TreeNode right;
    public int value;

    public TreeNode(int value){
        this.value=value;
    }
}
public class LowestCommonAncestor {

     public static TreeNode lca(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null || root == p || root == q) return root;

        TreeNode left = lca(root.left, p, q);
        TreeNode right = lca(root.right, p, q);

        if (left != null && right != null) return root;  //if both are not null return root.

        return left != null ? left : right; //else return the non-null one.
    }

    public static TreeNode lcaIterative(TreeNode root, TreeNode p, TreeNode q){
         if(root==null) return null;


         while(root!=null){
            //is it left
            if(p.value < root.value && q.value < root.value){
                root=root.left;
            }else if(p.value >root.value && q.value >root.value){
                //it's right
                root=root.right;
            }else{ //i.e. 
                return root;
            }
         }

         return null;
        
    }
}
