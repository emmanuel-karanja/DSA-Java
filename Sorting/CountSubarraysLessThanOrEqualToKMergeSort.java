package Sorting;

/**
 * PROBLEM:
 * Given an integer array `nums` (can contain negative numbers) and an integer `k`, 
 * count the number of contiguous subarrays whose sum is less than or equal to `k`.
 *
 * REASONING:
 * 1. Prefix Sums: A subarray sum from index i to j is prefixSum[j+1] - prefixSum[i].
 * Goal: Count pairs (i, j) such that i < j and prefixSum[j] - prefixSum[i] <= k.
 *
 * 2. Why Sliding Window/Deque fails: With negative numbers, prefixSum is not monotonic.
 * Shrinking the window might increase the sum, breaking the O(n) invariant.
 *
 * 3. Divide & Conquer (Merge Sort): 
 * - Split the prefixSum array into two halves.
 * - Recursively count valid pairs within the Left half and the Right half.
 * - Count "Cross-Pairs": For each element in the Left sorted half, find how many 
 * elements in the Right sorted half satisfy: prefixSum[right] - prefixSum[left] <= k.
 * - Because both halves are sorted during merge, we can use two pointers to 
 * find this count in O(n) for each level of the recursion.
 *
 * 4. Complexity: O(n log n) time and O(n) space.
 */
public class CountSubarraysLessThanOrEqualToKMergeSort {

    public static int countSubarrays(int[] nums, int k) {
        int n = nums.length;
        long[] prefixSum = new long[n + 1];
        for (int i = 0; i < n; i++) {
            prefixSum[i + 1] = prefixSum[i] + nums[i];
        }

        return mergeSortAndCount(prefixSum, 0, n, k);
    }

    private static int mergeSortAndCount(long[] sum, int start, int end, int k) {
        if (start >= end) return 0;

        int mid = start + (end - start) / 2;
        
        int count = mergeSortAndCount(sum, start, mid, k) + 
                    mergeSortAndCount(sum, mid + 1, end, k);

        // Count cross-pairs: sum[j] - sum[i] <= k where i is in left, j in right
        int j = mid + 1;
        for (int i = start; i <= mid; i++) {
            while (j <= end && sum[j] - sum[i] <= k) {
                j++;
            }
            count += (j - (mid + 1));
        }

        // Standard merge step to maintain sorted order for the next level up
        merge(sum, start, mid, end);
        return count;
    }

    private static void merge(long[] sum, int start, int mid, int end) {
        long[] temp = new long[end - start + 1];
        int i = start, j = mid + 1, k = 0;

        while (i <= mid && j <= end) {
            if (sum[i] <= sum[j]) temp[k++] = sum[i++];
            else temp[k++] = sum[j++];
        }
        while (i <= mid) temp[k++] = sum[i++];
        while (j <= end) temp[k++] = sum[j++];

        System.arraycopy(temp, 0, sum, start, temp.length);
    }

    public static void main(String[] args) {
        // Case 1: Contains negative numbers
        int[] nums1 = {1, -1, 2};
        int k1 = 2;
        // Subarrays: [1], [1,-1], [-1], [-1,2], [2], [1,-1,2] -> all sums <= 2
        System.out.println("Result 1: " + countSubarrays(nums1, k1)); // Expected: 6

        // Case 2: Standard positive
        int[] nums2 = {2, 1, 3};
        int k2 = 3;
        // Subarrays: [2], [1], [3], [2,1]
        System.out.println("Result 2: " + countSubarrays(nums2, k2)); // Expected: 4

        // Case 3: Tricky negative case that breaks deque
        int[] nums3 = {10, -5, 10};
        int k3 = 10;
        // Subarrays: [10], [-5], [10], [10,-5], [-5,10]
        // [10,-5,10] is 15 (too big)
        System.out.println("Result 3: " + countSubarrays(nums3, k3)); // Expected: 5
    }
}