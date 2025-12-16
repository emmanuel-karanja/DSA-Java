package DP;
/**Find the maximum subarray sum of an array.
 * 
 * INTUTION
 * 
 * When at i, ask yourself, are we expanding the current subarray or starting a new one? 
 * This means keeping a maxSoFar, 
 * 
 * DO BREAKDOWN
 * 
 * GOAL: Find the maximum subarray sum, not the subarray itself.  Which means State is dp[x]
 * 
 * CHOICE: At index i, we have exactly two choices:
        1.Start a new subarray at i 
             Take arr[i] alone
        2. Extend the previous subarray
            Take dp[i-1] + arr[i]

We do not consider all previous indices because dp[i-1] already encodes the optimal choice.
 * STATE: At each step i dp[i] is the maximum subarray sum at i.
 * 
 * RECURRENCE RELATION:  dp[i]=Max(dp[i]+arr[i],arr[i])
 * 
 * BASE CASE: dp[0] (1 element array) dp[0]=arr[0]
 * 
 * SOLN: take the max(dp[0...n-1])
 */

public class Kadane {

    public static int maxSubArraySum(int[] nums){
        if(nums==null || nums.length==0){
            throw new IllegalArgumentException("Nums is null or empty.");
        }

        if(nums.length==1) return nums[0];
        int maxSoFar=nums[0];
        int currentSum=nums[0];
    

        for(int i=1;i<nums.length;i++){
           // Expand current subarray or start new
            currentSum = Math.max(nums[i], currentSum + nums[i]);
            // Update overall max
            maxSoFar = Math.max(maxSoFar, currentSum);
        }

        return maxSoFar;
    }

    public static void main(String[] args){
        int[] nums = {-2, 1, -3, 4, -1, 2, 1, -5, 4};
        System.out.println("Max Subarray Sum: " + maxSubArraySum(nums)); // Should print 6
    }
    
}
