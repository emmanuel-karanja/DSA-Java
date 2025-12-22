package Graph;

import java.util.*;

/**
 * Problem Statement:
 * Given n nodes (1 to n) and a list of directed edges times[i] = [u, v, w] representing travel time w from node u to v,
 * and a source node K, compute the time it takes for all nodes to receive the signal starting from K.
 * Return -1 if some node is unreachable.
 * 
 * INTUTION:
 * 
 * First of all no exit condition. We exit once we've gone through all nodes.
 * How to model that state.
 * 
 * 
 * Approach:
 * - Build adjacency list from the input times.
 * - Use Dijkstra's algorithm with a min-heap to track the minimum travel time to each node.
 * - Maintain a distance array to store the shortest time to each node.
 * - Once all nodes are processed, return the maximum time from the source to any node.
 * - If any node is unreachable, return -1.
 */

public class NetworkDelayTime {

    public static int networkDelayTime(int[][] times, int n, int K) {
        // Build adjacency list
        Map<Integer, List<int[]>> graph = new HashMap<>();
        for (int[] t : times) {
            graph.computeIfAbsent(t[0], x -> new ArrayList<>()).add(new int[]{t[1], t[2]});
        }

        // Distance array
        int[] dist = new int[n + 1];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[K] = 0;

        // Min-heap: {node, time}
        PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a[1]));
        pq.offer(new int[]{K, 0});

        while (!pq.isEmpty()) {
            int[] cur = pq.poll();
            int node = cur[0];
            int time = cur[1];

            // Skip if no outgoing edges
            if (!graph.containsKey(node)) continue;

            for (int[] next : graph.get(node)) {
                int neighbor = next[0];
                int w = next[1];
                int newTime=time+w;
                if (newTime < dist[neighbor]) {
                    dist[neighbor] = newTime;
                    pq.offer(new int[]{neighbor, dist[neighbor]});
                }
            }
        }

        // Find the maximum time
        int maxTime = 0;
        for (int i = 1; i <= n; i++) {
            if (dist[i] == Integer.MAX_VALUE) return -1;
            maxTime = Math.max(maxTime, dist[i]);
        }

        return maxTime;
    }

    public static void main(String[] args) {
        int[][] times = {
            {2, 1, 1},
            {2, 3, 1},
            {3, 4, 1}
        };
        int n = 4;
        int K = 2;

        int result = networkDelayTime(times, n, K);
        System.out.println("Network delay time: " + result);  // Output: 2

        // Another test case
        int[][] times2 = {
            {1, 2, 1}
        };
        n = 2;
        K = 1;

        int result2 = networkDelayTime(times2, n, K);
        System.out.println("Network delay time: " + result2);  // Output: 1
    }
}