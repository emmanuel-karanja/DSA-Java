import java.util.*;
/** Given a list of nodes in an N-ary tree and a level L, return all nodes present at that level.

That part was straightforward using level-order traversal.

Follow-up (Where it got interesting)

Now assume:
 • Nodes can be added or removed dynamically
 • You must return the count of nodes at any level in O(1) time.
 
 INTUITION:

 1. Do a level order bfs traversal and know that level size is queue.size()
 2. For the followup, you need to use precomputation and recursion.


 WHY I LOVE THIS PROBLEM:

 It jumps you out of the often memorized solutions. And into a different mode of thinking.
 
 
 */


class Node {
    int val;
    int level; // store the node's level
    List<Node> children;

    Node(int val, int level) {
        this.val = val;
        this.level = level;
        this.children = new ArrayList<>();
    }
}

class NaryTree {
    Node root;
    Map<Integer, Integer> levelCount;

    public NaryTree(int rootVal) {
        this.root = new Node(rootVal, 0);
        this.levelCount = new HashMap<>();
        levelCount.put(0, 1); // root is level 0
    }

    // Add a child to a parent node
    public Node addChild(Node parent, int childVal) {
        int childLevel = parent.level + 1;
        Node child = new Node(childVal, childLevel);
        parent.children.add(child);

        // Update level count
        levelCount.put(childLevel, levelCount.getOrDefault(childLevel, 0) + 1);
        return child;
    }

    // Remove a node and all its descendants
    public void removeNode(Node node) {
        // Decrement count for this node and all descendants
        removeHelper(node);
        // Also remove this node from its parent's children
        // (Assuming we have reference to parent, or we can traverse to remove)
        // For simplicity, let's assume caller removes from parent's list if needed
    }

    private void removeHelper(Node node) {
        // Decrement count for this node
        levelCount.put(node.level, levelCount.get(node.level) - 1);

        // Recursively remove children
        for (Node child : node.children) {
            removeHelper(child);
        }
    }

    // O(1) query: count of nodes at a given level
    public int getCountAtLevel(int level) {
        return levelCount.getOrDefault(level, 0);
    }

     public static void main(String[] args) {
        NaryTree tree = new NaryTree(1); // root = 1

        Node node2 = tree.addChild(tree.root, 2);
        Node node3 = tree.addChild(tree.root, 3);
        Node node4 = tree.addChild(tree.root, 4);

        tree.addChild(node2, 5);
        tree.addChild(node2, 6);
        tree.addChild(node4, 7);

        System.out.println(tree.getCountAtLevel(0)); // 1
        System.out.println(tree.getCountAtLevel(1)); // 3
        System.out.println(tree.getCountAtLevel(2)); // 3

        tree.removeNode(node2);
        System.out.println(tree.getCountAtLevel(2)); // 1 (only node 7 remains)
    }
}
