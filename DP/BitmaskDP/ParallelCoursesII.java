package BitmaskDP;

import java.util.*;

/**
 * ============================================================
 * PROBLEM: PARALLEL COURSES II
 * ============================================================
 *
 * Problem Statement:
 * -----------------
 * - n courses labeled 1..n
 * - relations[i] = [prevCourse, nextCourse] means prevCourse must be completed before nextCourse
 * - Each semester, you can take at most k courses
 * Goal:
 * - Minimum number of semesters required to complete all courses
 * 
 * 
 * REASONING
 * 
 * We will construct a  bitmask that tells us in shorthand what the prerequisites for a given course
 * are i.e. next-->[prerequisites] .We need it to help us in selecting the next available course.
 * 
 * At each step want to know:
 *  
 *   1. What courses are available to select from? These have two characteristics for each course we can add to
 *      available set.
 *        - They are not yet complete. Obviously.
 *        - Their prerequisites are already satisfied. A course is eligibl for doing if we have done all
 *          prerequisites of it.
 *   *At this step, to check that a course has its prequisites compltely covered we mask & courseToPreMap[i]
 *    and compare that it's equal to courseToPreMap[i], meaning that all the bits set in courseToPreMap[i]
 *    are set.
 *   2. From the available set, we ensure that we've not selected more than K courses for a given semester.
 *        - Select a course from the remaining group.
 *     
 *
 * Approach (Bitmask + DP + Topology):
 * ----------------------------------
 * 1. Represent completed courses as a bitmask `mask` of length n
 *    - bit i = 1 → course i is completed
 *
 * 2. Precompute prerequisites for each course as a bitmask:
 *    - pre[i] = bitmask of courses that must be completed before taking course i
 *
 * 3. DP State:
 *    - dp[mask] = minimum semesters to reach state `mask` (courses completed)
 *    - Base case: dp[0] = 0 (no courses taken)
 *
 * 4. Transition:
 *    - For each state `mask`:
 *        - Compute set of available courses: courses not in mask, all prerequisites satisfied
 *        - Enumerate all subsets of available courses of size <= k
 *        - For each subset, newMask = mask | subset
 *        - dp[newMask] = min(dp[newMask], dp[mask] + 1)
 *
 * 5. Result:
 *    - dp[fullMask], where fullMask = (1<<n) - 1
 *
 * Complexity:
 * -----------
 * - States: 2^n
 * - Subset enumeration: 2^availableCourses, but prune to size <= k
 * - Feasible for n <= 15–16
 *
 */
public class ParallelCoursesII {

    public static int minNumberOfSemesters(int n, int[][] relations, int k) {
        int fullMask = (1 << n) - 1;

        // 1. Precompute prerequisites bitmask for each course
        int[] courseToPreMap = new int[n];
        for (int[] rel : relations) {
            int prev = rel[0] - 1;  // 0 based index conversion
            int next = rel[1] - 1;
            courseToPreMap[next] |= (1 << prev);
        }

        int[] dp = new int[1 << n];
        Arrays.fill(dp, Integer.MAX_VALUE);
        dp[0] = 0;

        for (int mask = 0; mask <= fullMask; mask++) {
            if (dp[mask] == Integer.MAX_VALUE) continue;  // We have not assigned any courses here at all.

            // Compute courses not in mask that also have all prerequisites satisfied:
            int available = 0;
            for (int i = 0; i < n; i++) {
                // mask & pre[i] which prerequisites of course i are already done.
                // (mask & courseToPreMap[i])=courseToPreMap means “All courses that are prerequisites for course i have been completed.”
                if ((mask & (1 << i)) == 0 && (mask & courseToPreMap[i]) == courseToPreMap[i]) {
                    // Basically add course i to available courses
                    available |= (1 << i);
                }
            }

            // Pick a course from the available courses
             // (sub-1) & mask is "what remains after selecting sub"
            for (int sub = available; sub > 0; sub = (sub - 1) & available) {
                if (Integer.bitCount(sub) <= k) { // Count of courses in the mask
                    int newMask = mask | sub;
                    dp[newMask] = Math.min(dp[newMask], dp[mask] + 1);
                }
            }
        }

        return dp[fullMask];
    }

    public static void main(String[] args) {
        int n = 4;
        int[][] relations = {{2,1},{3,1},{1,4}};
        int k = 2;
        System.out.println("Minimum semesters: " + minNumberOfSemesters(n, relations, k));
        // Output: 3
    }
}