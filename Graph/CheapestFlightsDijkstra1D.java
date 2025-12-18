package Graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

/**Given n cities connected by flights flight[i]=[u,v,w]  means there is a flight connecting city u to city v
 * with a cost w. Given the number of cities n, the list of flights, a source city src, and a destination city
 * dst, and an in integer K that represents maximum number of stops, return the cheapest flights between src and dst,
 * with at most K stops. If there is no such route, return -1.
 * 
 * INTUTION.
 * 
 * This sounds like a graph problem, and the it's about directed weighted graph. I am feeling a minHeap somewhere
 * in there.  BFS with a minHeap and a bestCost to reach acity.
 * 
 * Unlike Dijkstra we don't keep track of visited since we want to be able to re=add a processed node later on
 * provided we keep the stops and the cost down.
 * 
 * We get the oldValue from the statelist. i.e.
 * 
 * 
 * 
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
    int stops;
    State(int city, int cost, int stops) {
        this.city = city;
        this.cost = cost;
        this.stops = stops;
    }
}

public class CheapestFlightsDijkstra1D {

    public static int getCheapestFlight(int[][] flights, int n, int src, int dst, int K) {

        // Build graph
        Map<Integer, List<GraphNode>> graph = new HashMap<>();
        for (int[] f : flights) {
            graph.computeIfAbsent(f[0], x -> new ArrayList<>())
                 .add(new GraphNode(f[1], f[2]));
        }

        // best[city][stops] = minimum cost to reach city using exactly stops edges
        int[][] bestCost = new int[n][K + 2];
        for (int i = 0; i < n; i++) {
            Arrays.fill(bestCost[i], Integer.MAX_VALUE);
        }

        PriorityQueue<State> minHeap =
            new PriorityQueue<>(Comparator.comparingInt(a -> a.cost));

        minHeap.offer(new State(src, 0, 0));
        bestCost[src][0] = 0;

        while (!minHeap.isEmpty()) {
            State cur = minHeap.poll();

            int city = cur.city;
            int cost = cur.cost;
            int stops = cur.stops;

            // If destination reached within allowed stops, this is optimal
            if (city == dst) return cost;

            if (stops == K + 1) continue;

            if (!graph.containsKey(city)) continue;

            for (GraphNode next : graph.get(city)) {
                int newCost = cost + next.cost;

                if (newCost < bestCost[next.city][stops + 1]) {
                    bestCost[next.city][stops + 1] = newCost;  //since it's the same city but with 1 extra stop(this one)
                    minHeap.offer(new State(next.city, newCost, stops + 1));
                }
            }
        }

        return -1;
    }

    public static void main(String[] args) {
        int[][] flights = {
            {0, 1, 100},
            {1, 2, 100},
            {0, 2, 500}
        };
        int n = 3;
        int src = 0, dst = 2, K = 1;

        int cheapest = getCheapestFlight(flights, n, src, dst, K);
        System.out.println("Cheapest flight cost: " + cheapest);  // Output: 200
    }
}
