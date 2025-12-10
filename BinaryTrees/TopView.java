package BinaryTrees;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**Given a binary tree, render an array containing the top view of the tree
 * 
 * INTUTION:
 * 
 * Consider the horizontal displacement of nodes from root, where left distances expand to the negative and
 * right distances expand to positive.
 * 
 * Maintain a mapping between hd->Node and use level-order traversal (BFS)
 * 
 * KEY: THE ONLY DIFFERENCE BETWEEN TOP VIEW AND BOTTOM VIEW is that in bottom view, we allow lower branches
 * to overwrite upper branches if they have the same horizontal displacement(hd).
 */

class BinaryTreeNode{
    public BinaryTreeNode left;
    public BinaryTreeNode right;
    public int value;

    public BinaryTreeNode(BinaryTreeNode left,BinaryTreeNode right, int val){
        this.left=left;
        this.right=right;
        this.value=val;
    }
}

class QueueNode{
    public BinaryTreeNode node;
    public int hd;

    public QueueNode(BinaryTreeNode node,int hd){
        this.node=node;
        this.hd=hd;
    }
}
public class TopView {

    public static List<BinaryTreeNode> getTopView(BinaryTreeNode root){
        if(root==null){
            return Collections.emptyList();
        }

        // Map to store the mapping between the node hd->Node

        Map<Integer,BinaryTreeNode> hdMap= new HashMap<>();

        ArrayDeque<QueueNode> queue=new ArrayDeque<>();

        //insert root
        queue.add(new QueueNode(root,0));

        //perform levelorder traversal of the tree and record the nodes
        while(!queue.isEmpty()){
            QueueNode qNode=queue.poll();

            BinaryTreeNode node=qNode.node;
            int hd=qNode.hd;

            //don't overwrite the nodes since the lower branchers are obscured by the uppermost ones
            hdMap.putIfAbsent(hd, node);  //for bottom view; allow upper nodes to overwrite id. hdMap.put(hd,node)
        
            //visit the right and left
            if(node.left!=null){
                queue.add(new QueueNode(node.left,hd-1));
            }

            if(node.right!=null){
                queue.add(new QueueNode(node.right, hd+1));
            }
           
        }

        //extract the hds from the hdMap, sorted in ascending order

       // List<Integer> sortedHds=hdMap.keySet().stream().sorted().collect(Collectors.toList());
        List<Integer> sortedHds = new ArrayList<>(hdMap.keySet());
        Collections.sort(sortedHds);


        List<BinaryTreeNode> result=new ArrayList<>();
        for(int i=0;i<sortedHds.size();i++){
            //get the hd and mapped node
            result.add(hdMap.get(sortedHds.get(i)));
        }

        return result;

    }

    public static List<Integer> getTopViewValues(List<BinaryTreeNode> topViewNodes){
        return topViewNodes.stream()
                           .map(i->i.value)
                           .collect(Collectors.toList());
    }

    public static void main(String[] args) {
        /*
                  1
               /     \
              2       3
             / \     / \
            4   5   6   7
                  \
                   8
        */

        BinaryTreeNode root =
            new BinaryTreeNode(
                new BinaryTreeNode(
                    new BinaryTreeNode(null, null, 4),
                    new BinaryTreeNode(null, null, 5),
                    2
                ),
                new BinaryTreeNode(
                    new BinaryTreeNode(null, null, 6),
                    new BinaryTreeNode(null, null, 7),
                    3
                ),
                1
            );

        // Add deeper node to test top-view correctness
        root.right.left.right = new BinaryTreeNode(null, null, 8);

        List<BinaryTreeNode> topView = TopView.getTopView(root);

        System.out.println("Top View:");
        for (BinaryTreeNode node : topView) {
            System.out.print(node.value + " "); //expect: 4 2 1 3 7
        }
    }
}
