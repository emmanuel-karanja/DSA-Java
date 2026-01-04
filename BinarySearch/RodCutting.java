package BinarySearch;

/**
 * PROBLEM: Rod Cutting – Maximize Equal Piece Length
 *
 * GIVEN:
 * - An array `rods[]` where rods[i] is the length of the i-th rod
 * - An integer `k` representing the minimum number of pieces required
 *
 * RULES (non-negotiable):
 * 1. Rods may be cut arbitrarily
 * 2. All pieces must have the SAME length
 * 3. Pieces must have POSITIVE length
 *
 * GOAL:
 * 
 * The upper limit is the length of the longest rod and the smallest is 1.
 * 
 * What we want is to minimize the piecesso we need to maximise the length of each piece.
 * 
 * So we search for lengths between [1...max(lengths)] such that the pieces count >=k
 * - Find the MAXIMUM possible length `L` such that
 *   we can obtain AT LEAST `k` pieces of length `L`
 *
 * INTUITION:
 * - If we choose a candidate length `L`,
 *   each rod of length `r` can produce `r / L` pieces.
 * - Total pieces = sum(r / L)
 *
 * MONOTONICITY:
 * - If length `L` is feasible, any smaller length is also feasible
 * - If length `L` is NOT feasible, any larger length is NOT feasible
 *
 * ⇒ Binary search on the answer applies.
 *
 * SEARCH SPACE:
 * - Lower bound = 1
 *   (smallest possible positive piece length)
 *
 * - Upper bound = max(rods)
 *   (no piece can be longer than the longest rod)
 *
 * FEASIBILITY FUNCTION:
 * “Can we cut the rods to get at least `k` pieces of length `L`?”
 *
 * TIME COMPLEXITY:
 * - O(n log maxRodLength)
 *
 * SPACE COMPLEXITY:
 * - O(1)
 */
public class RodCutting {

    public static int maxPieceLength(int[] rods, int k) {
        int left = 1;
        int right = 0;

        for (int rod : rods) {
            right = Math.max(right, rod);
        }

        int answer = 0;

        while (left <= right) {
            int mid = left + (right - left) / 2;

            if (canCut(rods, mid, k)) {
                answer = mid;       // feasible, try longer pieces
                left = mid + 1;
            } else {
                right = mid - 1;    // not feasible, shorten pieces
            }
        }

        return answer;
    }

    // Feasibility check
    private static boolean canCut(int[] rods, int length, int k) {
        long pieces = 0;

        for (int rod : rods) {
            pieces += rod / length;
        }

        return pieces >= k;
    }

    // Driver
    public static void main(String[] args) {
        int[] rods = {4, 7, 9};
        int k = 5;

        System.out.println("Maximum possible piece length = " +
                maxPieceLength(rods, k));
        // Output: 3
    }
}
