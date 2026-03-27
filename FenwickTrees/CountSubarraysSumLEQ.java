package FenwickTrees;

import java.util.*;

/*
PROBLEM:
Count the number of contiguous subarrays in a given array (can have negatives) 
whose sum is <= S.

---

THINKING PROCESS:

1) Sliding Window Fails:
-----------------------
Sliding window only works for monotonic constraints (e.g., sum ≤ K for positive numbers).
If negatives exist, expanding/shrinking the window doesn't preserve validity.

Example:
nums = [2, -1, 3], S = 3
Window [2,-1] sum=1 < 3 → invalid
Window [2,-1,3] sum=4 ≥ 3 → valid
Cannot incrementally expand/shrink safely.

---

2) Prefix Sum Reduction:
------------------------

WE want ps[i] - ps[j] <=K where i >j

We want ps[j] 
   -ps[j] <=K-ps[i]

   apply negatives to lhs positive(flips the inequality in the process)
    ps[j]>=ps[i]-K

    How many values have I previously inserted into the tree that have a rank >= the rank of (p - S)?
    Hence to ensure only larger values are on the left, we sort in descending order.

---

3) Efficient Counting with Fenwick Tree:
----------------------------------------
- Collect all prefix values and prefix[j+1] - S for coordinate compression
- Use a Fenwick Tree to count how many prefix[i] ≤ target for each j
- Update the tree with the current prefix[j+1]

bit[i] stores the count of prefix sums in the original array whose compressed indices fall within 
[i - (i & -i) + 1, i], exactly like a standard BIT.

We only use the Fenwick Tree so we can query the number of prefix sums ≤ X in O(log n).
Everything else — prefix sums, coordinate compression, totalInserted — is just bookkeeping 
to make this query correct and efficient.

WHAT IS HAPPENING HERE?

1. IndexMap-->a lookup table to index a given ps[i] and ps[i]-K since we can have many occurencs
   contributed to by different i and j ranges within the array.
2. Fenwick tree value at index i is "the count of ocurrences of prefix sum(index by i in the index map) that
   is equal to ps". This is why we ensure we update the value indexed by i in the prefix tree by 1.
   
Time Complexity: O(n log n)
Space Complexity: O(n)


*/

public class CountSubarraysSumLEQ {

    // Fenwick Tree (1-based indexing)
    static class Fenwick {
        int[] bit;
        int n;

        Fenwick(int n) {
            this.n = n;
            bit = new int[n + 2]; // extra space
        }

        void update(int i, int delta) {
            while (i <= n) {
                bit[i] += delta;
                i += i & -i;
            }
        }

        int query(int i) {
            int sum = 0;
            while (i > 0) {
                sum += bit[i];
                i -= i & -i;
            }
            return sum;
        }
    }

    public static int countSubarraysLEQ(int[] nums, int S) {
        int n = nums.length;

        // Step 1: compute prefix sums
        long[] prefix = new long[n + 1];
        for (int i = 0; i < n; i++) {
            prefix[i + 1] = prefix[i] + nums[i];
        }

        // Step 2: collect all values for compression, compresion removes duplications
        Set<Long> set = new HashSet<>();
        for (long v : prefix) {
            set.add(v);
            set.add(v - S);
        }

        List<Long> allValues = new ArrayList<>(set);
        // For sum <=K we do reserve order so that th bigger p-K are
        // on the left side now
        Collections.sort(allValues, Collections.reverseOrder());

        Map<Long, Integer> indexMap = new HashMap<>();
        int idx = 1;
        for (long v : allValues) {
            indexMap.put(v, idx++);
        }

        // Step 3: Fenwick Tree
        Fenwick fenwick = new Fenwick(idx);

        int count = 0;


        for (long p : prefix) {
            long target = p - S;
            int targetIdx = indexMap.get(target);

            //"How many values have I previously inserted into the tree that have a rank <= the rank of (p - S)?
            // Why? because we want ps[i]-ps[j]<=K where i > j and so -ps[j]<=K-ps[i] we apply negative on both sids
            // and flip the sign ps[j]>=ps[i]-K
            //  How many occurrences of it?
            count += fenwick.query(targetIdx);  

            // Update fot the current ps[i]
            int currIdx = indexMap.get(p);
            fenwick.update(currIdx, 1);
        }

        return count;
    }

    public static void main(String[] args) {
        int[] nums1 = {2, -1, 3};
        int S1 = 3;
        System.out.println("Subarrays with sum <= " + S1 + ": " + countSubarraysLEQ(nums1, S1));
        // Expected: 3 → [2,-1,3], [3], [2,-1,3] includes overlapping counts

        int[] nums2 = {1,2,3,4};
        int S2 = 5;
        System.out.println("Subarrays with sum <= " + S2 + ": " + countSubarraysLEQ(nums2, S2));
        // Expected: 6 → [2,3],[3,4],[1,2,3],[2,3,4],[1,2,3,4],[5]
    }
}