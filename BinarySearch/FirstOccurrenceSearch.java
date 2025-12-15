package BinarySearch;
/**Given a sorted array, find the first occurence of a given target value if it exists.
 * 
 * INTUTION"
 * 
 *  Binary search until you get the target, and then keep searching i.e. set right=mid-1 even after
 *  we've found the value this is the only difference between this and the classic binary search.
 */
public class FirstOccurrenceSearch {

    public static int firstOccurrence(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;
        int result = -1; // stores index of first occurrence

        while (left <= right) {
            int mid = left + (right - left) / 2;

            if (nums[mid] == target) {
                result = mid;      // potential answer
                right = mid - 1;   // keep searching left
            } else if (nums[mid] < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        return result;
    }

    public static void main(String[] args){
        int[] nums = {1, 2, 2, 2, 3, 4};
        System.out.println(firstOccurrence(nums, 2)); // Output: 1
    }
}


