package FenwickTrees;

import java.util.*;

/**
 * PROBLEM: Maximum Subarray Sum ≥ K
 * * ALGEBRAIC REASONING:
 * 1. Constraint: ps[i] - ps[j] ≥ K  =>  ps[j] ≤ ps[i] - K
 * 
 * 2. Goal: Maximize (ps[i] - ps[j]). This requires the SMALLEST ps[j] 
 * that is less than or equal to (ps[i] - K).
 * 3. Strategy: Ascending Sort + Fenwick Minimum.
 * - query(map.get(p-K)) gives the min value in range [-inf, p-K].
 */
public class MaxSubarraySumGEQK {

    static class FenwickMin {
        int n;
        long[] bit;

        public FenwickMin(int n) {
            this.n = n;
            this.bit = new long[n + 2];
            Arrays.fill(bit, Long.MAX_VALUE); // Looking for MIN
        }

        public void update(int i, long val) {
            while (i <= n) {
                bit[i] = Math.min(bit[i], val);
                i += i & -i;
            }
        }

        public long query(int i) {
            long res = Long.MAX_VALUE;
            while (i > 0) {
                res = Math.min(res, bit[i]);
                i -= i & -i;
            }
            return res;
        }
    }

    public int maxSubarraySumAtLeastK(int[] nums, int K) {
        int n = nums.length;
        long[] prefix = new long[n + 1];
        for (int i = 0; i < n; i++) prefix[i + 1] = prefix[i] + nums[i];

        Set<Long> set = new HashSet<>();
        for (long p : prefix) {
            set.add(p);
            set.add(p - K);
        }

        // ASCENDING SORT: Rank 1 is the smallest value
        List<Long> sorted = new ArrayList<>(set);
        Collections.sort(sorted);

        Map<Long, Integer> map = new HashMap<>();
        for (int i = 0; i < sorted.size(); i++) {
            map.put(sorted.get(i), i + 1);
        }

        FenwickMin ft = new FenwickMin(sorted.size());
        long maxResult = Long.MIN_VALUE;

        for (long p : prefix) {
            // We need ps[j] <= p - K
            int targetIdx = map.get(p - K);
            long bestPrev = ft.query(targetIdx);

            if (bestPrev != Long.MAX_VALUE) {
                maxResult = Math.max(maxResult, p - bestPrev);
            }

            // Update with current prefix sum
            ft.update(map.get(p), p);
        }

        return (maxResult == Long.MIN_VALUE) ? -1 : (int) maxResult;
    }

    public static void main(String[] args) {
        MaxSubarraySumGEQK solver = new MaxSubarraySumGEQK();
        int[] nums = {2, -1, 5, 1};
        int K = 4;
        System.out.println("Maximum subarray sum ≥ " + K + ": " + solver.maxSubarraySumAtLeastK(nums, K));
        // Possible: [5] (5), [5,1] (6), [2,-1,5] (6), [2,-1,5,1] (7).
        // Result: 7
    }
}