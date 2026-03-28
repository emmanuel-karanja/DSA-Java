package FenwickTrees;

/**
 * PROBLEM STATEMENT: 
 * Count subarrays with length AT MOST K and sum in [L, U], including negatives.
 * * REASONING:
 * 1. Negatives = No monotonicity (Two-pointers fail).
 * 2. Length <= K = Sliding Window of "Valid Past" (Size K).
 * 3. Range [L, U] = Fenwick Tree + Coordinate Compression.
 * 4. Logic: Find PS[j] such that PS[i] - U <= PS[j] <= PS[i] - L, where i-K <= j < i.
 */
import java.util.*;

public class SubarrayAtMostKNegatives {

    public static long countSubarrays(int[] nums, int K, long L, long U) {
        int n = nums.length;
        long[] ps = new long[n + 1];
        for (int i = 0; i < n; i++) ps[i + 1] = ps[i] + nums[i];

        // 1. Coordinate Compression for all possible PS values and targets
        TreeSet<Long> set = new TreeSet<>();
        for (long x : ps) {
            set.add(x);
            set.add(x - L);
            set.add(x - U);
        }
        
        Map<Long, Integer> rank = new HashMap<>();
        int r = 1;
        for (long x : set) rank.put(x, r++);
        
        FenwickTree ft = new FenwickTree(r);
        long count = 0;

        // 2. Sliding History via Fenwick
        for (int i = 0; i <= n; i++) {
            // Remove the element that is now too far away (j < i - K)
            if (i > K) {
                ft.update(rank.get(ps[i - K - 1]), -1);
            }

            // Query range [PS[i]-U, PS[i]-L]
            int low = rank.get(ps[i] - U);
            int high = rank.get(ps[i] - L);
            count += ft.queryRange(low, high);

            // Add current PS to history
            ft.update(rank.get(ps[i]), 1);
        }

        return count;
    }
    
    // Standard Fenwick Implementation
    static class FenwickTree {
        int[] tree;
        FenwickTree(int n) { tree = new int[n + 1]; }
        void update(int i, int delta) {
            for (; i < tree.length; i += i & -i) tree[i] += delta;
        }
        int query(int i) {
            int sum = 0;
            for (; i > 0; i -= i & -i) sum += tree[i];
            return sum;
        }
        int queryRange(int l, int r) { return query(r) - query(l - 1); }
    }

    public static void main(String[] args) {
        int[] nums = {1, -2, 3, -1, 2};
        System.out.println("Count (Length <= 2, Sum [0, 2]): " + countSubarrays(nums, 2, 0, 2));
    }
}