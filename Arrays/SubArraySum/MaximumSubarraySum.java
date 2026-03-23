package Arrays.SubArraySum;

public class MaximumSubarraySum {
    public static int maxSubarraySum(int[] nums) {
        int maxSum = nums[0];   // max sum ending anywhere
        int currSum = nums[0];  // max sum ending at current position

        for (int i = 1; i < nums.length; i++) {
            // Either extend the current subarray or start fresh at nums[i]
            // If the current num is greater than the prevSum+current Num we start a new subarray at current num
            currSum = Math.max(nums[i], currSum + nums[i]);
            maxSum = Math.max(maxSum, currSum);
        }

        return maxSum;
    }

    public static void main(String[] args) {
        int[] nums = {-2, 1, -3, 4, -1, 2, 1, -5, 4};
        System.out.println("Maximum Subarray Sum: " + maxSubarraySum(nums)); // Output: 6
    }
}