package FenwickTrees;

import java.util.*;

/**
 * PROBLEM: Count of Subarrays with Sum in Range [lower, upper]
 *
 * Given an integer array nums and two integers lower and upper,
 * return the number of subarrays whose sum lies in the range [lower, upper] (inclusive).
 *
 * A subarray is defined as a contiguous non-empty sequence of elements.
 *
 * ------------------------------------------------------------
 * EXAMPLE:
 * nums = [-2, 5, -1], lower = -2, upper = 2
 *
 * Valid subarrays:
 * [-2] → -2
 * [-2, 5, -1] → 2
 * [-1] → -1
 *
 * Output: 3
 * ------------------------------------------------------------
 *
 * APPROACH: Prefix Sum + Fenwick Tree (Binary Indexed Tree) + Coordinate Compression
 *
 * 1. Prefix Sum Transformation:
 *    Let prefix[i] = sum of nums[0..i-1]
 *
 *    Then subarray sum:
 *      sum(i, j) = prefix[j] - prefix[i]
 *
 *    We want:
 *      lower ≤ prefix[j] - prefix[i] ≤ upper
 *
 *    Rearranged:
 *      prefix[i] ∈ [prefix[j] - upper, prefix[j] - lower]
 *
 * 2. Key Idea:
 *    For each prefix[j], count how many previous prefix[i]
 *    fall into the valid range.
 *
 * 3. Why Fenwick Tree?
 *    We need efficient:
 *      - Insert prefix values
 *      - Count how many fall within a range
 *
 *    Fenwick Tree supports:
 *      update → O(log n)
 *      query  → O(log n)
 *
 * 4. Why Coordinate Compression?
 *    Prefix sums can be large (negative/positive),
 *    so we compress them into a smaller index range.
 *
 *    We include:
 *      - prefix[i]
 *      - prefix[i] - lower
 *      - prefix[i] - upper
 *
 * 5. Complexity:
 *      Time:  O(n log n)
 *      Space: O(n)
 *
 * ------------------------------------------------------------
 */

class FenwickTree {
    int n;
    int[] bit;

    public FenwickTree(int n) {
        this.n = n;
        this.bit = new int[n + 1];
    }

    public void update(int i, int delta) {
        i++; // convert to 1-based index
        while (i <= n) {
            bit[i] += delta;
            i += i & -i;
        }
    }

    public int query(int i) {
        i++; // convert to 1-based index
        int sum = 0;
        while (i > 0) {
            sum += bit[i];
            i -= i & -i;
        }
        return sum;
    }
}

public class CountSubArraySumBetweenUpperAndLower {

    public int getSubArrayCount(int[] nums, int lower, int upper) {
        int n = nums.length;

        long[] prefix = new long[n + 1];
        for (int i = 0; i < n; i++) {
            prefix[i + 1] = prefix[i] + nums[i];
        }

        // Coordinate compression
        Set<Long> set = new HashSet<>();
        for (long p : prefix) {
            set.add(p);
            set.add(p - lower);
            set.add(p - upper);
        }

        List<Long> sorted = new ArrayList<>(set);
        Collections.sort(sorted);

        Map<Long, Integer> map = new HashMap<>();
        int idx=1;
        for (int i = 0; i < sorted.size(); i++) {
            map.put(sorted.get(i), idx++);
        }

        FenwickTree ft = new FenwickTree(idx);

        int count = 0;

        for (long p : prefix) {
            int left = map.get(p - upper);  // Since it's the smaller range.
            int right = map.get(p - lower);

            count += ft.query(right) -  ft.query(left - 1) ;

            ft.update(map.get(p), 1);
        }

        return count;
    }

    // ---------------- DRIVER ----------------
    public static void main(String[] args) {
        CountSubArraySumBetweenUpperAndLower solver =
                new CountSubArraySumBetweenUpperAndLower();

        int[] nums = {-2, 5, -1};
        int lower = -2;
        int upper = 2;

        int result = solver.getSubArrayCount(nums, lower, upper);

        System.out.println("Input: " + Arrays.toString(nums));
        System.out.println("Range: [" + lower + ", " + upper + "]");
        System.out.println("Number of valid subarrays: " + result);
    }
}