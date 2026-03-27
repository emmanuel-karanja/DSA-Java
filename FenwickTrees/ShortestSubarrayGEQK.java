package FenwickTrees;

import java.util.*;

/**
 * PROBLEM: Shortest Subarray Sum ≥ K
 * * ALGEBRAIC REASONING:
 * 1. Constraint: ps[i] - ps[j] ≥ K  =>  ps[j] ≤ ps[i] - K
 * 2. Goal: Minimize (i - j). This requires finding the LARGEST index 'j' 
 * such that ps[j] is in the range [-inf, ps[i] - K].
 * 3. Strategy: Ascending Sort + Fenwick Maximum (storing indices).
 */
public class ShortestSubarrayGEQK {

    static class FenwickMax {
        int n;
        int[] bit;

        public FenwickMax(int n) {
            this.n = n;
            this.bit = new int[n + 2];
            // Initialize with -1 because index 0 is valid, and we want the MAX index
            Arrays.fill(bit, -1);
        }

        public void update(int i, int indexVal) {
            while (i <= n) {
                bit[i] = Math.max(bit[i], indexVal);
                i += i & -i;
            }
        }

        public int query(int i) {
            int res = -1;
            while (i > 0) {
                res = Math.max(res, bit[i]);
                i -= i & -i;
            }
            return res;
        }
    }

    public int shortestSubarrayAtLeastK(int[] nums, int K) {
        int n = nums.length;
        long[] prefix = new long[n + 1];
        for (int i = 0; i < n; i++) prefix[i + 1] = prefix[i] + nums[i];

        Set<Long> set = new HashSet<>();
        for (long p : prefix) {
            set.add(p);
            set.add(p - K);
        }

        List<Long> sorted = new ArrayList<>(set);
        Collections.sort(sorted);

        Map<Long, Integer> map = new HashMap<>();
        for (int i = 0; i < sorted.size(); i++) {
            map.put(sorted.get(i), i + 1);
        }

        FenwickMax ft = new FenwickMax(sorted.size());
        int minLength = Integer.MAX_VALUE;

        for (int i = 0; i <= n; i++) {
            long p = prefix[i];
            
            // Search for the largest index j such that ps[j] <= p - K
            int targetIdx = map.get(p - K);
            int bestJ = ft.query(targetIdx);

            if (bestJ != -1) {
                minLength = Math.min(minLength, i - bestJ);
            }

            // Update Fenwick tree with the index 'i' at the rank of prefix sum 'p'
            ft.update(map.get(p), i);
        }

        return (minLength == Integer.MAX_VALUE) ? -1 : minLength;
    }

    public static void main(String[] args) {
        ShortestSubarrayGEQK solver = new ShortestSubarrayGEQK();
        int[] nums = {2, -1, 2, 1};
        int K = 3;
        System.out.println("Shortest subarray length ≥ " + K + ": " + solver.shortestSubarrayAtLeastK(nums, K));
        // Subarrays >= 3: [2,-1,2] (len 3), [2,-1,2,1] (len 4), [2,1] (len 2).
        // Result: 2
    }
}