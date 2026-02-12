package BinaryTrees;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**Find the left view of a binary tree i.e. the view of the left side nodes.
 * 
 * INTUTION:
 * 
 * Use level order traversal (BFS) and at each level, get the queue.size() as level size, iterate and add
 * the node at position 0 in the result.
 * 
 * KEY NOTE: Rightview we do the same thing only we check for tne queue.size()-1 element
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


public class LeftView {

    public static List<Integer> getLeftView(TreeNode root){
        if(root==null){
            return Collections.emptyList();
        }

        List<Integer> result=new ArrayList<>();

        ArrayDeque<TreeNode> queue=new ArrayDeque<>();
       
        queue.add(root);

        while(!queue.isEmpty()){
            int levelSize=queue.size();

            for(int i=0;i<levelSize;i++){
                TreeNode node=queue.poll();

                if(i==0){
                    result.add(node.value);
                }

                //explore left and right
                if(node.left!=null) queue.add(node.left);
                if(node.right!=null) queue.add(node.right);
                
            }
        }

        return result;
    }

    public static void main(String[] args) {

        /*
                1
               / \
              2   3
             / \   \
            4   5   6
                 \
                  7
        */

        TreeNode root = new TreeNode(1);

        root.left = new TreeNode(2);
        root.right = new TreeNode(3);

        root.left.left = new TreeNode(4);
        root.left.right = new TreeNode(5);

        root.right.right = new TreeNode(6);

        root.left.right.right = new TreeNode(7);

        List<Integer> leftView = LeftView.getLeftView(root);

        System.out.println("Left view of the tree: " + leftView);
    }   
}
