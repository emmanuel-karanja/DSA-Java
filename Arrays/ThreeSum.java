package Arrays;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**Given an array of integers, return all unique triplets nums[i],nums[j] and nums[k] such that
 * i!=j!=k that sum upto 0.
 * 
 * INTUTIOM
 * 
 * Sort the array of the numbers.
 * Skip duplicates.
 * 
 * If the sum ==0 add the triplet to the result set and
 *    skip left side inner duplicates.
 *    skip right side inner duplicates.
 * If the sum of a triplet is < 0 advance left since we want a more positive number.
 * If the sum of a triplet  > 0 advance right since we want a less positive number.
 * 
 * Use 3 points: i, left=i+1, and right=nums.length-1, right will decrement and left will increment
 */
public class ThreeSum {

    public static List<Integer[]> getTriplets(int[] nums){
        if(nums==null || nums.length ==0){
           throw new IllegalArgumentException("Nums is empty or null.");
        } 

        if(nums.length <3){
            throw new IllegalArgumentException("Nums has a length < 3.");
        }

        List<Integer[]> result= new ArrayList<>();

        //sort
        Arrays.sort(nums); //default ascending order sort

        for(int i=0;i<nums.length-2;i++){ //right will be nums.length-1 and max left is nums.length-2
            //skip initial duplicates
            if(i>0 && nums[i]==nums[i-1]) continue;

            int left=i+1;
            int right=nums.length-1;

           
            while(left < right){
                int sum=nums[i]+nums[left]+nums[right];

                if(sum==0){
                    Integer[] triplet={nums[i],nums[left],nums[right]};
                    result.add(triplet);

                    //skip inner duplicates
                    while(left<right && nums[left]==nums[left+1]) left++;
                    while(left<right && nums[right]==nums[right-1]) right--;

                    //move both after we've found the triplet
                    left++;
                    right--;
                }else if(sum <0){
                     //advance left to get a larger number since the negative numbers are on the left due to sorting
                     left++; 
                }else{  //sum > 0
                    //advance right
                    right--;
                }
            }
        }

        return result;
    }
    
}
