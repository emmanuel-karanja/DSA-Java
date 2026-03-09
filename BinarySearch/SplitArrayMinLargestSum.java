package BinarySearch;


/**
 * =====================================================
 * Split Array into K Subarrays Minimizing Maximum Sum
 * =====================================================
 *
 * Given nums[] and integer k,
 * split the array into k contiguous subarrays
 * such that the largest subarray sum is minimized.
 *
 * Approach:
 * Binary search the answer.
 *
 * Time Complexity: O(N log(sum))
 * Space Complexity: O(1)
 */
public class SplitArrayMinLargestSum {

    public static int splitArray(int[] nums, int k) {

        int left = 0;
        int right = 0;

        for (int n : nums) {
            left = Math.max(left, n);
            right += n;
        }

        int ans=0;

        while (left < right) {

            int mid = left + (right - left) / 2;
          
            if (canSplit(nums, k, mid)) {
                
                ans=mid;
                right = mid-1;
            } else {
                left = mid + 1;
            }
        }

        return ans;
    }

    private static boolean canSplit(int[] nums, int k, int maxSum) {

        int subarrays = 1;
        int currentSum = 0;

        for (int num : nums) {

            if (currentSum + num > maxSum) {
                subarrays++;
                currentSum = num;

                if (subarrays > k)
                    return false;
            } else {
                currentSum += num;
            }
        }

        return true;
    }

    public static void main(String[] args) {

        int[] nums = {7,2,5,10,8};
        int k = 2;

        System.out.println(splitArray(nums, k));
    }
}