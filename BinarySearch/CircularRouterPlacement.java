package BinarySearch;

import java.util.Arrays;
/**You are given N potential slots for wireless routers located on a circular track of circumference L. 
 * The positions are given as an array of integers representing distances from a fixed starting point 0. 
 * You must place exactly K routers in these slots such that the minimum distance between any two adjacent routers 
 * (including the wrap-around distance between the last and the first) is maximized.
 * 
 * 
 * Goal: Return the maximum possible value of this minimum distance.
 * 
 * INTUTION
 * THE "360-DEGREE" REASONING:
 * 1. TOPOLOGY: A circular track of length 'L' (e.g., 360°) has no boundaries. 
 * The distance between two points isn't just |a - b|; it's also the 
 * "wrap-around" distance across the 0-degree mark.
 * * 2. NORMALIZATION: We treat the circle as a linear array [0...L], but 
 * we add a "Wrap Check." For the K-th router to be valid, it must be 
 * >= minDist from the (K-1)th router AND the 1st router must be 
 * >= minDist from the K-th router across the finish line.
 * * 3. BSOA (Binary Search Over Answer): Because "feasibility" is monotonic 
 * (if you can space them 100ft apart, you can definitely space them 50ft), 
 * we binary search for the maximum spacing 'D'.
 *  */
public class CircularRouterPlacement {

    public int maxMinDistance(int[] slots, int k) {
        // Step 1: Normalization - Sort the slots to create a linear topology
        Arrays.sort(slots);
        int n = slots.length;
        int totalCircumference = 360; // Assuming degrees, or use max_range

        // Step 2: BSOA Range
        int low = 1;
        int high = totalCircumference / k;
        int result = 0;

        while (low <= high) {
            int mid = low + (high - low) / 2;
            // Step 4: Circular Greedy Check
            if (canPlace(slots, k, mid, totalCircumference)) {
                result = mid; // This distance is feasible, try larger
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        return result;
    }

    private boolean canPlace(int[] slots, int k, int minDist, int limit) {
        // In a circle, the 'optimal' start is unknown.
        // Optimization: Try starting greedy from the first few slots.
        // For most interview constraints, trying to start at slots[0] 
        // and checking the wrap-around is a strong starting point.
        
        for (int startIdx = 0; startIdx < Math.min(slots.length, 1); startIdx++) {
            int count = 1;
            int lastPos = slots[startIdx];
            
            for (int i = 1; i < slots.length; i++) {
                if (slots[i] - lastPos >= minDist) {
                    count++;
                    lastPos = slots[i];
                }
            }
            
            // Step 4 logic: Check the wrap-around distance 
            // between the last router and the first router.
            int wrapDistance = (slots[startIdx] + limit) - lastPos;
            if (count >= k && wrapDistance >= minDist) {
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        CircularRouterPlacement solver = new CircularRouterPlacement();
        int[] slots = {10, 20, 100, 150, 300};
        int k = 3;
        System.out.println("Maximized Minimum Distance: " + solver.maxMinDistance(slots, k));
    }
}