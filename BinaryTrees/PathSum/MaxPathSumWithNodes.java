package BinaryTrees.PathSum;
/**Key:
 * 
 * Find the most profitable route i.e. that'll not reduce the current maxSum and then form the bestPath
 * 
 */
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

public class MaxPathSumWithNodes {

    private static int maxPathSum = Integer.MIN_VALUE;
    private static List<TreeNode> bestPath = new ArrayList<>();

    // Helper class to return both sum and path
    private static class Result {
        int sum;            // max sum that can be extended to parent
        List<TreeNode> path; // corresponding path
        Result(int sum, List<TreeNode> path) {
            this.sum = sum;
            this.path = path;
        }
    }

    private static Result dfs(TreeNode node) {
        if (node == null) return new Result(0, new ArrayList<>());

        Result left = dfs(node.left);
        Result right = dfs(node.right);

        // Take only positive gains i.e. only those paths that'll add something to the current sum (profitable)
        int leftSum = Math.max(left.sum, 0);
        int rightSum = Math.max(right.sum, 0);

        // Update global max sum including both sides + current node
        int currentSum = node.value + leftSum + rightSum;

        if (currentSum > maxPathSum) {
            maxPathSum = currentSum;

            // Build best path: left + node + right
            bestPath = new ArrayList<>();  //reset best path
            bestPath.addAll(leftSum > 0 ? left.path : new ArrayList<>());
            bestPath.add(node);
            bestPath.addAll(rightSum > 0 ? right.path : new ArrayList<>());
        }

        // Return the path that can be extended to parent (max side)
        if (leftSum > rightSum) {
            List<TreeNode> extendPath = new ArrayList<>(leftSum > 0 ? left.path : new ArrayList<>());
            extendPath.add(node);
            return new Result(node.value + leftSum, extendPath);
        } else {
            List<TreeNode> extendPath = new ArrayList<>(rightSum > 0 ? right.path : new ArrayList<>());
            extendPath.add(node);
            return new Result(node.value + rightSum, extendPath);
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
