package BinarySearch;

import java.util.Arrays;

/**
 * You are given N potential slots for wireless routers located on a circular track of circumference L. 
 * The positions are given as an array of integers representing distances from a fixed starting point 0. 
 * You must place exactly K routers in these slots such that the minimum distance between any two adjacent routers 
 * (including the wrap-around distance between the last and the first) is maximized.
 * 
 * Goal: Return the maximum possible value of this minimum distance.
 * 
 * INTUITION:
 * THE "360-DEGREE" REASONING:
 * 1. TOPOLOGY: A circular track of length 'L' (e.g., 360°) has no boundaries. 
 *    The distance between two points isn't just |a - b|; it's also the "wrap-around" distance across the 0-degree mark.
 * 2. LINEARIZATION: Treat the circle as a linear array of length 2*N by duplicating slots with +L.
 *    This simplifies wrap-around checks.
 * 3. BSOA (Binary Search Over Answer): Because "feasibility" is monotonic 
 *    (if you can space them 100 apart, you can definitely space them 50), 
 *    we binary search for the maximum spacing 'D'.
 */
public class CircularRouterPlacement {

    public int maxMinDistance(int[] slots, int k) {
        // Step 1: Sort slots for greedy placement
        Arrays.sort(slots);
        int totalCircumference = 360; // You can adjust this if needed

        // Step 2: Binary Search Over Answer (BSOA)
        int low = 1;
        int high = totalCircumference / k;   //Note this
        int result = 0;

        while (low <= high) {
            int mid = low + (high - low) / 2;
            // Step 3: Check if we can place routers with at least `mid` distance
            if (canPlaceLinearized(slots, k, mid, totalCircumference)) {
                result = mid; // feasible, try larger
                low = mid + 1;
            } else {
                high = mid - 1; // not feasible, try smaller
            }
        }
        return result;
    }

    /**
     * Greedy placement using linearization.
     * Duplicate the array with +L to handle wrap-around automatically.
     */
    private boolean canPlaceLinearized(int[] slots, int k, int minDist, int L) {
        int n = slots.length;
        int[] linear = new int[2 * n];

        // Step 1: Copy original slots
        for (int i = 0; i < n; i++) {
            linear[i] = slots[i];
        }

        // Step 2: Duplicate with +L to handle wrap-around
        for (int i = 0; i < n; i++){
           linear[i + n] = slots[i] + L;
        } 

        // Step 3: Try placing k routers starting from each original slot
        // Treat every i as a possible starting point.
        for (int start = 0; start < n; start++) {
            int count = 1;
            int last = linear[start];

            // This inner loop answers the question, is it possible to place all the routers
            // starting from index 'start'? and with minDist between the position? if true we stop
            for (int i = start + 1; i < start + n; i++) { // Note the limit
                if (linear[i] - last >= minDist) {
                    count++;
                    last = linear[i];
                    if (count == k) break; // placed all routers
                }
            }

            if (count >= k) return true; // feasible
        }

        return false; // not feasible
    }

    public static void main(String[] args) {
        CircularRouterPlacement solver = new CircularRouterPlacement();
        int[] slots = {10, 20, 100, 150, 300};
        int k = 3;
        System.out.println("Maximized Minimum Distance: " + solver.maxMinDistance(slots, k));
    }
}
