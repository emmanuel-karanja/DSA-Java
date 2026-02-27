package Arrays;

import java.util.Arrays;
/**"Passive Retention" strategy. In a Min-Heap of size K, the "big boys" are 
safely buried in the lower levels of the tree, while the "weakest link" is constantly pushed to the root,
 effectively volunteering as tribute for the next poll() operation.
 Instead of maintaining a heap, you pick a "pivot" and partition the array into elements larger than
  the pivot and elements smaller.

If the pivot ends up exactly at index n - k, everything to the right of it is your "Top K."

Unlike a Heap, you don't care about the order within those K elements while you're searching.

THIS IS ONLY GOOD FOR STATIC ARRAY --> The heap is still the king when it comes to streaming data case.

*/
public class QuickSelectTopK {
    public int[] topKQuickSelect(int[] nums, int k) {
        int n = nums.length;
        int targetIndex = n - k;
        int left = 0, right = n - 1;
        
        while (left <= right) {
            int pivotIndex = partition(nums, left, right);
            if (pivotIndex == targetIndex) {
                break;
            } else if (pivotIndex < targetIndex) {
                left = pivotIndex + 1;
            } else {
                right = pivotIndex - 1;
            }
        }
        
        return Arrays.copyOfRange(nums, targetIndex, n);
    }

    private int partition(int[] nums, int left, int right) {
        int pivot = nums[right];
        int i = left;
        for (int j = left; j < right; j++) {
            if (nums[j] <= pivot) {
                swap(nums, i++, j);
            }
        }
        swap(nums, i, right);
        return i;
    }

    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
}
