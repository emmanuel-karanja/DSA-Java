package BinaryTrees;

import java.util.ArrayList;
import java.util.List;



/**Balance a binary search tree.
 * 
 
   INTUITION:
 *  - Perform an inorder traversal to produce a sorted list,
 *  - then recursively construct a height-balanced BST by
 *  - selecting the middle element as root.
 * 
 * **Inorder traversal already generates a sorted array. But if you started with an array, you'd sort it first.
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
public class BalanceTree {

    private static void inorderTraverse(TreeNode root, List<Integer> result){
        if(root==null) return;

        inorderTraverse(root.left, result);
        result.add(root.value);
        inorderTraverse(root.right, result);
    
    }

    private static TreeNode buildTree(int[] arr, int left,int right){
        if(left> right) return null;

        int mid=left+(right-left)/2; //once again to avoid the overflow
        TreeNode node=new TreeNode(arr[mid]);

        node.left=buildTree(arr, left, mid-1);
        node.right=buildTree(arr, mid+1, right);

        return node;
    }

    public static TreeNode buildBalancedBST(TreeNode root){
        if(root==null) return null;

        List<Integer> result=new ArrayList<>();
        inorderTraverse(root,result);

        int[] plainArray=result.stream()
                               .mapToInt(Integer::intValue)
                               .toArray();

        return buildTree(plainArray,0,result.size()-1);
        
    }

}
