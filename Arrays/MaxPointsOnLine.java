package Arrays;

import java.util.*;

/**
 * ==========================================================
 * PROBLEM: Max Points on a Line
 * ==========================================================
 *
 * Given an array of points where points[i] = [xi, yi],
 * return the maximum number of points that lie on the
 * same straight line.
 *
 * ----------------------------------------------------------
 * KEY IDEA
 * ----------------------------------------------------------
 *
 * Two points define a line.
 *
 * For every point i:
 *      compute slopes to all other points
 *
 * Points sharing the same slope relative to i
 * lie on the same line.
 *
 * To avoid floating point precision issues,
 * slopes are stored as a reduced fraction (dy/dx)
 * using GCD normalization.
 * 
 * This is the recursive gcd identity:  but check for divide by 0 first. if b is 0, return a.
 * gcd(a, b) = gcd(b, a % b) 
 *
 * ----------------------------------------------------------
 * EDGE CASES
 * ----------------------------------------------------------
 *
 * 1. Duplicate points
 * 2. Vertical lines (dx = 0)
 *
 * ----------------------------------------------------------
 * COMPLEXITY
 * ----------------------------------------------------------
 *
 * Time  : O(N²)
 * Space : O(N)
 *
 * ==========================================================
 */

public class MaxPointsOnLine {

    public static int maxPoints(int[][] points) {

        if (points.length <= 2)
            return points.length;

        int result = 0;

        for (int i = 0; i < points.length; i++) {

            Map<String, Integer> slopeCount = new HashMap<>();
            int duplicates = 0;
            int localMax = 0;

            int x1 = points[i][0];
            int y1 = points[i][1];
           // realizing that p1 to p2 is the same as p2 to p1
            for (int j = i + 1; j < points.length; j++) {

                int x2 = points[j][0];
                int y2 = points[j][1];

                int dx = x2 - x1;
                int dy = y2 - y1;

                if (dx == 0 && dy == 0) {
                    duplicates++;
                    continue;
                }

                 // Note how to normalize dy and dx
                int gcd = gcd(dx, dy);
                dx /= gcd;
                dy /= gcd;

                String slope = dy + "/" + dx;

                int count = slopeCount.getOrDefault(slope, 0) + 1;
                slopeCount.put(slope, count);

                localMax = Math.max(localMax, count);
            }

             // We do +1 since we have to count the anchor point i.e. this point at i.
            result = Math.max(result, localMax + duplicates + 1);
        }

        return result;
    }

    // Normalize the slope e.g. we could have diffrent values dy and dx but dy/dx shows us they are the same slope
    private static int gcd(int a, int b) {
        if (b == 0)
            return a;
        return gcd(b, a % b);
    }

    public static void main(String[] args) {

        int[][] points = {
                {1,1},
                {2,2},
                {3,3},
                {3,4},
                {4,5}
        };

        System.out.println(maxPoints(points));
    }
}