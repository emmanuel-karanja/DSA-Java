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
    int level;
    Node parent;
    List<Node> children;

    Node(int val, int level, Node parent) {
        this.val = val;
        this.level = level;
        this.parent = parent;
        this.children = new ArrayList<>();
    }
}

class NaryTree {
    Node root;
    Map<Integer, Integer> levelCount;

    public NaryTree(int rootVal) {
        this.root = new Node(rootVal, 0, null);
        this.levelCount = new HashMap<>();
        levelCount.put(0, 1); // root is level 0
    }

    // Add a child to a parent node
    public Node addChild(Node parent, int childVal) {
        int childLevel = parent.level + 1;
        Node child = new Node(childVal, childLevel, parent);
        parent.children.add(child);

        // Update level count
        levelCount.put(childLevel, levelCount.getOrDefault(childLevel, 0) + 1);
        return child;
    }

    // Remove a node and all its descendants
    public void removeNode(Node node) {
        // Remove node from parent's children list
        if (node.parent != null) {
            node.parent.children.remove(node);
        }

        // Recursively remove node and descendants from level counts
        removeHelper(node);
    }

    private void removeHelper(Node node) {
        // Decrement count for this node's level
        levelCount.put(node.level, levelCount.get(node.level) - 1);

        // Recursively remove children
        for (Node child : node.children) {
            removeHelper(child);
        }

        // Clear children list to free references
        node.children.clear();
    }

    // O(1) query: count of nodes at a given level
    public int getCountAtLevel(int level) {
        return levelCount.getOrDefault(level, 0);
    }

    // Simple BFS to get all nodes at a given level (optional)
    public List<Node> getNodesAtLevel(int targetLevel) {
        List<Node> result = new ArrayList<>();
        Queue<Node> queue = new LinkedList<>();
        queue.offer(root);

        while (!queue.isEmpty()) {
            Node current = queue.poll();
            if (current.level == targetLevel) {
                result.add(current);
            }
            for (Node child : current.children) {
                queue.offer(child);
            }
        }

        return result;
    }

    // Demo
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
        System.out.println(tree.getCountAtLevel(1)); // 2 (node3, node4 remain)
        System.out.println(tree.getCountAtLevel(2)); // 1 (only node7 remains)
    }
}
