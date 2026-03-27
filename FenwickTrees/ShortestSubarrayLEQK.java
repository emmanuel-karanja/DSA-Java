package FenwickTrees;

import java.util.*;

/**
 * PROBLEM: Shortest Subarray Sum ≤ K
 * * ALGEBRAIC REASONING:
 * 1. Constraint: ps[i] - ps[j] ≤ K  =>  ps[j] ≥ ps[i] - K
 * 2. Goal: Minimize (i - j). We need the LARGEST index 'j' such that
 * ps[j] is in the range [ps[i] - K, +∞).
 * 3. Strategy: Descending Sort + Fenwick Maximum (storing indices).
 * - Descending sort makes Rank 1 = Max Value.
 * - query(map.get(p-K)) returns the max index 'j' found in values >= p-K.
 */
public class ShortestSubarrayLEQK {

    static class FenwickMax {
        int n;
        int[] bit;

        public FenwickMax(int n) {
            this.n = n;
            this.bit = new int[n + 2];
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

    public int shortestSubarrayAtMostK(int[] nums, int K) {
        int n = nums.length;
        long[] prefix = new long[n + 1];
        for (int i = 0; i < n; i++) prefix[i + 1] = prefix[i] + nums[i];

        Set<Long> set = new HashSet<>();
        for (long p : prefix) {
            set.add(p);
            set.add(p - K);
        }

        List<Long> sorted = new ArrayList<>(set);
        // DESCENDING SORT for "Greater than or equal to" logic
        Collections.sort(sorted, Collections.reverseOrder());

        Map<Long, Integer> map = new HashMap<>();
        for (int i = 0; i < sorted.size(); i++) {
            map.put(sorted.get(i), i + 1);
        }

        FenwickMax ft = new FenwickMax(sorted.size());
        int minLength = Integer.MAX_VALUE;

        for (int i = 0; i <= n; i++) {
            long p = prefix[i];
            
            // Search for the largest index j such that ps[j] >= p - K
            int targetIdx = map.get(p - K);
            int bestJ = ft.query(targetIdx);

            if (bestJ != -1) {
                minLength = Math.min(minLength, i - bestJ);
            }

            // Update Fenwick tree with index 'i' at the rank of prefix sum 'p'
            ft.update(map.get(p), i);
        }

        return (minLength == Integer.MAX_VALUE) ? -1 : minLength;
    }

    public static void main(String[] args) {
        ShortestSubarrayLEQK solver = new ShortestSubarrayLEQK();
        // Array with some large numbers and small numbers
        int[] nums = {10, -5, 10, 1};
        int K = 6; 
        System.out.println("Shortest subarray length ≤ " + K + ": " + solver.shortestSubarrayAtMostK(nums, K));
        // Subarrays <= 6: [-5] (len 1), [1] (len 1), [-5, 10, 1] (len 3)...
        // Shortest should be 1.
    }
}