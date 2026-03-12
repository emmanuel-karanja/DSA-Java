package FenwickTrees;

import java.util.*;

/**
 * Problem: Count the number of inversions in an array.
 *
 * Definition:
 * An inversion is a pair of indices (i, j) such that:
 *
 *      i < j  and  arr[i] > arr[j]
 *
 * Example:
 *      arr = [2,4,1,3,5]
 *
 * Inversions:
 *      (2,1)
 *      (4,1)
 *      (4,3)
 *
 * Total = 3
 *
 *
 * --------------------------------------------------------------------
 * REASONING
 * --------------------------------------------------------------------
 *
 * For each element arr[i], we want to know:
 *
 *      "How many elements smaller than arr[i] appear to its right?"
 *
 * Because each such element forms an inversion with arr[i].
 *
 * Example:
 *
 *      [2,4,1,3,5]
 *
 * For element 4:
 *
 *      smaller elements to the right = {1,3}
 *
 * That contributes 2 inversions:
 *
 *      (4,1)
 *      (4,3)
 *
 *
 * --------------------------------------------------------------------
 * KEY IDEA
 * --------------------------------------------------------------------
 *
 * If we process the array from RIGHT → LEFT:
 *
 *      arr[n-1] → arr[0]
 *
 * then all elements we have already processed are exactly the
 * elements that lie to the RIGHT of the current element.
 *
 * So we maintain a data structure that can quickly answer:
 *
 *      "How many numbers smaller than X have we already seen?"
 *
 *
 * --------------------------------------------------------------------
 * DATA STRUCTURE
 * --------------------------------------------------------------------
 *
 * We use a Fenwick Tree (Binary Indexed Tree) to maintain counts
 * of elements we have seen so far.
 *
 * The Fenwick Tree supports two operations in O(log n):
 *
 *      update(i)  -> add an element at position i
 *      query(i)   -> count of elements in range [1..i]
 *
 *
 * --------------------------------------------------------------------
 * ALGORITHM
 * --------------------------------------------------------------------
 *
 * 1. Coordinate compression:
 *    Fenwick Trees operate on indices, not arbitrary values.
 *    So we map the array values into ranks from 1..n.
 *
 * 2. Traverse the array from right to left.
 *
 * 3. For each element arr[i]:
 *
 *      rank = compressed rank of arr[i]
 *
 *      query(rank-1)
 *
 *      gives the number of elements smaller than arr[i]
 *      that we have already seen (i.e. to its right).
 *
 * 4. Add that value to the inversion count.
 *
 * 5. Insert the current element into the Fenwick Tree.
 *
 *
 * --------------------------------------------------------------------
 * TIME COMPLEXITY
 * --------------------------------------------------------------------
 *
 * Coordinate compression: O(n log n)
 * Fenwick operations:     O(log n) per element
 *
 * Total:
 *
 *      O(n log n)
 *
 *
 * --------------------------------------------------------------------
 * SPACE COMPLEXITY
 * --------------------------------------------------------------------
 *
 *      O(n)
 *
 */

public class CountInversionsFenwick {

    static class FenwickTree {
        int[] tree;
        int n;

        FenwickTree(int n) {
            this.n = n;
            tree = new int[n + 1];
        }

        void update(int i, int val) {
            while (i <= n) {
                tree[i] += val;
                i += i & -i;
            }
        }

        int query(int i) {
            int sum = 0;
            while (i > 0) {
                sum += tree[i];
                i -= i & -i;
            }
            return sum;
        }
    }

    public static long countInversions(int[] arr) {

        int n = arr.length;

        // Coordinate compression
        int[] sorted = arr.clone();
        Arrays.sort(sorted);

        Map<Integer,Integer> rank = new HashMap<>();

        for(int i=0;i<n;i++)
            rank.put(sorted[i], i+1);

        FenwickTree ft = new FenwickTree(n);

        long inversions = 0;

        // Traverse right → left
        for(int i=n-1;i>=0;i--){

            int r = rank.get(arr[i]);

            // count smaller elements already seen
            inversions += ft.query(r-1);

            // insert current element
            ft.update(r,1);
        }

        return inversions;
    }

    public static void main(String[] args) {

        int[] arr = {2,4,1,3,5};

        System.out.println("Inversions: " + countInversions(arr));
    }
}