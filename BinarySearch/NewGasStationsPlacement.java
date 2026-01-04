package BinarySearch;

import java.util.Arrays;

/**
 * PROBLEM:
 * Given existing gas station positions along a road and k new gas stations,
 * minimize the maximum distance between adjacent gas stations.
 *
 * KEY INSIGHT:
 * We are minimizing a maximum → binary search on the answer.
 * 
 * CORE DISTICTION: The positions given already contain gas stations and we need to find the new ones we can place\
 * in the gaps i.e. positions[i]-positions[i-1]=gap
 * 
 *  stations=gap/test_distance
 *
 * SEARCH SPACE:
 * - left = 0 (best possible max distance)
 * - right = max gap between first and last station
 *
 * FEASIBILITY FUNCTION:
 * Given a candidate maximum distance `d`,
 * can we ensure every adjacent gap ≤ d using at most k new stations?
 *
 * For a gap of length `gap`,
 * the number of stations needed is:
 *
 *   stations = floor(gap / d)
 *
 * because inserting `x` stations splits the gap into `x + 1` segments.
 *
 * MONOTONICITY:
 * - If distance `d` is feasible → any larger distance is also feasible
 * - If distance `d` is not feasible → any smaller distance is not feasible
 */
public class NewGasStationsPlacement {

    public static double minmaxGasDist(int[] positions, int k) {
        Arrays.sort(positions);

        double left = 0;
        double right = positions[positions.length - 1] - positions[0];
        double eps = 1e-6;

        while (right - left > eps) {
            double mid = left + (right - left) / 2.0;

            if (canPlace(positions, k, mid)) {
                right = mid;     // try smaller maximum distance
            } else {
                left = mid;      // need larger distance
            }
        }

        return right;
    }

    private static boolean canPlace(int[] positions, int k, double maxDist) {
        int totalNeeded = 0;

        for (int i = 1; i < positions.length; i++) {
            int gap = positions[i] - positions[i - 1];
            totalNeeded += (int) Math.floor(gap / maxDist);
        }

        return totalNeeded <= k;
    }

    public static void main(String[] args) {
        int[] positions = {1, 2, 3, 4, 7};
        int k = 3;

        double res = minmaxGasDist(positions, k);
        System.out.printf("Minimum possible maximum distance: %.6f\n", res);
    }
}
