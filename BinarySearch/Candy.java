package BinarySearch;

import java.util.Arrays;

/**Given piles of candies, and k children distribute the candies to maximize the minimum i.e. each kid gets as many as possible
 * but all get.
 * 
 * INTUITION:
 * 
 * What's the minumum amount of candy a kid can get? 1 and the maximum? Size of the pile? Kids don't get the same amount
 * 
 * so suppose we search between 1 and max of candies?
 * 
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
            // Here if the pile < candiesPerChild, we have 0, we've not allocated that pile. and it means
            // candiesPerChild is too large and we'll pick a smaller value
            int kidsForThisPipe=pile / candiesPerChild;
            totalKids += kidsForThisPipe;
        }

        return totalKids >= k;
    }

    public static void main(String[] args) {
        int[] candies = {5, 8, 6};
        int k = 3;

        int maxCandiesPerChild = maxCandies(candies, k);
        System.out.println("Maximum candies per child: " + maxCandiesPerChild);
    }
}
