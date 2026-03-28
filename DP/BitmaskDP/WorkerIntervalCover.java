/**
 * PROBLEM STATEMENT:
 * Given 'N' intervals that must be covered and a list of workers, where each worker 
 * can cover a specific subset of those intervals, find the minimum number of workers 
 * required to cover all intervals.
 * * REASONING:
 * 1. This is a variation of the 'Set Cover' problem, which is NP-Hard.
 * 2.  If N (intervals) <= 20, Bitmask DP is the optimal choice.
 * 3. State: dp[mask] represents the minimum workers to cover the intervals set in 'mask'.
 * 4. Transition: For every reachable state 'i', we try adding worker 'j'. 
 * new_mask = i | worker_j_mask.
 * dp[new_mask] = min(dp[new_mask], dp[i] + 1).
 * 5. Complexity: O(2^N * W) where N = intervals, W = workers.
 */
public class WorkerIntervalCover {

    private static final int INF = 1_000_000; // Use a safe large value to avoid overflow

    public static int minWorkersToCover(int numIntervals, int[] workerMasks) {
        // Edge Case: N is too large for Bitmasking (Senior check)
        if (numIntervals > 25) {
            throw new IllegalArgumentException("Interval count too large for Bitmask DP. Consider backtracking or heuristics.");
        }
        
        if (numIntervals == 0) return 0;

        int target = (1 << numIntervals) - 1;
        int[] dp = new int[1 << numIntervals];
        java.util.Arrays.fill(dp, INF);
        dp[0] = 0;

        // Iterate through all possible coverage states
        for (int mask = 0; mask < (1 << numIntervals); mask++) {
            if (dp[mask] == INF) continue; // Skip unreachable states

            for (int workerMask : workerMasks) {
                int nextMask = mask | workerMask;
                
                // Only update if we found a strictly better (fewer workers) path
                if (dp[nextMask] > dp[mask] + 1) {
                    dp[nextMask] = dp[mask] + 1;
                }
            }
            
            // Optimization: If we reached target early, we can't do better for 
            // THIS mask, but we continue because other masks might reach target with fewer steps.
        }

        return dp[target] >= INF ? -1 : dp[target];
    }

    public static void main(String[] args) {
        System.out.println("--- Test Case 1: Standard Cover ---");
        int n1 = 3;
        int[] workers1 = {
            0b001, // Worker A covers Interval 0
            0b110, // Worker B covers Interval 1, 2
            0b101  // Worker C covers Interval 0, 2
        };
        // Expect 2 (A+B or C+B)
        System.out.println("Result: " + minWorkersToCover(n1, workers1));

        System.out.println("\n--- Test Case 2: Overlapping Efficiency ---");
        int n2 = 4;
        int[] workers2 = {
            0b1111, // Super Worker covers all
            0b0001, 
            0b0010
        };
        // Expect 1 (Super Worker is best)
        System.out.println("Result: " + minWorkersToCover(n2, workers2));

        System.out.println("\n--- Test Case 3: Impossible Cover ---");
        int n3 = 3;
        int[] workers3 = {0b001, 0b010}; // No one covers Interval 2
        // Expect -1
        System.out.println("Result: " + minWorkersToCover(n3, workers3));

        System.out.println("\n--- Test Case 4: Zero Intervals ---");
        System.out.println("Result: " + minWorkersToCover(0, new int[]{}));
    }
}