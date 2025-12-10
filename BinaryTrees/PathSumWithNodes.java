package BinaryTrees;

import java.util.ArrayList;
import java.util.List;

class TreeNode {
    public TreeNode left;
    public TreeNode right;
    public int value;

    public TreeNode(TreeNode left, TreeNode right, int value) {
        this.left = left;
        this.right = right;
        this.value = value;
    }
}

public class PathSumWithNodes {
    private static int maxPathSum = Integer.MIN_VALUE;
    private static List<TreeNode> bestPath = new ArrayList<>();

    private static List<TreeNode> dfs(TreeNode node) {
        if (node == null) return new ArrayList<>();

        List<TreeNode> leftPath = dfs(node.left);
        List<TreeNode> rightPath = dfs(node.right);

        //see how the sum is calculated.
        int leftSum = leftPath.stream().mapToInt(n -> n.value).sum();
        int rightSum = rightPath.stream().mapToInt(n -> n.value).sum();

        // Only take positive contributions
        List<TreeNode> leftGain = leftSum > 0 ? leftPath : new ArrayList<>();
        List<TreeNode> rightGain = rightSum > 0 ? rightPath : new ArrayList<>();

        int currentSum = node.value +
                leftGain.stream().mapToInt(n -> n.value).sum() +
                rightGain.stream().mapToInt(n -> n.value).sum();

        if (currentSum > maxPathSum) {
            maxPathSum = currentSum;

            // Store the best path (left + node + right)
            bestPath = new ArrayList<>();
            bestPath.addAll(leftGain);
            bestPath.add(node);
            bestPath.addAll(rightGain);
        }

        // Return the path that can be extended upwards (max side)
        if (leftGain.stream().mapToInt(n -> n.value).sum() > rightGain.stream().mapToInt(n -> n.value).sum()) {
            List<TreeNode> extendPath = new ArrayList<>(leftGain);
            extendPath.add(node);
            return extendPath;
        } else {
            List<TreeNode> extendPath = new ArrayList<>(rightGain);
            extendPath.add(node);
            return extendPath;
        }
    }

    public static int getPathSum(TreeNode root) {
        maxPathSum = Integer.MIN_VALUE;
        bestPath.clear();

        dfs(root);

        return maxPathSum;
    }

    public static List<TreeNode> getBestPath() {
        return bestPath;
    }
}
