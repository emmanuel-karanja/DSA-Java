package BinarySearch;

import java.util.Arrays;

/**Given  positions along a road and k new gas stations, minimize the maximum distance between adjacent gas stations.
 * INTUTION:
 * 
 * positions I assume are not evenly distributed. And so if you have k new gas stations, you want to find them such
 * that. the first one is at the lowest position i.e. position[0] and the furthest at least at position[n-1] or earlier.
 * 
 * left=position[0];
 * right=position[n-1] - position[0]
 * 
 * we search this space using binary search such that given minDistance. Beyond a certain distance x, we can't
 * fit them in that space.
 * we find if we can place all the gas stations.  This is the feasibility function. Beyond a certain
 * distance, we cannot fit. 
 * 
 * Feasibility: can we fit the gas stations along this road at distance at most x?
 */
public class NewGasStationsPlacement {

    public static double minmaxGasDist(int[] positions, int k) {
        Arrays.sort(positions);

        double left = 0;
        double right = positions[positions.length - 1] - positions[0];
        double eps = 1e-6; // precision

        while (right - left > eps) {
            double mid = left + (right - left) / 2.0;

            if (canPlace(positions, k, mid)) {
                
                right = mid; // try smaller maximum distance this right=mid only works if you are using doubles!!!
            } else {
                left = mid; // need larger distance
            }
        }

        return left;
    }

    private static boolean canPlace(int[] positions, int k, double distance) {
        int n = positions.length;
        int totalPlaced = 0;

        for (int i = 1; i < n; i++) {
            int gap = positions[i] - positions[i - 1];
            int stationsForThisGap=(int) Math.floor(gap-1/distance);
            totalPlaced += stationsForThisGap; // how many stations needed for this gap
        }

        return totalPlaced <= k; // feasible if we can place k or fewer
    }

    public static void main(String[] args) {
        int[] positions = {1, 2, 3, 4, 7};
        int k = 3;

        double res = minmaxGasDist(positions, k);
        System.out.printf("Minimum possible maximum distance: %.6f\n", res);
    }
}
