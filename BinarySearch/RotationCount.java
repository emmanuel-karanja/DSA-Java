package BinarySearch;

/**You are given a rotated sorted array of integers. Find the number of rotations on that array in log(n) time.
 * 
 * INTUTION:
 * 
 * The rotation count is the index of the smallest element in the array.
 * You can iterate over the array and keep track of the smallest element. This takes log(n) time.
 * 
 * You can do a binary search to determine which side is sorted i.e. get the midpoint mid,
 * if arr[mid]> arr[right], then, the sorted side is the left side. Minimum willbeon the UNSORTED SIDE
 */

public class RotationCount {

    public static int getRotationCount(int[] arr){
        if(arr==null || arr.length==0) {
            throw new IllegalArgumentException("Array is either empty or null.");
        }

        int left=0;
        int right=arr.length-1;

        while(left<right){
            int mid=left+(right-left)/2; //this is the way to calculate the mid, to avoid integer overflow

            if(arr[mid]>arr[right]){
               left=mid+1; //since right side is the unsorted
            }else{ //the unsorted side is left
                right=mid;
            }
        }

        return left; //or right if the question asks for the number return arr[left]
    }

   public static void main(String[] args){
      int[] nums={4,5,6,7,0,1,2};

      System.out.println(getRotationCount(nums));
   }  
}
