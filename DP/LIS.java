package DP;

import java.util.Arrays;

/**Given an array of integers, find the length of the longest subsequence (not necessarily contiguous) 
 * such that the elements are strictly increasing.
 * nums = [10, 9, 2, 5, 3, 7, 101, 18]
LIS = [2, 3, 7, 101] → length = 4

INTUITION:

 We are at position I, we need to find the count of all increasing elements from i to 0
 i.. j such that j>=0 and j<i

 Each number has a LIS of 1.

 Decision: At j do we increase or not?
 Solution is the space j>=0 and j<i where nums[i] >nums[j]
 *  */
public class LIS {
    
    public static int getLongestIncreasingSubsequence(int[] nums){
        if(nums==null || nums.length==0){
            throw new IllegalArgumentException("Number list is empty or null.");
        }

        if(nums.length==1) return 1;  // where there is base case we don't need to model the base state.

        int[]dp=new int[nums.length];
        int max=1;

        Arrays.fill(dp,1); //since a number's LIS is 1
        for(int i=1;i<nums.length;i++){
            for(int j=0;j<i;j++){
                // we either extend the list at j or we don't.
                if(nums[i]>nums[j]){
                    dp[i]=Math.max(dp[i],dp[j]+1);
                }
                
            }

            max=Math.max(max,dp[i]);
        }

        return max;

    }
}
