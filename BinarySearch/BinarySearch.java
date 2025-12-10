package BinarySearch;

/**Given a sorted array, find a target value t, in log(n) time
 * 
 * INTUTION:
 * 
 * Try tolocate the element in the middle,if you can't find it find out which half of the array it lies in and recursively
 * seatch that using twopoints
 */
public class BinarySearch {

    public static int binarySearchTarget(int[] arr, int target){
        if(arr==null || arr.length==0){
            throw new IllegalArgumentException("Array is null or empty.");
        }

        int low=0;
        int high=arr.length-1;
        while(low<high){
            int mid=low+(high-low)/2;
            if(arr[mid]==target) return mid;

            if(target<arr[mid]){
               high=mid-1; 
            }else{
               low=mid+1;
            }

        }

        return -1; //could not find
    }

    public static void main(String[] args){
        int[] arr={1,3,5,7,9,11,13};
        int target=7;

        System.out.println(binarySearchTarget(arr, target));
    }   
}
