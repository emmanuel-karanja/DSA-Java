package BinarySearch;
/**Just like first ocucrence but this time we only set the left-mid+1 when we keep searching*/
public class LastOccurrenceBinarySearch {
    
    public static int lastOccurence(int[] nums,int  target){

        int left=0;
        int right=nums.length-1;

        int result=-1;
        while(left<=right){
            int mid=left+(right-left)/2;

            if(nums[mid]==target){
                result =mid;
                //we keep searching
                left=mid+1;
            }else if(nums[mid] < target){
                left=mid+1;
            }else{
                right=mid-1;
            }
        }

        return result;
    }

        public static void main(String[] args){
        int[] nums = {1, 2, 2, 2, 3, 4};
        System.out.println(lastOccurence(nums, 2)); // Output: 1
    }
}
