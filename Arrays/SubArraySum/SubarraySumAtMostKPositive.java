package Arrays.SubArraySum;
public class SubarraySumAtMostKPositive {
    public static int countSubarraysAtMostK(int[] nums, int K) {
        int start = 0;
        int sum = 0;
        int count = 0;

        for (int end = 0; end < nums.length; end++) {
            sum += nums[end];

            // Shrink the window until sum <= K
            while (sum > K && start <= end) {
                sum -= nums[start];
                start++;
            }

            // All subarrays ending at 'end' and starting from start..end are valid
            count += (end - start + 1);
        }

        return count;
    }

    public static void main(String[] args) {
        int[] nums = {1, 2, 3, 4};
        int K = 5;
        System.out.println(countSubarraysAtMostK(nums, K)); // Output: 6
    }
}