package FenwickTrees;

import java.util.*;

/**
 * PROBLEM: Count Inversions (Left-to-Right Approach)
 * * ALGEBRAIC REASONING:
 * 1. An inversion is i < j and arr[i] > arr[j].
 * 2. When moving Left-to-Right, for the current element arr[j], 
 * any previously seen element arr[i] where arr[i] > arr[j] is an inversion.
 * 3. Total elements seen so far = j.
 * 4. Elements ≤ arr[j] = ft.query(rank).
 * 5. Elements > arr[j] = (Total seen so far) - (Elements ≤ arr[j]).
 */
public class CountInversionsLeftToRight {

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
        if(arr==null) return 0;
        final int n = arr.length;
        if (n == 0) return 0;

        // 1. Coordinate Compression (Handling Duplicates correctly)
        int[] sorted = arr.clone();
        Arrays.sort(sorted);
        
        Map<Integer, Integer> rankMap = new HashMap<>();
        int uniqueRanks = 0;
        for (int i = 0; i < n; i++) {
            // Only assign a rank to unique values to avoid double-counting
            if (i == 0 || sorted[i] != sorted[i - 1]) {
                rankMap.put(sorted[i], uniqueRanks++);
            }
        }

        FenwickTree ft = new FenwickTree(uniqueRanks);
        long totalInversions = 0;

        // 2. Process Left to Right
        for (int j = 0; j < n; j++) {
            int currentRank = rankMap.get(arr[j]);

            // Query: How many elements seen so far have rank <= currentRank?
            int countSmallerOrEqual = ft.query(currentRank);
            
            // Inversions = (Total elements processed) - (Those <= current)
            // Note: 'j' is the count of elements already in the tree
            totalInversions += (j - countSmallerOrEqual);

            // Add current element to the history
            ft.update(currentRank, 1);
        }

        return totalInversions;
    }

    public static void main(String[] args) {
        int[] arr = {2, 4, 1, 3, 5};
        // Trace:
        // j=0, val=2: seen 0, query(rank(2))=0. Inv += 0-0. Update(rank(2))
        // j=1, val=4: seen 1, query(rank(4))=1. Inv += 1-1. Update(rank(4))
        // j=2, val=1: seen 2, query(rank(1))=0. Inv += 2-0 = 2. Update(rank(1))
        // j=3, val=3: seen 3, query(rank(3))=2. Inv += 3-2 = 1. Update(rank(3))
        // j=4, val=5: seen 4, query(rank(5))=4. Inv += 4-4 = 0. Update(rank(5))
        // Total = 3
        System.out.println("Inversions: " + countInversions(arr));
    }
}