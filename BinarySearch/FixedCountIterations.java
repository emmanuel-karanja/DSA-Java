package BinarySearch;
/**The point of this is to show how to handle the binary search loop when dealing with doubles. 
 * 
 * EXPONENTIAL DECAY
 * 
 * The core mechanism of this approach is exponential decay. Every time the loop runs once, the mid 
 * calculation splits the current search range exactly in half. By the time you reach 100 iterations, 
 * you have divided your initial search space by 2^100. Because 2^100 is a massive number 
 * (over 30 decimal digits), the window between low and high shrinks so small that it eventually hits 
 * the physical limit of what a 64-bit double can represent. At that point, low and high are essentially 
 * the same bit-pattern in memory, providing the most accurate answer possible.
 * 
 * WHY WE NEED IT (AVOID TINY GAP INFINITE LOOP)
 *   We need this fixed-iteration method because double precision is relative, not absolute. As numbers get larger,
 *  the "gap" between representable numbers on the number line grows. 
 * 
 *  If you use a while loop with a tiny constant like 1e-12, but your input values are in the trillions, 
 *  the gap between two adjacent doubles might be larger than 1e-12. This creates a "trap" where the loop condition
 *   (high - low > EPS) is always true, but the computer is
 *  physically incapable of making the gap any smaller, leading to an infinite loop. The for loop bypasses this
 *  by simply stopping once the hardware's maximum precision has been reached.
 */
public class FixedCountIterations {
    public static void main(String[] args) {
        double low = 0.0;
        double high = 1e9; // Set to your maximum possible range

        // 100 iterations provides precision up to ~10^-30, 
        // which exceeds the precision of a 64-bit double.
        for (int i = 0; i < 100; i++) {
            double mid = low + (high - low) / 2.0;

            if (check(mid)) {
                // If mid is valid, we look for a 'better' value (higher or lower)
                low = mid; 
            } else {
                high = mid;
            }
        }

        System.out.println("Result: " + low);
    }

    private static boolean check(double val) {
        // Your logic here: return true if 'val' satisfies the condition
        return true; 
    }
}
