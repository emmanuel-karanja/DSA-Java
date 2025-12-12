package BinarySearch;
/**Find the median of two sorted arrays in less than O(nlogn) time:
 * 
 * INTUITION
 * 
 * Merging and finding the median is O(m+n)
 * 
 * Partition two array such that:
 * 
 * Arr1 = left1|right1
 * Arr2 = left2|right2
 * 
 * where left1,left2, right1 and right2 are the partitions.
 * 
 * 1. len(left1)+len(left2)<=len(right1)+len(right2)
 * 2. Max(left1,left2)<=min(right1,right2)
 * 
 * median=max(max(left1,left2),min(right1,right2))/2 for even length
 * median=max(max(left1,left2)) for odd length;
 * 
 * 
 * KEY: Binary search the smaller array
 * 
 * partition it first with left and right i.e. right=m at first
 * Key is to find maxLeft1, minRight1, maxLeft2, minRight2.
 * 
 * IT'S ALL ABOUT WHERE WE SPLIT THE SMALLER ARRAY. And the split is a binary split i.e. we are simply assigning left and right.
 * 
 * The higher we cut M, the lower we cut N.
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
        int right = m;

        while (left <= right) {
            int partitionM = left + (right - left) / 2;
            int partitionN = (m + n + 1) / 2 - partitionM;

            int maxLeftM = (partitionM == 0) ? Integer.MIN_VALUE : nums1[partitionM - 1];
            int minRightM = (partitionM == m) ? Integer.MAX_VALUE : nums1[partitionM];

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
                right = partitionM - 1; // move left
            } else {
                left = partitionM + 1; // move right
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
