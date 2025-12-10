package Arrays;
/**Given an array of positive integers, find the length of the longest subarray with a sum at most k.
 * 
 * INTUTION:
 * 
 * Use an expanding sliding window such that if the sum between left and right is >k, move advance left.
 */
public class LongestSubArrayWithSumAtMostK {
    public static int getSumAtMostK(int[] nums,int k){
        if(nums==null || nums.length==0){
            throw new IllegalArgumentException("Nums can't be either null or empty.");
        }

        int left=0;
        int right=0;
        int sum=0;
        int maxLength=0;

        while(right<nums.length){
            sum+=nums[right];
            
            //shrink the window if the sum>k
            while(sum>k){
                sum-=nums[left];
                left++;
            }

            maxLength=Math.max(maxLength,right-left+1);

            right++;
        }

        return maxLength;  //to return the subarray, select elements from left until and including right

    }

    public static void main(String[] args){
        int[] nums = {2, 1, 5, 1, 3, 2};
        int k = 7;
        int result = getSumAtMostK(nums, k);

        System.out.println("Longest subarray length with sum <= " + k + " is: " + result);
    }
}
