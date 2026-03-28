package FenwickTrees;

import java.util.*;

/**
 * Count Inversions using a Fenwick Tree (Right-to-Left approach)
 *
 * ALGORITHM:
 * 1. Process elements from right to left.
 * 2. For current element arr[i], count the number of elements < arr[i] that have been seen so far.
 * 3. Use coordinate compression to handle duplicates and large values.
 */
public class CountInversions {

    // Fenwick Tree / Binary Indexed Tree
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

    public static long countInversions(int[] arr) {
        if (arr == null || arr.length == 0) return 0;

        final int n = arr.length;

        // 1. Coordinate Compression
        int[] sorted = arr.clone();
        Arrays.sort(sorted);

        Map<Integer, Integer> rankMap = new HashMap<>();
        int rank = 0;
        for (int num : sorted) {
            rankMap.putIfAbsent(num, rank++);
        }

        FenwickTree ft = new FenwickTree(rank);
        long inversions = 0;

        // 2. Process elements from right to left
        for (int i = n - 1; i >= 0; i--) {
            int currentRank = rankMap.get(arr[i]);

            // Query: How many elements < current have already been seen
            int countSmaller = ft.query(currentRank - 1);

            inversions += countSmaller;

            // Update Fenwick Tree with current element
            ft.update(currentRank, 1);
        }

        return inversions;
    }

    public static void main(String[] args) {
        int[] arr = {2, 4, 1, 3, 5};
        System.out.println("Inversions: " + countInversions(arr)); // Output: 3
    }
}