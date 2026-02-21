package DP.TreeDP;

/**
 * PROBLEM: Tree Diameter (Longest Path in a Tree)
 * 
 * STATEMENT:
 * Given a tree (an acyclic connected graph) with nodes, find the length of the longest path 
 * between any two nodes in the tree. The path does not need to pass through the root.
 * 
 * DOMAIN: Tree / Tree DP
 * 
 * REASONING:
 * - Use post-order DFS to compute two pieces of information per node:
 *     1. maxDepth: the maximum depth from this node down to a leaf.
 *     2. maxDiameter: the maximum diameter found in the subtree rooted at this node.
 * - At each node, we combine information from children:
 *     - maxDepth = 1 + max(maxDepth of children)
 *     - maxDiameter = max(maxDiameter of children, sum of top two maxDepths among children + 2)
 *       (the +2 accounts for edges connecting the two deepest subtrees through this node)
 * - Return maxDiameter at root as final answer.
 * 
 * TIME COMPLEXITY: O(N) — each node visited once
 * SPACE COMPLEXITY: O(N) — call stack for DFS
 */

import java.util.*;

class TreeDiameter {

    // Node class
    static class Node {
        int val;
        List<Node> children = new ArrayList<>();
        Node(int val) { this.val = val; }
    }

    // DPState for each node
    static class DPState {
        int maxDepth;     // longest path from this node down to leaf
        int maxDiameter;  // maximum diameter in the subtree
        DPState(int maxDepth, int maxDiameter) {
            this.maxDepth = maxDepth;
            this.maxDiameter = maxDiameter;
        }
    }

    // Base state: leaf node
    private DPState baseState(Node node) {
        // A leaf has depth 0, diameter 0
        return new DPState(0, 0);
    }

    // DFS + transition
    private DPState dfs(Node node) {
        if (node.children.isEmpty()) return baseState(node);

        DPState curr = new DPState(0, 0);

        // Store top two max depths among children
        int firstMax = -1, secondMax = -1;

        for (Node child : node.children) {
            DPState childDP = dfs(child);

            // Update top two depths
            int depth = childDP.maxDepth;
            if (depth > firstMax) {
                secondMax = firstMax;
                firstMax = depth;
            } else if (depth > secondMax) {
                secondMax = depth;
            }

            // Update diameter in subtree
            curr.maxDiameter = Math.max(curr.maxDiameter, childDP.maxDiameter);
        }

        // max depth from current node
        curr.maxDepth = firstMax + 1;

        // possible diameter passing through this node
        if (secondMax != -1) {
            curr.maxDiameter = Math.max(curr.maxDiameter, firstMax + secondMax + 2);
        } else {
            curr.maxDiameter = Math.max(curr.maxDiameter, firstMax); // single branch
        }

        return curr;
    }

    // Entry point
    public int treeDiameter(Node root) {
        DPState result = dfs(root);
        return result.maxDiameter;
    }

    // Example usage
    public static void main(String[] args) {
        Node root = new Node(0);
        Node n1 = new Node(1);
        Node n2 = new Node(2);
        Node n3 = new Node(3);
        Node n4 = new Node(4);
        Node n5 = new Node(5);

        root.children.add(n1);
        root.children.add(n2);
        n1.children.add(n3);
        n1.children.add(n4);
        n2.children.add(n5);

        TreeDiameter solver = new TreeDiameter();
        System.out.println("Tree Diameter: " + solver.treeDiameter(root)); // Output: 4
    }
}
