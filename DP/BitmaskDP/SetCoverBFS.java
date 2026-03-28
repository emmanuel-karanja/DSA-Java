import java.util.*;

/**
 * SET COVER VIA BFS (STATE-SPACE SHORTEST PATH)
 *
 * Key Idea:
 * We model Set Cover as a shortest path problem over a state-space graph of bitmasks.
 *
 * GRAPH MAPPING:
 * - Node: A bitmask representing which elements are currently covered
 *         (0 → nothing covered, (1 << n) - 1 → everything covered)
 *
 * - Edge: From mask_A to mask_B if adding a subset transitions:
 *         mask_B = mask_A | subsetMask
 *
 * - Start: 0 (empty set)
 * - Target: FULL_MASK = (1 << n) - 1
 *
 * Since each subset contributes a uniform cost of 1,
 * this becomes an UNWEIGHTED SHORTEST PATH problem → solved optimally using BFS.
 *
 * WHY BFS WORKS:
 * - BFS explores states layer-by-layer
 * - Each layer corresponds to "number of subsets used"
 * - The first time we reach FULL_MASK, we have the minimum number of subsets
 *
 * BFS vs BITMASK DP:
 *
 * - Both have worst-case complexity: O(2^N * M)
 * - However, BFS has a key advantage:
 *
 *   EARLY EXIT:
 *   If the optimal solution uses k subsets, BFS only explores up to depth k,
 *   potentially visiting far fewer than 2^N states.
 *
 *   DP always iterates over all 2^N states regardless of solution depth.
 *
 * MEMORY:
 * - Both BFS and DP use O(2^N) space in the worst case
 * - BFS may use less in practice if many states are never reached
 *
 * WHEN TO USE WHAT:
 *
 * - Use BFS:
 *   * When minimizing number of subsets (uniform cost)
 *   * When solution depth is expected to be small
 *
 * - Use Dijkstra:
 *   * When subsets have different costs
 *
 * - Use Bitmask DP:
 *   * When you must iterate over all states
 *   * When submask enumeration is required (O(3^N) patterns)
 *
 * COMPLEXITY:
 * - Time: O(2^N * M) worst case
 * - Space: O(2^N)
 *
 * Interview Insight:
 * “This is a shortest path problem over bitmask states. Since all transitions
 *  have equal cost, BFS gives the minimum number of subsets with early termination.”
 */

public class SetCoverBFS {

    public static int minSetCover(int[] subsets, int n) {
        int fullMask = (1 << n) - 1;

        Queue<Integer> queue = new ArrayDeque<>();
        boolean[] visited = new boolean[1 << n];

        queue.offer(0);
        visited[0] = true;

        int steps = 0;

        while (!queue.isEmpty()) {
            int size = queue.size();

            // Process all states at current "distance" (same number of subsets used)
            for (int i = 0; i < size; i++) {
                int mask = queue.poll();

                if (mask == fullMask) return steps;

                for (int subset : subsets) {
                    int nextMask = mask | subset;

                    if (!visited[nextMask]) {
                        visited[nextMask] = true;
                        queue.offer(nextMask);
                    }
                }
            }

            steps++; // move to next layer (use one more subset)
        }

        return -1; // no solution
    }

    // helper: convert list of elements → bitmask
    public static int toMask(int[] elements) {
        int mask = 0;
        for (int e : elements) {
            mask |= (1 << e);
        }
        return mask;
    }

    public static void main(String[] args) {
        int n = 4;

        int[] subsets = new int[] {
            toMask(new int[]{0, 1}),
            toMask(new int[]{1, 2}),
            toMask(new int[]{2, 3}),
            toMask(new int[]{0, 3})
        };

        System.out.println(minSetCover(subsets, n)); // expected: 2
    }
}