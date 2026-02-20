package BinarySearch;
/**You are given a rotated sorted array of integers and a target value. Find the target value in log(n) time
 * 
 * INTUTION:
 * 
 * Log(n) is a dead give away that binary search will be usedhere.
 * 
 * Find the midpoint, and then compare with the target,if you hit return that.
 * 
 * KEY: SEARCH THE SORTED HALF OF THE ARRAY
 * 
 * if arr[mid]<arr[right] the right half is sorted and you search that.
 */
public class SearchRotatedArray {

    public static int getFromRotatedArray(int[] nums,int target){
        //edge case
        if(nums==null || nums.length ==0) return -1;

        int left=0;
        int right=nums.length-1;

        while(left<=right){
            //find if right half is sorted

            int mid=left+(right-left)/2;

            if(nums[mid]==target) return mid;

            if(nums[mid]<nums[right]){ // sorted half is right
                //Does the target fall within that half? If not, we search the other half
                if(nums[mid]< target && target <= nums[right]){
                    left=mid+1;
                }else{ //search left half
                    right=mid-1;
                }
            }else{
                //left half is sorted
                if(nums[left]<= target && target<nums[mid]){
                    right=mid-1;
                }else{
                    left=mid+1;
                }
            }
        }

        return -1; //not found
    }
    
    public static void main(String[] args) {
        int[] nums = {4,5,6,7,0,1,2};
        System.out.println(getFromRotatedArray(nums, 0)); // prints 4
    }
}
