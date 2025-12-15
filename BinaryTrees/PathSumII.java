package BinaryTrees;
/**
Given a binary tree and a target sum, return all root-to-leaf paths where the sum of node values equals the target sum.
Each path should be represented as a list of node values.
A leaf is defined as a node with no children. 

INTUTION:

DFS traversal of the tree and return when the remainingSum==0 or currentNode.value==remainingSum

ending conditions are:

1. Current node's left and right children are null. it's a leaf node.
2. node.value=remainingSum

Some backtracking is required to remove current node from the path incase we branch off*/
import java.util.*;

class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;

    TreeNode(int val) { this.val = val; }
}

public class PathSumII {

    public static List<List<Integer>> pathSum(TreeNode root, int targetSum) {
        List<List<Integer>> result = new ArrayList<>();
        if (root == null) return result;

        dfs(root, targetSum, new ArrayList<>(), result);
        return result;
    }

    private static void dfs(TreeNode node, int remainingSum, List<Integer> path, List<List<Integer>> result) {
        if (node == null) return;

        // Add current node to the path
        path.add(node.val);

        // Check if it's a leaf and sum matches, this is the end condition 
        if (node.left == null && node.right == null && node.val == remainingSum) {
            //make a copy of the current path and add it to the list of solutions
            result.add(new ArrayList<>(path));
        } else {
            // Recurse left and right with updated remaining sum
            dfs(node.left, remainingSum - node.val, path, result);
            dfs(node.right, remainingSum - node.val, path, result);
        }

        // Backtrack: remove current node before returning, we don't re-add the value of the removed node since
        // the remainingSum is passed by value. so the remainingSum initial value is unchanged.
        // if had been a global variable, we'd need to re-add.
        path.remove(path.size() - 1);  
    }

    // Example usage
    public static void main(String[] args) {
        /*
         * Tree:
         *       5
         *      / \
         *     4   8
         *    /   / \
         *   11  13  4
         *  /  \    / \
         * 7    2  5   1
         */
        TreeNode root = new TreeNode(5);
        root.left = new TreeNode(4);
        root.right = new TreeNode(8);
        root.left.left = new TreeNode(11);
        root.left.left.left = new TreeNode(7);
        root.left.left.right = new TreeNode(2);
        root.right.left = new TreeNode(13);
        root.right.right = new TreeNode(4);
        root.right.right.left = new TreeNode(5);
        root.right.right.right = new TreeNode(1);

        int targetSum = 22;

        List<List<Integer>> paths = pathSum(root, targetSum);
        System.out.println(paths); // [[5,4,11,2], [5,8,4,5]]
    }
}

