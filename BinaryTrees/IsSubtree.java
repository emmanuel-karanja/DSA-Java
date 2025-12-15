package BinaryTrees;
/**For completeness.
 * 
 *
 * 1. A tree is a subtree of itself.
 * 2. Both are null
 * 3. They are the same tree or p's left subtree is a subtree or p.right is a subtree
 * 
 * In this case  is q a subtree of p?
 */

class TreeNode{
    public TreeNode left;
    public TreeNode right;
    public int value;
    public TreeNode(int value){
        this.value=value;
    }
}

public class IsSubtree {
     public static boolean isSubTree(TreeNode p,TreeNode q){
        if(p==null) return false;
        return isSameTree(p, q) || isSubTree(p.left,q) ||isSubTree(p.right,q);

    }

    private static boolean isSameTree(TreeNode p, TreeNode q){
        if(p==null && q==null) return true;
        if(p==null || q==null) return false;

        //they are both not null, and the value is the same and left subtrees are same stree
        return p.value==q.value && 
               isSameTree(p.left,q.left) && 
               isSameTree(p.right, q.right);
    }
}
