package BinaryTrees;
/**Enhancement:
 * 
 *  Keep a parentMap i.e. nod-->parent
 *  it does nothing to help you calculate the diameter it just allows you to be able to generate the path later.
 * 
 *  
 */

import java.util.*;

class TreeNode {
    int val;
    TreeNode left, right;
    TreeNode(int val) { this.val = val; }
}

public class TreeDiameterBFSWithNodes {

    static class Pair {
        TreeNode node;
        int dist;
        Pair(TreeNode node, int dist) {
            this.node = node;
            this.dist = dist;
        }
    }

    /**
     * Step 1: Pre-process the tree to create back-links (child -> parent).
     * Without this, BFS is trapped at leaf nodes and cannot move "up".
     */
    private static void buildParentMap(TreeNode node, TreeNode p, Map<TreeNode, TreeNode> allParents) {
        if (node == null) return;
        allParents.put(node, p);
        buildParentMap(node.left, node, allParents);
        buildParentMap(node.right, node, allParents);
    }

    /**
     * BFS that can move in 3 directions: Left, Right, and Parent (Up).
     * tracking 'cameFrom' allows us to reconstruct the actual path later.
     */
    private static Pair bfs(TreeNode start, Map<TreeNode, TreeNode> allParents, Map<TreeNode, TreeNode> cameFrom) {
        Queue<Pair> q = new ArrayDeque<>();
        Set<TreeNode> visited = new HashSet<>();

        q.offer(new Pair(start, 0));
        visited.add(start);
        cameFrom.put(start, null);

        Pair farthest = new Pair(start, 0);

        while (!q.isEmpty()) {
            Pair current = q.poll();
            TreeNode node = current.node;
            int dist = current.dist;

            if (dist > farthest.dist) {
                farthest = current;
            }

            // Neighbors: Left, Right, and Parent
            List<TreeNode> neighbors = new ArrayList<>();
            if (node.left != null) neighbors.add(node.left);
            if (node.right != null) neighbors.add(node.right);
            if (allParents.get(node) != null) neighbors.add(allParents.get(node));

            for (TreeNode neighbor : neighbors) {
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    cameFrom.put(neighbor, node); // Record for path reconstruction
                    q.offer(new Pair(neighbor, dist + 1));
                }
            }
        }
        return farthest;
    }

    public static int diameter(TreeNode root, TreeNode[] endpoints) {
        if (root == null) return 0;

        // 1. Build global parent map (The "Back Links")
        Map<TreeNode, TreeNode> allParents = new HashMap<>();
        buildParentMap(root, null, allParents);

        // 2. BFS from root to find one endpoint of the diameter (u)
        Map<TreeNode, TreeNode> cameFromU = new HashMap<>();
        Pair u = bfs(root, allParents, cameFromU);

        // 3. BFS from u to find the other endpoint (v)
        Map<TreeNode, TreeNode> cameFromV = new HashMap<>();
        Pair v = bfs(u.node, allParents, cameFromV);

        endpoints[0] = u.node;
        endpoints[1] = v.node;

        // Optional: Reconstruct the path from u to v
        List<Integer> path = new ArrayList<>();
        TreeNode curr = v.node;
        while (curr != null) {
            path.add(curr.val);
            curr = cameFromV.get(curr); 
        }
        System.out.println("Path: " + path);

        return v.dist; // diameter in edges
    }

    public static void main(String[] args) {
        // Build Example Tree:
        //       1
        //      / \
        //     2   3
        //    / \
        //   4   5
        //  /
        // 6
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);
        root.left.left = new TreeNode(4);
        root.left.right = new TreeNode(5);
        root.left.left.left = new TreeNode(6);

        TreeNode[] endpoints = new TreeNode[2];
        int dia = diameter(root, endpoints);

        System.out.println("Diameter = " + dia);
        System.out.println("Endpoints: " + endpoints[0].val + " and " + endpoints[1].val);
    }
}