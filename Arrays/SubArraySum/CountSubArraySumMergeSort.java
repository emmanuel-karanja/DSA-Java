package Arrays.SubArraySum;

import java.util.*;

/**
 * PROBLEM: Count of Subarrays with Sum in Range [lower, upper]
 *
 * APPROACH: Prefix Sum + Merge Sort (Divide & Conquer)
 *
 * ------------------------------------------------------------
 * IDEA:
 *
 * Let prefix[i] = sum of nums[0..i-1]
 *
 * Then:
 *   subarray sum(i, j) = prefix[j] - prefix[i]
 *
 * We want:
 *   lower ≤ prefix[j] - prefix[i] ≤ upper
 *
 * Rearranged:
 *   prefix[i] ∈ [prefix[j] - upper, prefix[j] - lower]
 *
 * ------------------------------------------------------------
 *
 * STRATEGY:
 *
 * 1. Compute prefix sums
 * 2. Use merge sort to:
 *    - Sort prefix array
 *    - Count valid range pairs during merge step
 *
 * 3. For each right-side prefix[j], count how many prefix[i]
 *    from left side fall into valid range.
 *
 * ------------------------------------------------------------
 *
 * COMPLEXITY:
 *   Time:  O(n log n)
 *   Space: O(n)
 *
 * ------------------------------------------------------------
 */

public class CountSubArraySumMergeSort {

    public int countRangeSum(int[] nums, int lower, int upper) {
        int n = nums.length;

        long[] prefix = new long[n + 1];
        for (int i = 0; i < n; i++) {
            prefix[i + 1] = prefix[i] + nums[i];
        }

        return mergeSort(prefix, 0, n + 1, lower, upper);
    }

    private int mergeSort(long[] prefix, int left, int right, int lower, int upper) {
        if (right - left <= 1) return 0;

        int mid = (left + right) / 2;

        int count = mergeSort(prefix, left, mid, lower, upper)
                  + mergeSort(prefix, mid, right, lower, upper);

        int j = mid, k = mid;

        // Count valid ranges
        for (int i = left; i < mid; i++) {

            // Move k → first index where diff >= lower
            while (k < right && prefix[k] - prefix[i] < lower) k++;

            // Move j → first index where diff > upper
            while (j < right && prefix[j] - prefix[i] <= upper) j++;

            count += (j - k);
        }

        // Merge step (standard)
        long[] temp = new long[right - left];
        int p = left, q = mid, idx = 0;

        while (p < mid && q < right) {
            if (prefix[p] <= prefix[q]) {
                temp[idx++] = prefix[p++];
            } else {
                temp[idx++] = prefix[q++];
            }
        }

        while (p < mid) temp[idx++] = prefix[p++];
        while (q < right) temp[idx++] = prefix[q++];

        // Copy back
        System.arraycopy(temp, 0, prefix, left, temp.length);

        return count;
    }

    // ---------------- DRIVER ----------------
    public static void main(String[] args) {
        CountSubArraySumMergeSort solver = new CountSubArraySumMergeSort();

        int[] nums = {-2, 5, -1};
        int lower = -2;
        int upper = 2;

        int result = solver.countRangeSum(nums, lower, upper);

        System.out.println("Input: " + Arrays.toString(nums));
        System.out.println("Range: [" + lower + ", " + upper + "]");
        System.out.println("Number of valid subarrays: " + result);
    }
}