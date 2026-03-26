package FenwickTrees;

import java.util.*;

/**
 * PROBLEM: Maximum Subarray Sum ≤ K
 *
 * Given an integer array nums and an integer K, find the maximum sum of any subarray
 * such that the sum is ≤ K.
 *
 * APPROACH:
 * 1. Compute prefix sums: prefix[i] = sum of nums[0..i-1]
 * 2. For each prefix[j], we want the largest prefix[i] ≤ prefix[j] - lower_bound
 *    (here lower_bound = prefix[j] - K)
 * 3. Use coordinate compression with a HashSet + Fenwick Tree to efficiently
 *    query the max prefix sum ≤ a certain value
 * 4. Update the Fenwick Tree with the current prefix sum
 *
 * Note: We use Fenwick Tree for "max" instead of count.
 */

class FenwickMax {
    int n;
    long[] bit;

    public FenwickMax(int n) {
        this.n = n;
        this.bit = new long[n + 2]; // 1-based indexing
        Arrays.fill(bit, Long.MIN_VALUE);
    }

    // Max update at position i
    public void update(int i, long val) {
        i++; // 1-based
        while (i <= n + 1) {
            bit[i] = Math.max(bit[i], val);
            i += i & -i;
        }
    }

    // Max query for prefix [0..i]
    public long query(int i) {
        i++; // 1-based
        long res = Long.MIN_VALUE;
        while (i > 0) {
            res = Math.max(res, bit[i]);
            i -= i & -i;
        }
        return res;
    }
}

public class MaxSubarraySumLEQK {

    public int maxSubarraySumNoLargerThanK(int[] nums, int K) {
        int n = nums.length;
        long[] prefix = new long[n + 1];
        for (int i = 0; i < n; i++) prefix[i + 1] = prefix[i] + nums[i];

        // Step 1: Collect all relevant values for compression
        Set<Long> set = new HashSet<>();
        for (long p : prefix) {
            set.add(p);
            set.add(p - K);
        }

        // Step 2: Coordinate compression
        List<Long> sorted = new ArrayList<>(set);
        Collections.sort(sorted);
        Map<Long, Integer> map = new HashMap<>();

        int idx=1;
        for (int i = 0; i < sorted.size(); i++) {
            map.put(sorted.get(i), idx++);
        }

        // Step 3: Fenwick Tree for max prefix
        FenwickMax ft = new FenwickMax(idx);

        int maxSum = Integer.MIN_VALUE;

        for (long p : prefix) {
            // Find the largest prefix[i] ≤ p - lower_bound = p - K
            int targetIdx = map.get(p - K);
            long bestPrev = ft.query(targetIdx);
            if (bestPrev != Long.MIN_VALUE) {
                maxSum = Math.max(maxSum, (int) (p - bestPrev));
            }

            // Update Fenwick with current prefix sum
            int currIdx=map.get(p);
            ft.update(currIdx, p);
        }

        return maxSum;
    }

    // ---------------- DRIVER ----------------
    public static void main(String[] args) {
        MaxSubarraySumLEQK solver = new MaxSubarraySumLEQK();

        int[] nums = {3, -2, 5, -1};
        int K = 4;

        int result = solver.maxSubarraySumNoLargerThanK(nums, K);

        System.out.println("Input: " + Arrays.toString(nums));
        System.out.println("Constraint K = " + K);
        System.out.println("Maximum subarray sum ≤ K: " + result);
    }
}