package BinaryTrees;
/**Two tree are the same if and only if:
 * 
 * 1. Both are null.
 * 2. Both are not null and they both have the same value, and their left and then right subtrees are the same tree.
 * 
 * 
 * IS SUBTREE:
 * 
 * A tree is a subtree of itself.
 * 
 * 1. Both are null
 * 2. They are the same tree or p's left subtree is a subtree or p.right is a subtree
 */

class TreeNode{
    public TreeNode left;
    public TreeNode right;
    public int value;
    public TreeNode(int value){
        this.value=value;
    }
}
public class IsSameTree {

    public static boolean isSameTree(TreeNode p, TreeNode q){
        if(p==null && q==null) return true;
        if(p==null || q==null) return false;

        //they are both not null, and the value is the same and left subtrees are same stree
        return p!=null && q!=null && p.value==q.value && isSameTree(p.left,q.left) && isSameTree(p.right, q.right);
    }

    public static boolean isSubTree(TreeNode p,TreeNode q){
        if(p==null) return false;

        return isSameTree(p, q) || isSubTree(p.left,q) ||isSubTree(p.right,q);

    }
    
}
