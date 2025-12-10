package BinaryTrees;
/**You are given the root of a binary tree. Each node contains a number from 0 to 9.
 * And each path from root to left is a number e.g. 1->2->3-4 to form 1234 as the value.
 * 
 * Find the totalvalue(sumo of all the numbers) of the binary tree.
 * 
 * INTUTION:
 * 
 * Do a DFS and compose the number from root to leaf. And then add that to the total.
 * 
 * *If you were asked to find the largest number, only the evaluation within the finding of
 * the number would change e.g. instead of total, we could do Math.max
 */

class TreeNode{
    public int value;
    public TreeNode left;
    public TreeNode right;

    public TreeNode(TreeNode left, TreeNode right, int value){
        this.value=value;
        this.left=left;
        this.right=right;
    }
}

public class NumbersSum {
    
    private static int dfs(TreeNode node, int currentNumber){
        if(node==null){
            return 0;
        }

       //calculate current number, 
       currentNumber=currentNumber*10 + node.value;

       //if leaf do total
       if(node.left==null && node.right==null){
         return currentNumber;
       }

       //sum left and right subtrees

       return dfs(node.left,currentNumber)+dfs(node.right,currentNumber);

       //to find max you'd need return  Math.max(dfs(left,currentNum),dfs(right,currentNum))
    

    }

    public static int getTotalSum(TreeNode root){
        if(root==null) return 0;

        int totalSum= dfs(root,0);
        return totalSum;
    }

    public static void main(String[] args) {

        /*
            Construct the tree:

                    1
                   / \
                  2   3
                 / \   \
                4   5   6
        */

        TreeNode n4 = new TreeNode(null, null, 4);
        TreeNode n5 = new TreeNode(null, null, 5);
        TreeNode n6 = new TreeNode(null, null, 6);

        TreeNode n2 = new TreeNode(n4, n5, 2);
        TreeNode n3 = new TreeNode(null, n6, 3);

        TreeNode root = new TreeNode(n2, n3, 1);

        int totalSum = NumbersSum.getTotalSum(root);

        System.out.println("Total sum of root-to-leaf numbers: " + totalSum);
    }

}
