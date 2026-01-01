package FenwickTrees;
/**
 * FENWICK TREE (BINARY INDEXED TREE)
 * 
 * WHAT IT IS:
 * A Fenwick Tree (or Binary Indexed Tree) is a data structure that allows efficient
 * prefix sum queries and single-element updates on an array. 
 * Both operations are O(log n) time.
 * 
 * WHAT PROBLEM IT SOLVES:
 * - Dynamic prefix sum or cumulative frequency queries.
 * - Counting inversions.
 * - Range sum queries with point updates.
 * 
 * WHY 1-INDEXED:
 * - Fenwick Tree uses binary representations of indices to navigate ranges.
 * - `i & -i` is used to isolate the lowest set bit of an index.
 * - 1-indexing ensures the formulas for update and query work uniformly and avoids
 *   accidental errors at index 0.
 * 
 * MEANING OF i & -i:
 * - Isolates the lowest set bit (rightmost 1) of integer i.
 * - Example: i = 12 (1100b), -i = -12 (0100b for & operation), i & -i = 4 (0100b)
 * - In Fenwick Trees:
 *      - update: i += i & -i  (jump to next responsible node)
 *      - query: i -= i & -i  (move to parent node)
 * - Intuition: Node at index i covers a range of length (i & -i) ending at i.
 * 
 * WHY IT IS EFFICIENT:
 * - Each update or query only touches O(log n) nodes because binary indexing
 *   lets us jump through ranges in powers of two.
 * - Example:
 *      Array: [1, 2, 3, 4, 5, 6, 7, 8]
 *      Query prefix sum up to index 8:
 *          Instead of summing all 8 elements, we jump from 8 -> 8-8&-8=0,
 *          summing only log2(8)=3 nodes.
 * - Naive alternative:
 *      - Prefix sum stored in plain array: query [0..i] is O(i)
 *      - Updating an element requires recomputing all prefix sums after it → O(n)
 * - Other alternatives:
 *      - Segment Tree: can also do point updates and range queries in O(log n),
 *        but more code and higher constant factors.
 *      - Simple cumulative sum array: fast queries O(1), but updates O(n)
 * 
 * SUMMARY:
 * - Purpose: Efficient prefix sums + point updates
 * - Operations: query sum, update element
 * - Complexity: O(log n)
 * - Indexing: 1-based
 * - Core trick: i & -i determines range covered by a node
 * 
 * 
 *WHERE IT'S APPLIED

 1. Range sum queries with updates

Example: You have an array of numbers and need to:
  - Query the sum of elements from index 0..i or [l..r]
  - Update a single element frequently
- - Fenwick Tree lets you do both in O(log n), whereas a naive cumulative sum array only allows O(1) queries but O(n) updates.

2. Counting inversions in an array 
An inversion is a pair (i, j) where i < j and arr[i] > arr[j].

You can sweep through the array from right to left, using a Fenwick Tree to count how many smaller 
elements have already been seen, efficiently.

 3. Dynamic frequency tables
      - Suppose you have a list of events or scores and want to answer queries like:
        “How many numbers ≤ x have appeared so far?”
      - Fenwick Trees can maintain cumulative frequency efficiently.

4. Online queries
    e.g. Any situation where you cannot precompute everything because updates arrive dynamically.
Examples:
 - Real-time stock price sums
 - Running leaderboard updates
- Prefix counts in streaming data
 */

public class FenwickTree {
    private int[] tree;
    private int n;

    public FenwickTree(int size) {
        this.n = size;
        this.tree = new int[size + 1]; // 1-indexed
    }

    // Update: add 'val' to index i
    public void update(int i, int val) {
        i++; // shift to 1-based indexing
        while (i <= n) {
            tree[i] += val;
            i += i & -i; // move to parent
        }
    }

    // Query: sum of [0..i]
    public int query(int i) {
        i++; // shift to 1-based indexing
        int sum = 0;
        while (i > 0) {
            sum += tree[i];
            i -= i & -i; // move to parent
        }
        return sum;
    }

    // Query sum of [l..r]
    public int queryRange(int l, int r) {
        return query(r) - query(l - 1);
    }

    public static void main(String[] args) {
        int[] nums = {1, 7, 3, 0, 7, 8, 3, 2, 6, 2};
        FenwickTree ft = new FenwickTree(nums.length);

        // Build tree
        for (int i = 0; i < nums.length; i++) {
            ft.update(i, nums[i]);
        }

        System.out.println("Sum [0..4]: " + ft.query(4)); // 1+7+3+0+7 = 18
        System.out.println("Sum [3..8]: " + ft.queryRange(3, 8)); // 0+7+8+3+2+6 = 26

        // Update index 3 (0->5)
        ft.update(3, 5);
        System.out.println("Sum [3..8] after update: " + ft.queryRange(3, 8)); // 31
    }
}
