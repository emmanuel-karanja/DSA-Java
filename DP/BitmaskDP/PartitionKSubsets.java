package BitmaskDP;

/**
 * PROBLEM: Partition to K Equal Sum Subsets
 * * STATEMENT:
 * Given an array 'nums' and an integer 'k', determine if the array can be 
 * partitioned into 'k' subsets such that every subset has the same sum.
 * * GENERATIVE DP LOGIC:
 * 1. GOAL: Reach state (1 << n) - 1 (all elements used) where all buckets are filled perfectly.
 * 2. STATE: dp[mask] represents the accumulated sum of the *current* bucket being filled
 * after using the elements indicated by the bitmask.
 * 3. TRANSITIONS (Skip/Take):
 * - For a given mask, iterate through all elements 'j'.
 * - IF (mask & (1 << j)) == 0 (Element j is available/Skip case for previous items):
 * - IF dp[mask] + nums[j] <= targetSum (Take case is valid):
 * - nextMask = mask | (1 << j)
 * - dp[nextMask] = (dp[mask] + nums[j]) % targetSum
 * 4. BASE CASE: dp[0] = 0 (No elements used, current bucket sum is 0).
 * 5. SELECTION RULE: Use an 'int[]' initialized to -1. Only progress from states != -1.
 * * CONSTRAINTS: N <= 16 (The signal for 2^N state space).
 */
import java.util.Arrays;

public class PartitionKSubsets {

    public boolean canPartitionKSubsets(int[] nums, int k) {
        int n = nums.length;
        int totalSum = 0;
        for (int num : nums) totalSum += num;

        // Preliminary check: If total sum doesn't divide evenly by k, it's impossible.
        if (totalSum % k != 0) return false;
        int target = totalSum / k;

        // dp[mask] = the sum of the current subset being filled.
        // We use 2^n states to represent every possible combination of chosen elements.
        int[] dp = new int[1 << n];
        Arrays.fill(dp, -1);
        
        // Base state: 0 elements used, 0 current sum.
        dp[0] = 0;

        // Iterating through all masks (topological order is natural for integers 0 to 2^n-1)
        for (int mask = 0; mask < (1 << n); mask++) {
            // If this configuration of elements cannot form valid partial subsets, skip it.
            if (dp[mask] == -1) continue;

            for (int j = 0; j < n; j++) {
                // Take Case: If j-th bit is not set AND it fits in the current target bucket.
                if ((mask & (1 << j)) == 0 && dp[mask] + nums[j] <= target) {
                    int nextMask = mask | (1 << j);
                    
                    // The modulo resets the current bucket sum to 0 whenever we hit 'target'.
                    // This allows the next 'Take' to start filling a fresh bucket.
                    dp[nextMask] = (dp[mask] + nums[j]) % target;
                }
            }
            
            // Optimization: If we found a way to reach the final state, exit early.
            if (dp[(1 << n) - 1] == 0) return true;
        }

        return dp[(1 << n) - 1] == 0;
    }

    public static void main(String[] args) {
        PartitionKSubsets solver = new PartitionKSubsets();

        // Test Case: Valid partition
        int[] nums1 = {4, 3, 2, 3, 5, 2, 1};
        int k1 = 4;
        System.out.println("Input: [4,3,2,3,5,2,1], k=4 | Result: " + solver.canPartitionKSubsets(nums1, k1));

        // Test Case: Invalid partition
        int[] nums2 = {1, 2, 3, 4};
        int k2 = 3;
        System.out.println("Input: [1,2,3,4], k=3 | Result: " + solver.canPartitionKSubsets(nums2, k2));
    }
}