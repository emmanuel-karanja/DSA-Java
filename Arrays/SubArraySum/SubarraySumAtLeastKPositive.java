public class SubarraySumAtLeastKPositive {
    public static int countSubarraysAtLeastK(int[] nums, int K) {
        int n = nums.length;

        // Total subarrays
        int total = n * (n + 1) / 2;

        // Count subarrays with sum < K
        int start = 0, sum = 0, countLess = 0;
        for (int end = 0; end < n; end++) {
            sum += nums[end];

            // Shrink window while sum >= K
            while (sum >= K && start <= end) {
                sum -= nums[start];
                start++;
            }

            // All subarrays ending at 'end' and starting from start..end have sum < K
            countLess += (end - start + 1);
        }

        // Subarrays with sum >= K
        return total - countLess;
    }

    public static void main(String[] args) {
        int[] nums = {1, 2, 3, 4};
        int K = 5;
        System.out.println(countSubarraysAtLeastK(nums, K)); // Output: 4
        // [2,3], [3,4], [1,2,3], [2,3,4]
    }
}