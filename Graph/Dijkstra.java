package Graph;

import java.util.*;
/**Find the shortest distance between a source and destination node in a graph where the weights are non-negative.
 * 
 * INTUTION:
 * 
 * BFS and a greedy approach where we priortize and finalize the node with the shortest distance so far from the curent
 * node. This is the greedy commitment.
 * 
 * Now, this works where we know this local optima is going to lead to global optima since the weights are non-negative,
 * i.e. distance can never be negative.
 * 
 * i.e.if we are at node d, we know it's the shortest path node since all the nodes in the minHeap have distances >= d
 * and if we take any other alternative path, we will go through those nodes further affirming that the distance will be
 * greater.
 * 
 * When we check if a node is visited or not, the same node will enter the heap with different distances and we ensure
 * we don't process it again since the first time we always get its shortest distace i.e. if we've removed it from the\
 * min heap,we are guaranteed to have gotten its shortest distance since if it repears the distance it will have willalso
 * include additional values from other intermediate nodes.
 * 
 * KEY: The step where we check if the popped node is visited or not is CRUCIAL to the entire algorithm.
 *     Without it we are not guaranteed to arrive at a global optimal.
 * 
 * Only those nodes that pass the relaxation test (i.e. newValue < oldValue) will be explored further.
 */
class GraphNode {
    public int value; // node id
    public Map<GraphNode, Integer> neighbors; // neighbor -> edge weight

    public GraphNode(int value) {
        this.value = value;
        this.neighbors = new HashMap<>();
    }

    // convenience to add an edge
    public void addNeighbor(GraphNode neighbor, int weight) {
        //Weight is assigned to an edge not to the vertex itself.
        neighbors.put(neighbor, weight);
    }
    
}

class QueueNode {
    public GraphNode graphNode;
    public int distance; // distance from start

    public QueueNode(GraphNode node, int distance) {
        this.graphNode = node;
        this.distance = distance;
    }
}

public class Dijkstra {

    public static Map<Integer, Integer> getDistances(GraphNode startNode, Set<GraphNode> allNodes) {
        Map<Integer, Integer> distances = new HashMap<>();
        Set<GraphNode> visited = new HashSet<>();

        // Initialize all distances to infinity
        for (GraphNode node : allNodes) {
            distances.put(node.value, Integer.MAX_VALUE);
        }

        // PriorityQueue orders by current shortest distance
        PriorityQueue<QueueNode> minHeap = new PriorityQueue<>(Comparator.comparingInt(a -> a.distance));
        
        distances.put(startNode.value, 0);
        minHeap.add(new QueueNode(startNode, 0));

        while (!minHeap.isEmpty()) {
            QueueNode curr = minHeap.poll();
            GraphNode node=curr.graphNode;

            if (visited.contains(node)) continue; // already finalized
            //visit, once the shortest node so far is popped from the heap,we never reconsdier it. This is the greedy commitment.
            visited.add(node);

            // Relax edges
            for (Map.Entry<GraphNode, Integer> entry : node.neighbors.entrySet()) {
                GraphNode neighbor = entry.getKey();
                int weight = entry.getValue();

                int newDist = curr.distance + weight;
                if (newDist < distances.get(neighbor.value)) { //You only explore those nodes that pass
                    //the relaxation condition.
                    distances.put(neighbor.value, newDist);
                    minHeap.add(new QueueNode(neighbor, newDist));
                }
            }
        }

        return distances;
    }

    public static void main(String[] args) {
        // Example usage
        GraphNode n0 = new GraphNode(0);
        GraphNode n1 = new GraphNode(1);
        GraphNode n2 = new GraphNode(2);
        GraphNode n3 = new GraphNode(3);

        n0.addNeighbor(n1, 2);
        n0.addNeighbor(n2, 4);
        n1.addNeighbor(n2, 1);
        n1.addNeighbor(n3, 7);
        n2.addNeighbor(n3, 3);

        Set<GraphNode> allNodes = Set.of(n0, n1, n2, n3);

        Map<Integer, Integer> distances = getDistances(n0, allNodes);

        System.out.println("Shortest distances from node 0:");
        for (Map.Entry<Integer, Integer> entry : distances.entrySet()) {
            System.out.println("Node " + entry.getKey() + ": " + entry.getValue());
        }
    }
}
