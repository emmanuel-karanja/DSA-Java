package Arrays;

import java.util.*;

/*
PROBLEM:
Given an array of positive integers nums and an integer k,
return the number of contiguous subarrays whose product is ≥ k.

---

THINKING PROCESS:

1) Why NOT sliding window directly?
-----------------------------------
Sliding window works when the condition is monotonic:
    If a window is valid, all its subwindows are also valid.

This holds for:
    sum ≤ K
    product < K

BUT NOT for:
    product ≥ K

Counterexample:
    nums = [10, 1, 1], k = 10

    [10,1,1] → product = 10  -->correct
    [1,1]    → product = 1    -->wrong

So we cannot use the standard "expand + count (right - left + 1)" trick.

---

2) Complement Trick (simpler solution)
--------------------------------------
We could compute:
    total subarrays - subarrays with product < k

This works because "product < k" IS monotonic → sliding window applies.

---

3) Log Transform (general & interview-strong approach)
------------------------------------------------------
We transform multiplication → addition using logarithms:

    log(a * b * c) = log(a) + log(b) + log(c)

So:
    product ≥ k
becomes:
    log(product) ≥ log(k)

Let:
    prefix[i] = log(nums[0]) + ... + log(nums[i-1])

Then for subarray (i, j):
    product(i..j) ≥ k
⇔  prefix[j+1] - prefix[i] ≥ log(k)

Rearrange:
    prefix[i] ≤ prefix[j+1] - log(k)

---

4) Reduced Problem
-------------------
For each j, count how many i < j such that:
    prefix[i] ≤ prefix[j] - log(k)

This is a classic:
    "count of prefix sums ≤ target"

We solve it using:
    - sorted list + binary search (O(n log n))
    - or Fenwick Tree (advanced)

---

TIME COMPLEXITY:
    O(n log n)

SPACE COMPLEXITY:
    O(n)

---

KEY INSIGHT:
Multiplicative constraints → take logs → reduce to prefix sum inequalities.

*/

public class SubarrayProductAtLeastK_Log {

    public static int countSubarrays(int[] nums, int k) {
        if (k <= 1) {
            int n = nums.length;
            return n * (n + 1) / 2;
        }

        int n = nums.length;
        double logK = Math.log(k);

        // Build prefix log sums
        double[] prefix = new double[n + 1];
        for (int i = 0; i < n; i++) {
            prefix[i + 1] = prefix[i] + Math.log(nums[i]);
        }

        List<Double> sortedPrefix = new ArrayList<>();
        sortedPrefix.add(0.0);

        int count = 0;

        for (int j = 1; j <= n; j++) {
            double target = prefix[j] - logK;

            int validCount = upperBound(sortedPrefix, target);
            count += validCount;

            insert(sortedPrefix, prefix[j]);
        }

        return count;
    }

    // Returns first index where value > target
    private static int upperBound(List<Double> list, double target) {
        int left = 0, right = list.size();
        while (left < right) {
            int mid = left + (right - left) / 2;
            if (list.get(mid) <= target) left = mid + 1;
            else right = mid;
        }
        return left;
    }

    // Insert while maintaining sorted order
    private static void insert(List<Double> list, double val) {
        int idx = Collections.binarySearch(list, val);
        if (idx < 0) idx = -idx - 1;
        list.add(idx, val);
    }

    public static void main(String[] args) {
        int[] nums = {10, 5, 2, 6};
        int k = 100;

        int result = countSubarrays(nums, k);
        System.out.println("Subarrays with product >= K: " + result);
    }
}