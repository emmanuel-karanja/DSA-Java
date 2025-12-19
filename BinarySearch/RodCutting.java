package BinarySearch;

/**
 * Problem: Rod Cutting for Maximum Pieces
 *
 * Given:
 * - An array `rods[]` representing the lengths of n rods.
 * - An integer `L` representing the minimum allowed length of a piece.
 *
 * Goal:
 * - Cut the rods into pieces such that each piece has length >= L.
 * - Maximize the total number of pieces obtained.
 *
 * Example:
 * Input: rods = [4, 7, 9], L = 3
 * Output: 5
 * Explanation:
 *  - Rod 4 → 1 piece of length 4
 *  - Rod 7 → 2 pieces: 3 + 4
 *  - Rod 9 → 2 pieces: 3 + 6
 *  Total = 1 + 2 + 2 = 5 pieces
 *
 * Approach:
 * - Binary search on the candidate piece length.
 * - Check feasibility: Can we cut rods into pieces of length >= candidate?
 * - Search for the largest feasible piece length to maximize pieces.
 */
public class RodCutting {

    public static int maxPieces(int[] rods, int L) {
        // Binary search range: minimum length L to maximum rod length
        int left = L;
        int right = 0;
        for (int rod : rods) right = Math.max(right, rod);

        int maxPieces = 0;

        while (left <= right) {
            int mid = left + (right - left) / 2;

            if (canCut(rods, mid, L)) {
                // Feasible: count pieces and try longer pieces
                maxPieces = countPieces(rods, mid);
                left = mid + 1;
            } else {
                // Not feasible: reduce piece length
                right = mid - 1;
            }
        }

        return maxPieces;
    }

    // Check if it's possible to cut all rods into pieces of length >= candidate
    private static boolean canCut(int[] rods, int candidateLength, int L) {
        if (candidateLength < L) return false; // must respect minimum length

        long totalPieces = 0;
        for (int rod : rods) {
            totalPieces += rod / candidateLength;
        }

        return totalPieces > 0;
    }

    // Count total pieces if each piece has length candidateLength
    private static int countPieces(int[] rods, int candidateLength) {
        int pieces = 0;
        for (int rod : rods) pieces += rod / candidateLength;
        return pieces;
    }

    // Example driver code
    public static void main(String[] args) {
        int[] rods = {4, 7, 9};
        int L = 3;

        System.out.println("Maximum pieces: " + maxPieces(rods, L));
        // Expected Output: 5
    }
}
