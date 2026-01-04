package BinarySearch;

import java.util.Arrays;

/**
 * PROBLEM: Maximum Candies per Child
 *
 * GIVEN:
 * - An array `candies[]` where candies[i] represents the number of candies in the i-th pile
 * - An integer `k` representing the number of children
 *
 * RULES:
 * 1. Each child must get at least 1 candy
 * 2. Candies can be taken from any pile, but each child gets exactly `candiesPerChild` candies
 * 3. Piles can be split; a single pile can contribute to multiple children
 *
 * GOAL:
 * - Maximize the minimum number of candies each child receives
 *
 * INTUITION:
 * 1. Candidate answer = number of candies per child
 * 2. Feasibility function: For a candidate number `candiesPerChild`, compute how many children
 *    can be served from all piles. If >= k, candidate is feasible.
 * 3. Monotone property:
 *    - If `candiesPerChild` is feasible, any smaller number is also feasible
 *    - If `candiesPerChild` is NOT feasible, any larger number is also NOT feasible
 *
 * SEARCH SPACE:
 * - Lower bound = 1 (minimum possible positive candies per child)
 * - Upper bound = max(candies) (largest pile determines the maximum possible per child)
 *
 * BINARY SEARCH:
 * - Search for the largest feasible candidate
 */
public class Candy {

    public static int maxCandies(int[] candies, int k) {
        int left = 1; // minimum candies per child
        int right = Arrays.stream(candies).max().orElse(0); // maximum possible candies from a single pile
        int result = 0;

        while (left <= right) {
            int mid = left + (right - left) / 2;

            if (canDistribute(candies, k, mid)) {
                // mid is feasible, try to maximize
                result = mid;
                left = mid + 1;
            } else {
                // mid not feasible, reduce
                right = mid - 1;
            }
        }

        return result;
    }

    // Feasibility function: can we give at least 'candiesPerChild' to k children?
    private static boolean canDistribute(int[] candies, int k, int candiesPerChild) {
        int totalKids = 0;

        for (int pile : candies) {
            totalKids += pile / candiesPerChild;
        }

        return totalKids >= k;
    }

    public static void main(String[] args) {
        int[] candies = {5, 8, 6};
        int k = 3;

        int maxCandiesPerChild = maxCandies(candies, k);
        System.out.println("Maximum candies per child: " + maxCandiesPerChild); // Expected: 5
    }
}
