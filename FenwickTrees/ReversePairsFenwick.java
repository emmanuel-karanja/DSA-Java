package FenwickTrees;

import java.util.*;

/**
 * PROBLEM: Reverse Pairs (LeetCode 493)
 * * Given an integer array nums, return the number of reverse pairs in the array.
 * A reverse pair is a pair (i, j) where:
 * 0 <= i < j < nums.length  AND  nums[i] > 2 * nums[j]
 * * --------------------------------------------------------------------
 * REASONING:
 * 1. This is a variation of the Inversion Counting problem. 
 * 2. We process the array from LEFT to RIGHT.
 * 3. For the current element nums[j], we need to count how many 
 * previously seen elements nums[i] satisfy: nums[i] > 2 * nums[j].
 * 4. Formula: (Total elements seen so far) - (Elements <= 2 * nums[j]).
 * 5. Because we are comparing nums[i] against 2 * nums[j], our 
 * Coordinate Compression must include BOTH:
 * - Every nums[i] (to be inserted into the tree)
 * - Every 2 * nums[i] (to be queried against the tree)
 * --------------------------------------------------------------------
 */
public class ReversePairsFenwick {

    static class FenwickTree {
        int[] tree;
        int n;

        FenwickTree(int n) {
            this.n = n;
            this.tree = new int[n + 1];
        }

        void update(int i, int val) {
            i++; // 1-based indexing
            while (i <= n) {
                tree[i] += val;
                i += i & -i;
            }
        }

        int query(int i) {
            i++; // 1-based indexing
            int sum = 0;
            while (i > 0) {
                sum += tree[i];
                i -= i & -i;
            }
            return sum;
        }
    }

    public int reversePairs(int[] nums) {
        if (nums == null || nums.length < 2) return 0;
        int n = nums.length;

        // 1. Coordinate Compression
        // We need to map both val and 2*val to ranks
        Set<Long> allValues = new TreeSet<>();
        for (int x : nums) {
            allValues.add((long) x);
            allValues.add(2L * x);
        }

        Map<Long, Integer> rankMap = new HashMap<>();
        int rank = 0;
        for (long val : allValues) {
            rankMap.put(val, rank++);
        }

        FenwickTree ft = new FenwickTree(rank);
        int count = 0;

        // 2. Process Left to Right
        for (int j = 0; j < n; j++) {
            long val = nums[j];
            // What we want
            long target = 2L * val;

            // How many elements seen so far are > 2 * nums[j]?
            // Total seen (j) - elements <= 2 * nums[j]
            int countLEQ = ft.query(rankMap.get(target));
            count += (j - countLEQ);

            // Add current nums[j] to the Fenwick Tree
            int currIdx=rankMap.get(val);
            ft.update(currIdx, 1);
        }

        return count;
    }

    public static void main(String[] args) {
        ReversePairsFenwick solver = new ReversePairsFenwick();

        int[] nums1 = {1, 3, 2, 3, 1};
        // Pairs: (3, 1) at indices (1,4) and (3,1) at indices (3,4).
        // Note: 3 > 2*1 is true. 2 > 2*1 is false.
        System.out.println("Output (Expected 2): " + solver.reversePairs(nums1));

        int[] nums2 = {2, 4, 3, 5, 1};
        // Pairs: (3, 1), (4, 1), (5, 1). 
        // 2 is not > 2*1.
        System.out.println("Output (Expected 3): " + solver.reversePairs(nums2));
    }
}