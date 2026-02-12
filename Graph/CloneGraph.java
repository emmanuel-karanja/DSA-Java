package Graph;

import java.util.*;

// Graph node definition
class Node {
    public int val;
    public List<Node> neighbors;

    public Node(int val) {
        this.val = val;
        this.neighbors = new ArrayList<>();
    }
}

public class CloneGraph {

    public static Node cloneGraph(Node node) {
        if (node == null) return null;

        // 1. Map original node -> cloned node
        Map<Node, Node> map = new HashMap<>();

        // BFS queue
        Queue<Node> queue = new ArrayDeque<>();
        queue.offer(node);

        // 2. Clone the start node
        Node cloneStart = new Node(node.val);
        map.put(node, cloneStart);

        while (!queue.isEmpty()) {
            Node current = queue.poll();

            for (Node neighbor : current.neighbors) {
                if (!map.containsKey(neighbor)) {
                    // Clone neighbor and put in map
                    Node neighborClone = new Node(neighbor.val);
                    map.put(neighbor, neighborClone);
                    queue.offer(neighbor);
                }
                //3.  Link clone of current to clone of neighbor.  Find the node clone and find the neighbour clone and link them
                map.get(current).neighbors.add(map.get(neighbor));
            }
        }

        return cloneStart;
    }

    // =======================
    // TEST
    // =======================
    public static void main(String[] args) {

        // Build sample graph: 1 - 2 - 3, 1 - 3
        Node node1 = new Node(1);
        Node node2 = new Node(2);
        Node node3 = new Node(3);

        node1.neighbors.add(node2);
        node1.neighbors.add(node3);

        node2.neighbors.add(node1);
        node2.neighbors.add(node3);

        node3.neighbors.add(node1);
        node3.neighbors.add(node2);

        Node clone = cloneGraph(node1);

        // Quick sanity check
        System.out.println("Original Node 1: " + node1 + " with neighbors " + node1.neighbors.size());
        System.out.println("Cloned Node 1: " + clone + " with neighbors " + clone.neighbors.size());
    }
}
