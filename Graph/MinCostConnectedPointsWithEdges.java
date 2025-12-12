package Graph;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

/**
 * Problem:
 * ----------
 * Given a list of points in 2D space, where each point is represented as [x, y],
 * we want to connect all the points with edges such that:
 *   1. Every point is reachable from any other point (the graph is connected).
 *   2. The sum of the costs of the edges is minimized.
 * 
 * The cost of connecting two points [xi, yi] and [xj, yj] is the Manhattan distance:
 *   cost = |xi - xj| + |yi - yj|
 * 
 * Task:
 * -----
 * 1. Compute the minimum total cost to connect all points (i.e., compute the MST).
 * 2. Return the list of edges that form this MST.
 * 
 * Approach:
 * ----------
 * We use **Prim's algorithm**:
 *   - Start from an arbitrary point (point 0).
 *   - Use a priority queue (min-heap) to always add the edge with the minimum cost
 *     that connects a visited node to an unvisited node.
 *   - Keep track of visited nodes to avoid cycles.
 *   - Collect the edges chosen into a list.
 * 
 * Time Complexity: O(n^2 log n), because for each node we may add O(n) edges to the heap.
 * Space Complexity: O(n^2) for the heap in the worst case.
 * 
 * Output:
 * -------
 * MSTResult object containing:
 *   - totalCost: total cost of connecting all points.
 *   - edges: list of edges in the MST, each represented as [fromNode, toNode].
 */

class QueueNode {
    public int node;    // Node being added to the MST
    public int parent;  // Node in the MST it connects to
    public int cost;    // Cost of the edge connecting parent -> node

    public QueueNode(int node, int parent, int cost) {
        this.node = node;
        this.parent = parent;
        this.cost = cost;
    }
}

public class MinCostConnectedPointsWithEdges {

    /**
     * Calculate Manhattan distance between points[i] and points[j]
     */
    private static int manhattanDist(int[][] points, int i, int j) {
        return Math.abs(points[i][0] - points[j][0]) + Math.abs(points[i][1] - points[j][1]);
    }

    /**
     * Result object for MST
     */
    public static class MSTResult {
        public int totalCost;        // Total cost of the MST
        public List<int[]> edges;    // Edges in MST, each edge = [from, to]

        public MSTResult(int totalCost, List<int[]> edges) {
            this.totalCost = totalCost;
            this.edges = edges;
        }
    }

    /**
     * Compute the minimum cost to connect all points and return the edges in the MST.
     */
    public static MSTResult getMinCostAndEdges(int[][] points) {
        if (points == null || points.length == 0) 
            return new MSTResult(0, new ArrayList<>());

        final int n = points.length;
        boolean[] visited = new boolean[n];  // Track which nodes are already in the MST

        // Min-heap to pick the edge with smallest cost
        PriorityQueue<QueueNode> minHeap = new PriorityQueue<>((a, b) -> Integer.compare(a.cost, b.cost));
        List<int[]> mstEdges = new ArrayList<>();  // Store edges of MST

        minHeap.add(new QueueNode(0, -1, 0)); // Start from point 0, no parent

        int totalCost = 0;
        int edgeCount = 0;

        while (edgeCount < n) {
            QueueNode node = minHeap.poll();  // Get the edge with minimum cost
            int u = node.node;

            // Skip if node is already included in MST
            if (visited[u]) continue;

            // Add node to MST
            visited[u] = true;
            totalCost += node.cost;   // Add the cost to total
            if (node.parent != -1) {  // Skip the first node, which has no parent
                mstEdges.add(new int[]{node.parent, u});
            }
            edgeCount++;

            // Add all edges from u to unvisited nodes to the heap
            for (int v = 0; v < n; v++) {
                if (!visited[v]) {
                    int cost = manhattanDist(points, u, v);
                    minHeap.add(new QueueNode(v, u, cost));
                }
            }
        }

        return new MSTResult(totalCost, mstEdges);
    }

    // Example driver
    public static void main(String[] args) {
        int[][] points = {{0,0},{2,2},{3,10},{5,2},{7,0}};
        MSTResult result = getMinCostAndEdges(points);

        System.out.println("Total MST cost: " + result.totalCost);
        System.out.println("Edges in MST:");
        for (int[] edge : result.edges) {
            System.out.println(edge[0] + " -> " + edge[1]);
        }
    }
}
