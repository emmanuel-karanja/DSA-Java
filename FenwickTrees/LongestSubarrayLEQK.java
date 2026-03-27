package FenwickTrees;

import java.util.*;

/**
 * PROBLEM: Longest Subarray Sum ≤ K
 * * ALGEBRAIC REASONING:
 * 1. Inequality: ps[i] - ps[j] ≤ K  =>  ps[j] ≥ ps[i] - K
 * 2. Goal: Maximize (i - j). We need the EARLIEST index 'j' (minimum j).
 * 3. Strategy: Descending Sort + Fenwick Minimum (storing indices).
 * 
 * 
 */
public class LongestSubarrayLEQK {

    static class FenwickMin {
        int n;
        int[] bit;

        public FenwickMin(int n) {
            this.n = n;
            this.bit = new int[n + 2];
            // Initialize with a large value because we want the MINIMUM index
            Arrays.fill(bit, Integer.MAX_VALUE);
        }

        public void update(int i, int indexVal) {
            while (i <= n) {
                bit[i] = Math.min(bit[i], indexVal);
                i += i & -i;
            }
        }

        public int query(int i) {
            int res = Integer.MAX_VALUE;
            while (i > 0) {
                // we shift from sum to min or max
                res = Math.min(res, bit[i]);
                i -= i & -i;
            }
            return res;
        }
    }

    public int longestSubarrayAtMostK(int[] nums, int K) {
        int n = nums.length;
        long[] prefix = new long[n + 1];
        for (int i = 0; i < n; i++) prefix[i + 1] = prefix[i] + nums[i];

        // Step 1: Coordinate Compression
        Set<Long> set = new HashSet<>();
        for (long p : prefix) {
            set.add(p);
            set.add(p - K);
        }

        List<Long> sorted = new ArrayList<>(set);
        // DESCENDING sort for "Greater than or equal to" logic, don't use it for sum >=K
        Collections.sort(sorted, Collections.reverseOrder());

        Map<Long, Integer> map = new HashMap<>();
        for (int i = 0; i < sorted.size(); i++) {
            map.put(sorted.get(i), i + 1);
        }

        FenwickMin ft = new FenwickMin(sorted.size());
        int maxLength = -1;

        for (int i = 0; i <= n; i++) {
            long p = prefix[i];
            
            // Search for the earliest index j such that ps[j] >= p - K
            int targetIdx = map.get(p - K);
            int earliestJ = ft.query(targetIdx);

            if (earliestJ != Integer.MAX_VALUE) {
                maxLength = Math.max(maxLength, i - earliestJ);
            }

            // Update Fenwick tree with index 'i' at the rank of prefix sum 'p'
            ft.update(map.get(p), i);
        }

        return maxLength;
    }

    public static void main(String[] args) {
        LongestSubarrayLEQK solver = new LongestSubarrayLEQK();
        
        // Example: Subarrays with sum <= 2
        int[] nums = {1, 2, -3, 1};
        int K = 2;
        
        int result = solver.longestSubarrayAtMostK(nums, K);
        System.out.println("Longest subarray length ≤ " + K + ": " + result);
        // Subarrays <= 2: [1] (len 1), [-3] (len 1), [2,-3,1] (len 3), [1,2,-3,1] (len 4, sum 1)
        // Expected: 4
    }
}