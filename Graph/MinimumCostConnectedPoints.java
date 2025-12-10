package Graph;

import java.util.PriorityQueue;


/**Given n points ona 2D plane, where point i=[xi,yi]. The cost connecting two points is the 
 * Manhattan Distance dist[i,j]=|xi-xj| + |yi-yj|, find the minimum cost of connecting all points so that
 * there is one simple path between any two points.
 * 
 * INTUTION:
 * 
 * This implies BFS. At each point, we go to all the surrounding points.
 * It implies MinHeap.
 * Check edges between u->v etc
 * 
 * Prioritize low cost edges.
 * 
 * KEY: This the classic Minimum Spanning Tree problem.
 * 
 * 
 */
/**
 * Minimum cost to connect all points (MST using Manhattan distance).
 */
class QueueNode {
    public int node;
    public int cost;

    public QueueNode(int node, int cost) {
        this.node = node;
        this.cost = cost;
    }
}

public class MinimumCostConnectedPoints {

    private static int manhattanDist(int[][] points, int i, int j) {
        return Math.abs(points[i][0] - points[j][0]) + Math.abs(points[i][1] - points[j][1]);
    }

    public static int getMinCost(int[][] points) {
        if (points == null || points.length == 0) return 0;

        final int n = points.length;
        boolean[] visited = new boolean[n];
        PriorityQueue<QueueNode> minHeap = new PriorityQueue<>((a, b) -> Integer.compare(a.cost, b.cost));

        minHeap.add(new QueueNode(0, 0)); // start from point 0
        int totalCost = 0;
        int count = 0;

        while (count < n) { // visit all points, we stop after we've visited all the points.
            QueueNode node = minHeap.poll();
            int u = node.node;

            if (visited[u]) continue;

            //visit
            visited[u] = true;
            totalCost += node.cost;
            count++;

            // add all edges from u to unvisited points, basically this point to all points hence this format
            //basically you'll say at the current point, we check all unvisited points.
            for (int v = 0; v < n; v++) {
                if (!visited[v]) {
                    minHeap.add(new QueueNode(v, manhattanDist(points, u, v)));
                }
            }
        }

        return totalCost;
    }

    // Example driver
    public static void main(String[] args) {
        int[][] points = {{0,0},{2,2},{3,10},{5,2},{7,0}};
        System.out.println(getMinCost(points)); // prints the min cost
    }
}
