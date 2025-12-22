package Graph;

import java.util.*;

/**
 * Given n cities connected by directed flights where
 * flights[i] = [u, v, w] represents a flight from city u to city v with cost w.
 *
 * Given:
 *   - number of cities n
 *   - flight list
 *   - source city src
 *   - destination city dst
 *   - integer K representing the maximum number of intermediate stops
 *
 * Return the cheapest cost from src to dst using at most K stops.
 * If no such route exists, return -1.
 *
 * ------------------------------------------------------------
 * PROBLEM CLASS
 * Shortest path with resource constraints
 *
 * State definition:
 *   (city, edgesUsed)
 *
 * Resource:
 *   edgesUsed = number of flights taken so far
 *
 * Key observations:
 *   - A stop is NOT an edge; stops = edgesUsed - 1
 *   - Max allowed edges = K + 1
 *   - edgesUsed must therefore lie in [0 .. K + 1]
 *
 * DP table:
 *   bestCost[city][edgesUsed] = minimum cost to reach city
 *   using exactly edgesUsed edges
 *
 * Algorithm:
 *   Constrained Dijkstra over expanded state space.
 *
 * Invariants:
 *   - Priority queue is ordered by total cost only
 *   - Cities are NOT marked visited
 *   - Same city may be visited multiple times with different edgesUsed
 *   - Only strictly better (relaxed) states are pushed
 *   - The first time dst is polled from the heap, its cost is optimal
 *     among all valid states (edgesUsed <= K + 1)
 */
class GraphNode {
    int city;
    int cost;

    GraphNode(int city, int cost) {
        this.city = city;
        this.cost = cost;
    }
}

class State {
    int city;
    int cost;
    int edgesUsed;

    State(int city, int cost, int edgesUsed) {
        this.city = city;
        this.cost = cost;
        this.edgesUsed = edgesUsed;
    }
}

public class CheapestFlightsConstrainedDijkstra {

    public static int getCheapestFlight(
            int[][] flights,
            int n,
            int src,
            int dst,
            int K) {

        /* ---------------- Build adjacency list ---------------- */
        Map<Integer, List<GraphNode>> graph = new HashMap<>();
        for (int[] f : flights) {
            graph.computeIfAbsent(f[0], x -> new ArrayList<>())
                 .add(new GraphNode(f[1], f[2]));
        }

        /* ---------------- DP table ----------------
         * edgesUsed ∈ [0 .. K+1]  →  K+2 slots
         */
        int[][] bestCost = new int[n][K + 2];
        for (int i = 0; i < n; i++) {
            Arrays.fill(bestCost[i], Integer.MAX_VALUE);
        }

        /* ---------------- Min-heap ordered by cost ---------------- */
        PriorityQueue<State> minHeap =
                new PriorityQueue<>(Comparator.comparingInt(s -> s.cost));

        minHeap.offer(new State(src, 0, 0));
        bestCost[src][0] = 0;

        /* ---------------- Dijkstra over expanded state space ---------------- */
        while (!minHeap.isEmpty()) {
            State cur = minHeap.poll();

            int city = cur.city;
            int cost = cur.cost;
            int edgesUsed = cur.edgesUsed;

            // First time destination is polled => minimal possible cost
            if (city == dst) {
                return cost;
            }

            // Cannot expand further if max edges already used
            if (edgesUsed == K + 1) {
                continue;
            }

            if (!graph.containsKey(city)) {
                continue;
            }

            for (GraphNode next : graph.get(city)) {
                int newCost = cost + next.cost;
                int nextEdgesUsed = edgesUsed + 1;

                if (newCost < bestCost[next.city][nextEdgesUsed]) {
                    bestCost[next.city][nextEdgesUsed] = newCost;
                    minHeap.offer(new State(next.city, newCost, nextEdgesUsed));
                }
            }
        }

        return -1;
    }

    /* ---------------- Example ---------------- */
    public static void main(String[] args) {
        int[][] flights = {
            {0, 1, 100},
            {1, 2, 100},
            {0, 2, 500}
        };

        int n = 3;
        int src = 0, dst = 2, K = 1;

        int cheapest = getCheapestFlight(flights, n, src, dst, K);
        System.out.println("Cheapest flight cost: " + cheapest); // 200
    }
}
