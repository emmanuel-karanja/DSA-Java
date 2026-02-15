package BinaryTrees;

/**
 * Problem Statement:
 * Compute the diameter of a binary tree using BFS (level-order traversal).
 *
 * Definitions:
 * - Height of a tree: longest path from root to any leaf (in edges).
 * - Diameter of a tree: length of the longest path between any two nodes in the tree (in edges). 
 *   This path does NOT have to pass through the root.
 *
 * Intuition:
 * - The diameter is always the distance between two farthest nodes in the tree.
 * - BFS can be used to find the farthest node from any starting node.
 * - Algorithm (2 BFS trick):
 *   1. Pick any node (usually root) and run BFS to find the farthest node `u`.
 *   2. Run BFS from `u` to find the farthest node `v` from it.
 *   3. The distance between `u` and `v` is the diameter of the tree.
 *
 * Approach:
 * - Use a queue to perform BFS while tracking distance from the start node.
 * - Keep track of visited nodes to avoid revisiting.
 * - Perform two BFS runs to determine the diameter.
 *
 * Time Complexity: O(n), where n is the number of nodes.
 * Space Complexity: O(n) for queue and visited set.
 */

import java.util.*;

class TreeNode {
    int val;
    TreeNode left, right;
    TreeNode(int val) { this.val = val; }
}


public class TreeDiameterBFS{

    static class Pair {
        TreeNode node;
        int dist;
        Pair(TreeNode node, int dist) {
            this.node = node;
            this.dist = dist;
        }
    }

    // STEP 1: Build the "Back Links"
    private static void populateParents(TreeNode node, TreeNode parent, Map<TreeNode, TreeNode> parentMap) {
        if (node == null) return;
        parentMap.put(node, parent);
        populateParents(node.left, node, parentMap);
        populateParents(node.right, node, parentMap);
    }

    // STEP 2: BFS that can move in 3 directions
    private static Pair bfs(TreeNode start, Map<TreeNode, TreeNode> parentMap) {
        Queue<Pair> q = new ArrayDeque<>();
        Set<TreeNode> visited = new HashSet<>();

        q.offer(new Pair(start, 0));
        visited.add(start);
        Pair farthest = new Pair(start, 0);

        while (!q.isEmpty()) {
            Pair current = q.poll();
            TreeNode node = current.node;
            int dist = current.dist;

            if (dist > farthest.dist) {
                farthest = current;
            }

            // Direction 1: Left
            if (node.left != null && !visited.contains(node.left)) {
                visited.add(node.left);
                q.offer(new Pair(node.left, dist + 1));
            }
            // Direction 2: Right
            if (node.right != null && !visited.contains(node.right)) {
                visited.add(node.right);
                q.offer(new Pair(node.right, dist + 1));
            }
            // Direction 3: UP (The Back Link)
            TreeNode parent = parentMap.get(node);
            if (parent != null && !visited.contains(parent)) {
                visited.add(parent);
                q.offer(new Pair(parent, dist + 1));
            }
        }
        return farthest;
    }

    public static int diameter(TreeNode root) {
        if (root == null) return 0;

        Map<TreeNode, TreeNode> parentMap = new HashMap<>();
        populateParents(root, null, parentMap);

        // First BFS: Start at root, find a leaf node (u)
        Pair u = bfs(root, parentMap);

        // Second BFS: Start at leaf (u), find the farthest node (v)
        Pair v = bfs(u.node, parentMap);

        return v.dist;
    }

    public static void main(String[] args) {
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);
        root.left.left = new TreeNode(4);
        root.left.right = new TreeNode(5);
        root.left.left.left = new TreeNode(6);

        System.out.println("Diameter of tree = " + diameter(root));
    }
}
