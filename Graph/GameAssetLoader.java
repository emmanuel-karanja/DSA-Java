package Graph;

/**
 * PROBLEM: "Game Asset Loader"
 * * STATEMENT:
 * A game update consists of N assets (0 to N-1). Some assets have 
 * prerequisites. Given a list of dependencies [A, B] where A must be 
 * loaded before B, determine a valid loading order. 
 * If a circular dependency exists (e.g., A needs B and B needs A), 
 * return an empty list.
 *
 * INTUITION:
 * - This is a Directed Acyclic Graph (DAG) problem. 
 * - The pattern is Topological Sort (Kahn's Algorithm).
 * - Step 1: Track the "In-Degree" (number of prerequisites) for each node.
 * - Step 2: Add all nodes with 0 in-degree (no prerequisites) to a Queue.
 * - Step 3: Process the queue. When you "load" an asset, decrement the 
 * in-degree of its neighbors. 
 * - Step 4: If any neighbor's in-degree hits 0, add it to the queue.
 * - If the number of processed nodes != Total nodes, there's a cycle.
 */

import java.util.*;

public class GameAssetLoader {
    public List<Integer> findLoadOrder(int numAssets, int[][] dependencies) {
        List<Integer> order = new ArrayList<>();
        Map<Integer, List<Integer>> adj = new HashMap<>();
        int[] inDegree = new int[numAssets];

        // Build the Graph
        for (int[] dep : dependencies) {
            int pre = dep[0];
            int post = dep[1];
            adj.computeIfAbsent(pre, k -> new ArrayList<>()).add(post);
            inDegree[post]++;
        }

        // Queue for assets with no prerequisites
        Queue<Integer> queue = new LinkedList<>();
        for (int i = 0; i < numAssets; i++) {
            if (inDegree[i] == 0) queue.add(i);
        }

        

        while (!queue.isEmpty()) {
            int curr = queue.poll();
            order.add(curr);

            if (adj.containsKey(curr)) {
                for (int neighbor : adj.get(curr)) {
                    inDegree[neighbor]--;
                    if (inDegree[neighbor] == 0) {
                        queue.add(neighbor);
                    }
                }
            }
        }

        // Cycle Detection
        return (order.size() == numAssets) ? order : new ArrayList<>();
    }
}
