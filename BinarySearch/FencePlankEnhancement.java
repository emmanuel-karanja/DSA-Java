package BinarySearch;

/**
 * PROBLEM: Fence Plank Enhancement
 * * Given N planks of various heights and K operations.
 * * Each operation increases a plank's height by 1.
 * * GOAL: Maximize the minimum height (Make the shortest plank as tall as possible).
 * * APPROACH: Binary Search Over Answer (BSOA).
 * * The "Check" function is GREEDY because we always want to fill the shortest 
 * planks first to meet a target height H.
 */
public class FencePlankEnhancement {

    public int maximizeMinHeight(int[] heights, int k) {
        int low = 0;
        int high = 0;
        
        // Find the range for BSOA
        for (int h : heights) {
            low = Math.min(low, h);
            // Theoretical max: shortest plank + all K operations. Note this. Assume all the operations were
            // applied on the longest plank.
            high = Math.max(high, h + k); 
        }

        int result = low;
        while (low <= high) {
            int mid = low + (high - low) / 2;
            
            if (canAchieve(heights, mid, k)) {
                result = mid; // This height is possible, try higher
                low = mid + 1;
            } else {
                high = mid - 1; // Too high, try lower
            }
        }
        return result;
    }

    /**
     * The FEASIBILITY CHECK (Greedy)
     * Time: O(N)
     */
    private boolean canAchieve(int[] heights, int target, int k) {
        long operationsNeeded = 0; // Use long to prevent overflow
        for (int h : heights) {
            if (h < target) {  // Crucial check
                operationsNeeded += (target - h);
            }
        }
        return operationsNeeded <= k;
    }
}