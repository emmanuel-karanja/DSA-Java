package Backtracking;

/**
 * STRATEGY: Backtracking with Pruning
 * Logic: Try placing each number into each of the K buckets.
 * Pruning 1: Sort descending (large numbers fail faster).
 * Pruning 2: If a number doesn't fit in a bucket, move to the next bucket.
 * Pruning 3: If an empty bucket fails to take an item, skip all other empty buckets.
 */
import java.util.Arrays;

public class PartitionBacktracking {
    public boolean canPartitionKSubsets(int[] nums, int k) {
        int sum = Arrays.stream(nums).sum();
        if (sum % k != 0) return false;
        int target = sum / k;

        // Sort descending to fail faster
        Arrays.sort(nums);
        reverse(nums);

        return backtrack(nums, new int[k], 0, target);
    }

     // Notice how this an exact match to the Backtracking canonical form.
    private boolean backtrack(int[] nums, int[] buckets, int index, int target) {
        // Goal: All items have been placed
        if (index == nums.length) return true;

        int currentNum = nums[index];

        for (int i = 0; i < buckets.length; i++) {
            // Take Case: Can we put the current number in this bucket?
            if (buckets[i] + currentNum <= target) {
                buckets[i] += currentNum; // Take
                
                if (backtrack(nums, buckets, index + 1, target)) return true;
                
                buckets[i] -= currentNum; // Skip/Backtrack
            }

            // PRUNING: If this bucket was empty and we couldn't fit the number, 
            // there's no point in trying this number in subsequent empty buckets.
            if (buckets[i] == 0) break;
        }
        return false;
    }

    private void reverse(int[] nums) {
        for (int i = 0, j = nums.length - 1; i < j; i++, j--) {
            int temp = nums[i];
            nums[i] = nums[j];
            nums[j] = temp;
        }
    }
}