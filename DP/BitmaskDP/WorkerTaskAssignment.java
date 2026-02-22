/**
 * PROBLEM: Minimum Cost Assignment (Worker-Task)
 * * STATEMENT:
 * Given an N x N cost matrix where cost[i][j] is the cost of assigning 
 * worker 'i' to task 'j', find the minimum total cost to assign every 
 * worker to exactly one unique task.
 * 
 * Logic & Design Doc
 * 
 * The Goal: Minimize the total cost to assign N tasks to N workers
 *  such that each worker gets exactly one task.The State: dp[i][mask] is the minimum cost
 *  to have the first i workers complete the tasks represented by the bits in mask.
 * 
 * The Guard:
 *    We only "Take" task j for worker i if:(mask & (1 << j)) == 0: Task j has not been
 *  assigned to any previous worker.
 * 
 *  The Transition:
 *   Take: If we assign task j to worker i,  the cost is costMatrix[i][j] + dp[i-1][mask ^ (1 << j)].
 * 
 * Skip: In this specific N-to-N assignment, a worker cannot "skip" having a task if we want to
 *  reach the goal, but we "skip" through the tasks j to find the best one to "take.
 * 
 * "Base Case: dp[0][0] = 0.
 * 
 * Dimensions: dp[N + 1][1 << N].  
 * * GENERATIVE DP LOGIC:
 * 1. GOAL: Min cost for state dp[N][(1 << N) - 1].
 * 2. STATE: dp[i][mask] = Min cost for first 'i' workers to do 'mask' tasks.
 * 3. TRANSITION (The "Take" Choice):
 * - For the current worker 'i', we try "taking" every task 'j'.
 * - IF task 'j' is in the mask ((mask & (1 << j)) != 0):
 * - dp[i][mask] = min(dp[i][mask], cost[i-1][j] + dp[i-1][mask ^ (1 << j)])
 * 4. SPACE OPTIMIZATION: Since worker 'i' only depends on 'i-1', we can 
 * use a 1D array dp[mask].
 */
import java.util.Arrays;

public class WorkerTaskAssignment {

    public int minCost(int[][] cost) {
        int n = cost.length;
        int totalMasks = 1 << n;
        
        // dp[mask] represents the min cost to complete the tasks in 'mask'
        // using the first 'k' workers, where k = number of set bits in mask.
        int[] dp = new int[totalMasks];
        
        // Goal is Min, so initialize with a large value (Sentinel)
        // Integer.MAX_VALUE / 2 prevents overflow when adding costs
        Arrays.fill(dp, Integer.MAX_VALUE / 2);
        
        // Base Case: 0 workers, 0 tasks, 0 cost
        dp[0] = 0;

        // Generative approach: Iterate through all masks
        // The number of set bits in 'mask' implicitly tells us which worker we are on.
        for (int mask = 0; mask < totalMasks; mask++) {
            // Count how many workers have been assigned tasks so far
            int workerIdx = Integer.bitCount(mask);
            
            // If workerIdx < n, we are looking to assign the NEXT worker
            if (workerIdx < n) {
                for (int taskJ = 0; taskJ < n; taskJ++) {
                    // "Take" Choice: If taskJ is NOT yet in the mask
                    if ((mask & (1 << taskJ)) == 0) {
                        int nextMask = mask | (1 << taskJ);
                        
                        // Combine: min(current_best_for_next_state, current_cost + previous_state)
                        dp[nextMask] = Math.min(dp[nextMask], dp[mask] + cost[workerIdx][taskJ]);
                    }
                }
            }
        }

        return dp[totalMasks - 1];
    }

    public static void main(String[] args) {
        WorkerTaskAssignment solver = new WorkerTaskAssignment();

        // Example: 3 workers, 3 tasks
        int[][] cost = {
            {9, 2, 7}, // Worker 0 costs for Task 0, 1, 2
            {6, 4, 3}, // Worker 1
            {5, 8, 1}  // Worker 2
        };

        int result = solver.minCost(cost);
        // Expected: Worker 0 -> Task 1 (2), Worker 1 -> Task 0 (6), Worker 2 -> Task 2 (1) = 9
        // Or: Worker 0 -> Task 1 (2), Worker 1 -> Task 2 (3), Worker 2 -> Task 0 (5) = 10
        System.out.println("Minimum Assignment Cost: " + result);
    }
}