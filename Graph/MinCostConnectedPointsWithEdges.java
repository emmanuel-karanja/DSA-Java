package Graph;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

/**
 * Minimum cost to connect all points (MST using Manhattan distance), 
 * now also tracks the edges in the MST.
 */
class QueueNode {
    public int node;    // Node being added to MST
    public int parent;  // Node in MST it connects to
    public int cost;    // Edge cost

    public QueueNode(int node, int parent, int cost) {
        this.node = node;
        this.parent = parent;
        this.cost = cost;
    }
}

public class MinCostConnectedPointsWithEdges{

    private static int manhattanDist(int[][] points, int i, int j) {
        return Math.abs(points[i][0] - points[j][0]) + Math.abs(points[i][1] - points[j][1]);
    }

    public static class MSTResult {
        public int totalCost;
        public List<int[]> edges; // each edge is [from, to]

        public MSTResult(int totalCost, List<int[]> edges) {
            this.totalCost = totalCost;
            this.edges = edges;
        }
    }

    public static MSTResult getMinCostAndEdges(int[][] points) {
        if (points == null || points.length == 0) return new MSTResult(0, new ArrayList<>());

        final int n = points.length;
        boolean[] visited = new boolean[n];
        PriorityQueue<QueueNode> minHeap = new PriorityQueue<>((a, b) -> Integer.compare(a.cost, b.cost));
        List<int[]> mstEdges = new ArrayList<>();

        minHeap.add(new QueueNode(0, -1, 0)); // start from point 0, no parent

        int totalCost = 0;
        int count = 0;

        while (count < n) {
            QueueNode node = minHeap.poll();
            int u = node.node;

            if (visited[u]) continue;

            visited[u] = true;
            totalCost += node.cost;
            if (node.parent != -1) {
                mstEdges.add(new int[]{node.parent, u});
            }
            count++;

            // Add edges from u to all unvisited points
            for (int v = 0; v < n; v++) {
                if (!visited[v]) {
                    minHeap.add(new QueueNode(v, u, manhattanDist(points, u, v)));
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
