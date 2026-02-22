package DP.TreeDP;

/**
 * Binary Tree Cameras Problem (Tree DP with State Compression)
 *
 * Problem:
 * Place cameras on nodes of a binary tree such that every node is monitored.
 * A camera at a node monitors:
 *   - the node itself
 *   - its immediate children
 *   - its parent
 *
 * Goal:
 * Minimize the total number of cameras needed.
 *
 * ----------------------------
 * DP BREAKDOWN (RUBRIC STYLE)
 * ----------------------------
 *
 * 1️⃣ Goal
 * Minimize the number of cameras to monitor all nodes in the tree.
 *
 * 2️⃣ State (compressed)
 * Instead of a DP table per node, we define 3 states:
 *   0: Node is NOT covered (needs a camera from parent)
 *   1: Node IS covered (monitored) but has no camera
 *   2: Node HAS a camera
 *
 * We will compute these states recursively per node.
 *
 * 3️⃣ Choices / Decisions
 * For each node:
 *   - Place a camera here (state 2)
 *   - Don’t place a camera (then node might be covered by children, state 1)
 *   - If node is a leaf, sometimes it forces parent to place a camera (state 0)
 *
 * 4️⃣ Recurrence Relation
 * For node:
 *
 *   state0 (not covered) = state1_left + state1_right
 *       → this forces parent to place a camera
 *
 *   state2 (camera here) = 1 + min(child0, child1, child2) for each child
 *       → camera at node covers itself + children
 *
 *   state1 (covered without camera) = min(
 *        child2 + min(child0, child1, child2)
 *   )  
 *
 * This is easier to implement by returning 3 values per node.
 *
 * 5️⃣ Base Cases
 * - Null node:
 *     - state0 = 0 (needs coverage from parent)
 *     - state1 = 0 (null node is trivially covered)
 *     - state2 = large value (can’t place camera)
 *
 * 6️⃣ Evaluation Order Rule
 * - Post-order traversal ensures children states are computed before parent.
 *
 * 7️⃣ Solution
 * - After computing root, minimum cameras = min(state1_root, state2_root)
 *
 * ----------------------------
 * DP PRINCIPLES
 * ----------------------------
 * - Optimal Substructure: min cameras at parent depends only on children states
 * - State Compression: only 3 states per node, no need for a full DP table
 * - Base State: handled explicitly for null nodes
 * - Complexity: O(N), space O(H) recursion stack
 */
class BinaryTreeCameras {

    static class Node {
        int val;
        Node left, right;

        Node(int val) {
            this.val = val;
        }
    }

    static final int INF = 1000000;

    public static int minCameraCover(Node root) {
        int[] result = dfs(root);
        // root must be covered, so min(state1, state2)
        return Math.min(result[1], result[2]);
    }

    // Returns {state0, state1, state2} for node
    // state0 = node not covered i.. covered by both children i.e. left[1]+right[1]
    // state1 = node covered without camera i.e. = min(coveredBySelfCamera,coveredByChildren) 
    // state2 = node has camera =1+ min(for all left children)+min(for all right children)
    private static int[] dfs(Node node) {
        if (node == null) {
            return new int[]{0, 0, INF}; // base state
        }

        int[] left = dfs(node.left);
        int[] right = dfs(node.right);

        int state0 = left[1] + right[1]; // node not covered → children must cover
        
        //this is state2= 1+min(for all left)+min(for all right)
        int state2 = 1 + Math.min(left[0], Math.min(left[1], left[2]))
                       + Math.min(right[0], Math.min(right[1], right[2]));
        int state1 = Math.min(state2, 
                             Math.min(
                             left[2] + Math.min(right[1], right[2]), 
                             right[2] + Math.min(left[1], left[2])
        ));

        return new int[]{state0, state1, state2};
    }

    // Example usage
    public static void main(String[] args) {
        Node root = new Node(0);
        root.left = new Node(0);
        root.right = new Node(0);
        root.left.left = new Node(0);
        root.left.right = new Node(0);

        System.out.println("Minimum cameras needed: " + minCameraCover(root));
    }
}

