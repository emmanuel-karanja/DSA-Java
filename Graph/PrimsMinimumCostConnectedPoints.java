package Graph;

import java.util.Arrays;
import java.util.PriorityQueue;


/**Given n points ona 2D plane, where point i=[xi,yi]. The cost connecting two points is the 
 * Manhattan Distance dist[i,j]=|xi-xj| + |yi-yj|, find the minimum cost of connecting all points so that
 * there is one simple path between any two points.
 * 
 * INTUTION:
 * 
 * This implies BFS. At each point, we go to all the surrounding points.
 * This is actually Minimum Spanning Tree or Prim's Algorithm using MinHeap.
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

class Edge {
    int to;
    int cost;

    Edge(int to, int cost) {
        this.to = to;
        this.cost = cost;
    }
}

public class PrimsMinimumCostConnectedPoints {

    private static int manhattanDist(int[][] points, int i, int j) {
        return Math.abs(points[i][0] - points[j][0]) + Math.abs(points[i][1] - points[j][1]);
    }

     public static int getMinCost(int[][] points) {
        if (points == null || points.length == 0) return 0;

        int n = points.length;

        boolean[] visited = new boolean[n];

        // minDist[v] = minimum cost edge connecting v to current MST
        int[] minDist = new int[n];
        Arrays.fill(minDist, Integer.MAX_VALUE);
        minDist[0] = 0;

        PriorityQueue<Edge> minHeap =
                new PriorityQueue<>((a, b) -> a.cost - b.cost);

        minHeap.add(new Edge(0, 0));

        int totalCost = 0;
        int visitedCount = 0;

        while (visitedCount < n) {
            Edge cur = minHeap.poll();
            int u = cur.to;

            if (visited[u]) continue;

            // Add node to MST
            visited[u] = true;
            totalCost += cur.cost;
            visitedCount++;

            // Relax edges from u
            for (int v = 0; v < n; v++) {
                if (!visited[v]) {
                    int dist = manhattanDist(points, u, v);
                    if (dist < minDist[v]) {
                        minDist[v] = dist;
                        minHeap.add(new Edge(v, dist));
                    }
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
