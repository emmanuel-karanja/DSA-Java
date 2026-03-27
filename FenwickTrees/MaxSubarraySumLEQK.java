package FenwickTrees;

import java.util.*;

/**
 * PROBLEM: Maximum Subarray Sum ≤ K
 * * Given an integer array 'nums' and an integer 'K', find the maximum sum of any 
 * contiguous subarray such that the sum does not exceed K.
 *
 * ALGEBRAIC REASONING:
 * 1. Subarray Sum Definition: 
 * Sum(i, j) = prefix[i] - prefix[j]  (where i is current index, j is a previous index)
 * 
 * 
 * 2. Constraint: 
 * prefix[i] - prefix[j] ≤ K
 * 
 * Rearranging for the "known" current prefix[i]:
 * -prefix[j] ≤ K - prefix[i]
 * prefix[j] ≥ prefix[i] - K
 * 
 * 
 * 3. Optimization Goal:
 * To maximize (prefix[i] - prefix[j]), we need to subtract the SMALLEST possible prefix[j]
 * that still satisfies the constraint prefix[j] ≥ (prefix[i] - K).
 * 
 * 
 * 4. Why Descending Sort + Fenwick Min?
 * 
 * - A standard Fenwick tree queries the "prefix" (indices 1 to target).
 * - By sorting values in DESCENDING order:
 * Rank 1 = Largest Value
 * Rank N = Smallest Value
 * - Therefore, query(map.get(target)) covers all values from Max down to Target.
 * - This effectively searches the range [target, ∞) in the original number space.
 * - We store the MINIMUM value in the Fenwick tree to find the best (smallest) prefix[j].
 *
 * TIME COMPLEXITY: O(N log N) due to sorting and Fenwick operations.
 * SPACE COMPLEXITY: O(N) for the prefix sums and coordinate map.
 */

public class MaxSubarraySumLEQK {

    // Fenwick Tree modified to track Prefix Minimums
    static class FenwickMin {
        int n;
        long[] bit;

        public FenwickMin(int n) {
            this.n = n;
            this.bit = new long[n + 2];
            // Initialize with Max Value because we are looking for the MINIMUM
            Arrays.fill(bit, Long.MAX_VALUE);
        }

        // Update the tree with a value at a specific ranked index
        public void update(int i, long val) {
            while (i <= n) {
                bit[i] = Math.min(bit[i], val);
                i += i & -i;
            }
        }

        // Query the minimum value among all ranks from 1 to i
        public long query(int i) {
            long res = Long.MAX_VALUE;
            while (i > 0) {
                res = Math.min(res, bit[i]);
                i -= i & -i;
            }
            return res;
        }
    }

    public int maxSubarraySumNoLargerThanK(int[] nums, int K) {
        int n = nums.length;
        long[] prefix = new long[n + 1];
        for (int i = 0; i < n; i++) {
            prefix[i + 1] = prefix[i] + nums[i];
        }

        // Step 1: Collect all prefix sums and search targets (p - K) for coordinate compression
        Set<Long> set = new HashSet<>();
        for (long p : prefix) {
            set.add(p);
            set.add(p - K);
        }

        // Step 2: SORT DESCENDING
        // This maps larger numbers to smaller indices in the Fenwick Tree
        List<Long> sorted = new ArrayList<>(set);
        Collections.sort(sorted, Collections.reverseOrder());

        Map<Long, Integer> map = new HashMap<>();
        for (int i = 0; i < sorted.size(); i++) {
            map.put(sorted.get(i), i + 1); // 1-based indexing
        }

        FenwickMin ft = new FenwickMin(sorted.size());
        long maxResult = Long.MIN_VALUE;

        // Step 3: Iterate through prefix sums
        for (long p : prefix) {
            // Find target index for (p - K)
            int targetIdx = map.get(p - K);
            
            // Query for the minimum prefix sum seen so far that is >= (p - K)
            // Because of descending sort, query(targetIdx) looks at the "Right Tail"
            long bestPrev = ft.query(targetIdx);

            if (bestPrev != Long.MAX_VALUE) {
                maxResult = Math.max(maxResult, p - bestPrev);
            }

            // Update the Fenwick Tree with the current prefix sum 'p'
            int currIdx=map.get(p);
            // Store the value of P
            ft.update(currIdx, p);
        }

        return (maxResult == Long.MIN_VALUE) ? 0 : (int) maxResult;
    }

    public static void main(String[] args) {
        MaxSubarraySumLEQK solver = new MaxSubarraySumLEQK();
        
        int[] nums = {3, -2, 5, -1};
        int K = 4;

        int result = solver.maxSubarraySumNoLargerThanK(nums, K);

        System.out.println("Input: " + Arrays.toString(nums));
        System.out.println("Constraint K = " + K);
        System.out.println("Maximum subarray sum ≤ K: " + result); 
        // Expected: 4 (from subarray [5, -1] or [3, -2, 3... wait, 5-1 is the winner here])
    }
}