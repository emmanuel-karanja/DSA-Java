package BinaryTrees;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;

/**Serialize a binary tree and also provide a way to reform it from the serialized data.
 * 
 * INTUTION
 * 
 * Do a level-order traversal of the tree i.e. root->left->right per level and store in an array,
 * any node that's null is given a special marker.
 * 
 * Use level-order traversal with BFS.
 * 
 * Deserializing works now in reverse. Parse the result string and reconstitute the tree.
 * 
 * BFS BASED ONES:
 * Basically,root-->left-->right per level.
 * 
 * DFS BASED ONES:
 * Preorder = root -->left subtree -->right subtre
 * Inorder=  left subtree-->root-->right subtree
 * Post-order = left subtree-->right subtree-->root
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
public class SerializeAndDeserialize {

    public static String serialize(TreeNode root){
        if(root==null) return "";

        List<String> result=new ArrayList<>();
        ArrayDeque<TreeNode> queue=new ArrayDeque<>();

        queue.add(root);

        while(!queue.isEmpty()){
            TreeNode node=queue.poll();

            if(node!=null){
                String asString=String.valueOf(node.value);
                result.add(asString);
                queue.add(node.left);
                queue.add(node.right);
            }else{
                //we 
                result.add("#"); //a special sentinel for this purpose
            }
        }

        // Optional: trim trailing "#", find the index of the trailing '#'
        int i = result.size() - 1;
        while (i >= 0 && result.get(i).equals("#")) {
            i--;
        }

    
        result = result.subList(0, i + 1);

        return String.join(",",result); //note this

    }

    public static TreeNode deSerialize(String preorder){
        if(preorder==null || preorder.length()==0){
            return null;
        }

        String[] values=preorder.split(",");

        int index=0;
        //create tree
        TreeNode root=new TreeNode(Integer.parseInt(values[index++]));

        ArrayDeque<TreeNode> queue=new ArrayDeque<>();

        queue.add(root);

        while(!queue.isEmpty() && index < values.length){
             TreeNode node=queue.poll();

             //deserialize left
             if(!values[index].equals("#")){ //not null
                TreeNode left= new TreeNode(Integer.parseInt(values[index]));

                node.left=left;
                queue.add(left);

             }

             
             //increement index
             index++;

            if (index >= values.length) break;  //we check here since it could surpass.

             if(!values[index].equals("#")){
                TreeNode right= new TreeNode(Integer.parseInt(values[index]));

                node.right=right;
                queue.add(right);
             }

             //incremement index
             index++;
        }

      return root;
    }
    
}
