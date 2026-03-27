package TreeDP;
/**
 * Given a binary tree, place cameras on nodes so that every node is monitored.
 * - A camera at a node monitors:
 *     1. The node itself
 *     2. Its immediate children
 *     3. Its parent
 * - Goal: Minimize the total number of cameras needed.
 *
 * INTUITION:
 * Tree DP with three states per node can elegantly represent coverage.
 *
 * GOAL:
 *   Minimize the number of cameras needed to cover the entire tree.
 *
 * STATE:
 *   Each state represents the **coverage status of the node**, NOT the number of cameras at the node.
 *   For each node:
 *   - state0: Node is NOT covered (needs a camera from parent)
 *   - state1: Node IS covered but has NO camera
 *   - state2: Node HAS a camera
 *
 *   Each state stores the **minimum number of cameras required** in the subtree rooted at this node given that
 *   the node is in that state.
 *   So state2 does not mean “we place a camera and count 1 here only” — it represents **the optimal number of
 *   cameras for this subtree if this node has a camera**.
 *
 * CHOICES:
 *   At a node:
 *     1. Place a camera → state2
 *     2. Do not place a camera → state1 or state0 depending on coverage
 *
 * RECURRENCE RELATION:
 *   state0(node) = left[1] + right[1]
 *       // node not covered → children must cover themselves
 *
 *   state2(node) = 1 + min(left[0], left[1], left[2]) + min(right[0], right[1], right[2])
 *       // place camera here → covers self + children
 *
 *   state1(node) = min(
 *       state2(node),
 *       left[2] + min(right[1], right[2]),
 *       right[2] + min(left[1], left[2])
 *   )
 *       // node covered without camera → either child has camera or node itself has camera
 *
 * BASE CASE:
 *   null node:
 *     state0 = 0 → trivially "not covered" but needs no camera
 *     state1 = 0 → trivially "covered"
 *     state2 = INF → impossible to place camera
 *
 * KEY INSIGHTS:
 *   - Post-order traversal ensures children are evaluated before parent.
 *   - The final answer is min(state1_root, state2_root) since root must be covered.
 */
public class BinaryTreeCameras {

    static class Node {
        int val;
        Node left, right;

        Node(int val) { this.val = val; }
    }

    static final int INF = 1_000_000;

    /**
     * Returns {state0, state1, state2} for a node:
     * - state0: node is NOT covered (needs camera from parent)
     * - state1: node is covered but has NO camera
     * - state2: node HAS a camera
     */
    private static int[] dfs(Node node) {
        if (node == null) {
            return new int[]{0, 0, INF}; // base case
        }

        int[] left = dfs(node.left);
        int[] right = dfs(node.right);

        // Node is not covered: children must cover themselves
        int state0 = left[1] + right[1];

        // Node has camera: 1 for itself + min states of children
        int state2 = 1 + Math.min(left[0], Math.min(left[1], left[2]))
                       + Math.min(right[0], Math.min(right[1], right[2]));

        // Node is covered without camera: either child has camera or node has camera
        int state1 = Math.min(
                       state2,  // node has camera
                       Math.min(
                           left[2] + Math.min(right[1], right[2]),
                           right[2] + Math.min(left[1], left[2])
                       ));

        return new int[]{state0, state1, state2};
    }

    public static int minCameraCover(Node root) {
        int[] states = dfs(root);
        // Root must be covered: choose covered or has camera
        return Math.min(states[1], states[2]);
    }

    // Example usage
    public static void main(String[] args) {
        Node root = new Node(0);
        root.left = new Node(0);
        root.right = new Node(0);
        root.left.left = new Node(0);
        root.left.right = new Node(0);

        System.out.println("Minimum cameras needed: " + minCameraCover(root));
        // Expected output: 2
    }
}