/**
 * PROBLEM STATEMENT:
 * Find the minimum number of workers to cover N intervals using BFS.
 * * REASONING:
 * 1. State-Space Graph: Each bitmask is a node. An edge exists from mask A to mask B 
 * if a worker can transition the coverage from A to B.
 * 2. Uniform Edge Weight: Every worker added costs '1'. BFS finds the shortest path 
 * in unweighted graphs.
 * 3. Visited Array: Crucial to prevent cycles or redundant processing of the same 
 * coverage state.
 */
import java.util.*;

public class WorkerCoverBFS {

    public static int minWorkersBFS(int numIntervals, int[] workerMasks) {
        if (numIntervals == 0) return 0;
        
        int target = (1 << numIntervals) - 1;
        Queue<Integer> queue = new LinkedList<>();
        boolean[] visited = new boolean[1 << numIntervals];

        // Start with 0 intervals covered
        queue.add(0);
        visited[0] = true;

        int workersHired = 0;

        while (!queue.isEmpty()) {
            int size = queue.size();
            workersHired++; // We are looking at the next layer of "hiring"

            // Process all states reachable with the current number of workers
            for (int i = 0; i < size; i++) {
                int currentMask = queue.poll();

                for (int workerMask : workerMasks) {
                    int nextMask = currentMask | workerMask;

                    // If we reached the goal, return the current level
                    if (nextMask == target) {
                        return workersHired;
                    }

                    // If we haven't seen this specific coverage combination, add to queue
                    if (!visited[nextMask]) {
                        visited[nextMask] = true;
                        queue.add(nextMask);
                    }
                }
            }
            
            // Safety: If we've hired more workers than exist, it's impossible.
            // But the queue will naturally empty if no new states are reachable.
        }

        return -1; // Target mask never reached
    }

    public static void main(String[] args) {
        // Driver code
        int n = 4;
        int[] workers = {
            0b1000, // Worker 1
            0b0110, // Worker 2
            0b0011, // Worker 3
            0b0101  // Worker 4
        };

        int result = minWorkersBFS(n, workers);
        System.out.println("Minimum workers (BFS): " + result); 
        // Expected: 2 (e.g., Worker 1 + Worker 2 covers 1110, wait... 1 + 3 covers 1011, 
        // 1 + 2 + 3 covers 1111. Let's see: 1000 | 0110 | 0011 = 1111.)
    }
}