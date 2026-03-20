package FenwickTrees;

import java.util.*;

/*
PROBLEM:
Given an array of positive integers nums and an integer k,
return the number of contiguous subarrays whose product is ≥ k.

------------------------------------------------------------

THINKING PROCESS:

1) Why sliding window fails
---------------------------
Sliding window requires monotonicity:
    If a window is valid, all its subwindows must also be valid.

This works for:
    product < k

BUT NOT for:
    product ≥ k

Counterexample:
    nums = [10, 1, 1], k = 10
    [10,1,1] → 10 -->correct
    [1,1]    → 1  -->wrong

So we cannot use contribution counting directly.

------------------------------------------------------------

2) Transform multiplication → addition
--------------------------------------
Take logs:

    log(a * b * c) = log(a) + log(b) + log(c)

So:
    product ≥ k
⇔  log(product) ≥ log(k)

Let:
    prefix[i] = sum of logs up to index i-1

Then:
    product(i..j) ≥ k
⇔  prefix[j+1] - prefix[i] ≥ log(k)

Rearrange:
    prefix[i] ≤ prefix[j+1] - log(k)

------------------------------------------------------------

3) Reduced Problem
------------------
For each j, count how many i < j such that:
    prefix[i] ≤ prefix[j] - log(k)

This becomes:
    "count of prefix sums ≤ target"

------------------------------------------------------------

4) Why Fenwick Tree?
--------------------
We need:
    - fast prefix frequency queries
    - dynamic updates

Steps:
    1. Collect all prefix values
    2. Coordinate compress them (map to indices)
    3. Use Fenwick Tree to:
        - query how many prefix ≤ target
        - insert current prefix

------------------------------------------------------------

TIME COMPLEXITY:
    O(n log n)

SPACE:
    O(n)

------------------------------------------------------------

KEY INSIGHT:
Convert multiplicative constraint → additive prefix problem,
then use Fenwick Tree for efficient counting.

*/

public class SubarrayProductAtLeastK_Fenwick {

    // Fenwick Tree (1-based index)
    static class Fenwick {
        int[] tree;
        int n;

        Fenwick(int n) {
            this.n = n;
            this.tree = new int[n + 1];
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

    public static int countSubarrays(int[] nums, int k) {
        if (k <= 1) {
            int n = nums.length;
            return n * (n + 1) / 2;
        }

        int n = nums.length;
        double logK = Math.log(k);

        // Step 1: prefix logs
        double[] prefix = new double[n + 1];
        for (int i = 0; i < n; i++) {
            prefix[i + 1] = prefix[i] + Math.log(nums[i]);
        }

        // Step 2: collect all values for compression
        List<Double> allValues = new ArrayList<>();
        for (double v : prefix) {   // Store prefix and its complement
            allValues.add(v);
            allValues.add(v - logK);  
        }

        Collections.sort(allValues);

        // Step 3: coordinate compression
        Map<Double, Integer> indexMap = new HashMap<>();
        int idx = 1;
        for (double v : allValues) {
            if (!indexMap.containsKey(v)) {
                indexMap.put(v, idx++);
            }
        }

        // Step 4: Fenwick Tree
        Fenwick fenwick = new Fenwick(idx);

        int count = 0;

        for (int j = 0; j <= n; j++) {
            double target = prefix[j] - logK;

            int targetIdx = indexMap.get(target);

            // count how many prefix[i] ≤ target
            count += fenwick.query(targetIdx);

            // insert current prefix[j]
            int currIdx = indexMap.get(prefix[j]);
            fenwick.update(currIdx, 1);
        }

        return count;
    }

    public static void main(String[] args) {
        int[] nums = {10, 5, 2, 6};
        int k = 100;

        int result = countSubarrays(nums, k);
        System.out.println("Subarrays with product >= K: " + result);
    }
}