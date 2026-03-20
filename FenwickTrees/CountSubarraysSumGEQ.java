package FenwickTrees;

import java.util.*;

/*
PROBLEM:
Count the number of contiguous subarrays in a given array (can have negatives) 
whose sum is ≥ S.

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
Let prefix[i] = sum of nums[0..i-1]
Subarray sum from i to j = prefix[j+1] - prefix[i]
We want:
    prefix[j+1] - prefix[i] ≥ S
⇔  prefix[i] ≤ prefix[j+1] - S

---

3) Efficient Counting with Fenwick Tree:
----------------------------------------
- Collect all prefix values and prefix[j+1] - S for coordinate compression
- Use a Fenwick Tree to count how many prefix[i] ≤ target for each j
- Update the tree with the current prefix[j+1]

Time Complexity: O(n log n)
Space Complexity: O(n)
*/

public class CountSubarraysSumGEQ {

    // Fenwick Tree (1-based indexing)
    static class Fenwick {
        int[] tree;
        int n;

        Fenwick(int n) {
            this.n = n;
            tree = new int[n + 2]; // extra space
        }

        void update(int i, int delta) {
            while (i <= n) {
                tree[i] += delta;
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

    public static int countSubarraysGEQ(int[] nums, int S) {
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
        Collections.sort(allValues);

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
            count += fenwick.query(targetIdx);  // initially set to zero

            int currIdx = indexMap.get(p);
            fenwick.update(currIdx, 1);
        }

        return count;
    }

    public static void main(String[] args) {
        int[] nums1 = {2, -1, 3};
        int S1 = 3;
        System.out.println("Subarrays with sum ≥ " + S1 + ": " + countSubarraysGEQ(nums1, S1));
        // Expected: 3 → [2,-1,3], [3], [2,-1,3] includes overlapping counts

        int[] nums2 = {1,2,3,4};
        int S2 = 5;
        System.out.println("Subarrays with sum ≥ " + S2 + ": " + countSubarraysGEQ(nums2, S2));
        // Expected: 6 → [2,3],[3,4],[1,2,3],[2,3,4],[1,2,3,4],[5]
    }
}