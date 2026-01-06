package BinaryTrees;

import java.util.*;

/**
 * PROBLEM: Mode(s) of a Binary Tree
 *
 * You are given the root of a binary tree (not necessarily a BST).
 * The mode(s) are the value(s) that appear most frequently in the tree.
 *
 * Return all modes.
 *
 * ------------------------------------------------------------
 * APPROACH (DFS + Frequency Map):
 *
 * 1. Traverse the entire tree using DFS.
 * 2. Maintain a map from value → frequency.
 * 3. Track the maximum frequency seen so far.
 * 4. After traversal, collect all values whose frequency
 *    equals the maximum frequency.
 *
 * ------------------------------------------------------------
 * TIME COMPLEXITY:
 * - O(n), where n is the number of nodes
 *
 * SPACE COMPLEXITY:
 * - O(n), for the frequency map
 *
 * ------------------------------------------------------------
 */

public class ModeOfBinaryTree {

    static class TreeNode {
        int val;
        TreeNode left, right;
        TreeNode(int v) { val = v; }
    }

    private static Map<Integer, Integer> freq = new HashMap<>();
    private static int maxFreq = 0;

    public static int[] findMode(TreeNode root) {
        dfs(root);

        List<Integer> modes = new ArrayList<>();
        for (Map.Entry<Integer, Integer> entry : freq.entrySet()) {
            if (entry.getValue() == maxFreq) {
                modes.add(entry.getKey());
            }
        }

        return modes.stream().mapToInt(i -> i).toArray();
    }

    private static void dfs(TreeNode node) {
        if (node == null) return;

        int count = freq.getOrDefault(node.val, 0) + 1;
        freq.put(node.val, count);
        maxFreq = Math.max(maxFreq, count);

        dfs(node.left);
        dfs(node.right);
    }
}
