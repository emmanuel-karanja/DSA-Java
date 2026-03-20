package Arrays;
/**Problem Statement

You are given an integer array nums and an integer k. Find the maximum sum of any contiguous subarray with length at least k.

Constraints
1 <= nums.length <= 10^5
-10^4 <= nums[i] <= 10^4
1 <= k <= nums.length

Example

nums = [1, 2, -1, 2, 3], k = 3

Subarrays of length >= 3:
[1,2,-1] → sum = 2
[2,-1,2] → sum = 3
[-1,2,3] → sum = 4
[1,2,-1,2] → sum = 4
[2,-1,2,3] → sum = 6
[1,2,-1,2,3] → sum = 7

Answer: 7

BruteForce:

O(n²) approach:
Generate all subarrays of length >= k
Compute sums
Track max
Works conceptually, but too slow for n = 10^5

*/
public class MaxSubArraySumOfAtLeastLengthK {

   public int maxSubarraySum(int[] nums, int k) {
    int n = nums.length;
    int[] prefix = new int[n+1];
    
    for(int i=0;i<n;i++) {
        prefix[i+1] = prefix[i]+nums[i];
    }

    int maxSum = Integer.MIN_VALUE;
    int minPrefix = 0;

    // Nice!
    for(int i=k;i<=n;i++){
        minPrefix = Math.min(minPrefix, prefix[i-k]);
        maxSum = Math.max(maxSum, prefix[i]-minPrefix);
    }

    return maxSum;
}
}
