package DP;
/**Find the maximum subarray sum of an array.
 * 
 * INTUTION
 * 
 * When at i, ask yourself, are we expanding the current subarray or starting a new one? 
 * This means keeping a maxSoFar, 
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
