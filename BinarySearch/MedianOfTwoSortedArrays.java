package BinarySearch;
/**Find the median of two sorted arrays in less than O(nlogn) time:

MENTAL MODEL

We do NOT search both arrays.
We binary-search ONLY the shorter array (M).

At any step we choose a cut `mid` in M (0 ≤ mid ≤ m).
This cut forces a corresponding cut in the other array (N):

    partitionN = (m + n + 1) / 2 - mid

So the total number of elements on the left side is always correct.

Think in terms of PARTITIONS, not indices.

Each partition defines:
    leftM | rightM
    leftN | rightN

The goal is to find a partition where:
    max(leftM, leftN) ≤ min(rightM, rightN)

Boundary cases are expressed via sentinels:
    - If a left side is empty → maxLeft = -∞
    - If a right side is empty → minRight = +∞

This naturally handles cases where:
    - mid == 0 or mid == m
    - partitionN == 0 or partitionN == n
    - m == 0 (the shorter array is empty)

Binary search adjustment:
    - If maxLeftM > minRightN → cut too far right in M → move left
    - Else if maxLeftN > minRightM → cut too far left in M → move right

Once the invariant holds, the median is:
    - Odd total length: max(maxLeftM, maxLeftN)
    - Even total length: average of max(left) and min(right)

Key idea:
The algorithm is about ordering across partitions, not merging arrays.
*/

public class MedianOfTwoSortedArrays {

    public static double getMedian(int[] nums1, int[] nums2) {
        // always binary search the smaller array
        if (nums1.length > nums2.length) {
            return getMedian(nums2, nums1);
        }

        int m = nums1.length;
        int n = nums2.length;

        int left = 0;
        int right = m; //note this not m-1 but m

        while (left <= right) {
            int mid = left + (right - left) / 2;
            int partitionN = (m + n + 1) / 2 - mid;

            // Take it for granted that partitonM can move from 0 to m and partitionN from 0 to n
            int maxLeftM = (mid == 0) ? Integer.MIN_VALUE : nums1[mid - 1];  //when arr1.length==0
            int minRightM = (mid == m) ? Integer.MAX_VALUE : nums1[mid];  //where right=m and left==0

            int maxLeftN = (partitionN == 0) ? Integer.MIN_VALUE : nums2[partitionN - 1];
            int minRightN = (partitionN == n) ? Integer.MAX_VALUE : nums2[partitionN];

            // check if we found correct partition
            if (maxLeftM <= minRightN && maxLeftN <= minRightM) {
                if ((m + n) % 2 == 0) { // even total length
                    return (Math.max(maxLeftM, maxLeftN) + Math.min(minRightM, minRightN)) / 2.0;
                } else { // odd total length
                    return Math.max(maxLeftM, maxLeftN);
                }
            } else if (maxLeftM > minRightN) {
                right = mid - 1; // move left
            } else {
                left = mid + 1; // move right
            }
        }

        throw new IllegalArgumentException("Input arrays are not sorted correctly.");
    }

    public static void main(String[] args) {
        int[] nums1 = {1, 3, 8};
        int[] nums2 = {7, 9, 10, 11};

        System.out.println(getMedian(nums1, nums2)); // Output: 8.0
    }
}
