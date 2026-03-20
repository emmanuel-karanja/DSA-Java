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
 * 
 * WHY THE GREEDY COMMITMENT:
 * 
 * The min-heap always gives you the node with the smallest current known distance from the source.
   Let’s call this distance d.

    All other nodes in the heap have distances ≥ d.
    So when we pop a node u with distance d:
    There is no way to reach u via any other path that is shorter than d.

Why? Any alternative path must go through other nodes in the heap or nodes already visited.
All nodes in the heap have distance ≥ d
→ any path through them will only increase the distance, never decrease it.

Role of the visited set:
A node can appear in the heap multiple times with different distances.
The first time it is popped, that distance is the shortest possible.
Later occurrences are ignored by visited.contains(node)
Without this check:

You could process a node multiple times
And potentially violate the assumption that we finalized its shortest distance
 */

class Node {
    int target, weight;
    Node(int target, int weight) {
        this.target = target;
        this.weight = weight;
    }
}

public class Dijkstra {
    public int[] findShortestPaths(int n, List<List<Node>> adj, int source) {
        int[] dist = new int[n];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[source] = 0;

        // Min-Heap based on distance: int[]{node_id, distance}
        PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a[1]));
        pq.offer(new int[]{source, 0});

        while (!pq.isEmpty()) {
            int[] current = pq.poll();
            int u = current[0];
            int d = current[1];

            // Optimization: If we found a better path already, skip this stale entry
            if (d > dist[u]) continue;

            for (Node neighbor : adj.get(u)) {
                int v = neighbor.target;
                int weight = neighbor.weight;

                // Relaxation Step
                if (dist[u] + weight < dist[v]) {
                    dist[v] = dist[u] + weight;
                    pq.offer(new int[]{v, dist[v]});
                }
            }
        }
        return dist;
    }
}