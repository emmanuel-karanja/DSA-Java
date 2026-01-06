import java.util.*;

/**
 * PROBLEM: Mode(s) of a Binary Search Tree (BST)
 *
 * You are given the root of a Binary Search Tree (BST).
 * The mode(s) are the value(s) that appear most frequently in the tree.
 *
 * Unlike a general binary tree, a BST has an important property:
 * - An inorder traversal of a BST produces values in sorted order.
 *
 * GOAL:
 * Return all mode values (there may be more than one).
 *
 * ------------------------------------------------------------
 * KEY OBSERVATION:
 *
 * Because inorder traversal yields values in sorted order,
 * equal values appear consecutively.
 *
 * This allows us to:
 * - Avoid using a HashMap
 * - Compute frequencies on the fly
 * - Use O(1) extra space (excluding recursion stack)
 *
 * ------------------------------------------------------------
 * APPROACH (Inorder Traversal with State Tracking):
 *
 * We maintain:
 * - prevVal     → value of the previously visited node
 * - currCount   → count of the current value streak
 * - maxCount    → maximum frequency seen so far
 * - result      → list of mode values
 *
 * Steps:
 * 1. Perform an inorder traversal (left → node → right).
 * 2. If current value == prevVal, increment currCount.
 *    Otherwise, reset currCount to 1.
 * 3. Compare currCount with maxCount:
 *      - If currCount > maxCount:
 *          • clear result
 *          • add current value
 *          • update maxCount
 *      - If currCount == maxCount:
 *          • add current value
 * 4. Update prevVal.
 *
 * ------------------------------------------------------------
 * TIME COMPLEXITY:
 * - O(n), where n is the number of nodes
 *
 * SPACE COMPLEXITY:
 * - O(1) extra space (ignoring recursion stack)
 *
 * ------------------------------------------------------------
 */

public class ModeOfBST {

    // Definition for a BST node
    static class TreeNode {
        int val;
        TreeNode left, right;

        TreeNode(int val) {
            this.val = val;
        }
    }

    private static Integer prevVal = null;
    private static int currCount = 0;
    private static int maxCount = 0;
    private static List<Integer> modes = new ArrayList<>();

    public static int[] findMode(TreeNode root) {
        inorder(root);
        return modes.stream().mapToInt(i -> i).toArray();
    }

    private static void inorder(TreeNode node) {
        if (node == null) return;

        inorder(node.left);

        // Process current node
        if (prevVal == null || node.val != prevVal) {
            currCount = 1;
        } else {
            currCount++;
        }

        if (currCount > maxCount) {
            maxCount = currCount;
            modes.clear();
            modes.add(node.val);
        } else if (currCount == maxCount) {
            modes.add(node.val);
        }

        prevVal = node.val;

        inorder(node.right);
    }

    // Driver code
    public static void main(String[] args) {
        /*
                  2
                 / \
                1   2

            Inorder traversal: [1, 2, 2]
            Mode = 2
         */

        TreeNode root = new TreeNode(2);
        root.left = new TreeNode(1);
        root.right = new TreeNode(2);

        System.out.println(Arrays.toString(findMode(root))); // Output: [2]
    }
}
