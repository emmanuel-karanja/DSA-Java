package Backtracking;

import java.util.*;

/**
 * PROBLEM:
 * Given an array of integers nums (may have duplicates) and a target integer target,
 * return all unique combinations of nums where the chosen numbers sum to target.
 *
 * - Each number in nums may be chosen an unlimited number of times.
 * - Two combinations are considered the same if they have the same frequency of chosen numbers.
 * - Combinations can be returned in any order.
 *
 * APPROACH:
 * - Use backtracking to explore all combinations.
 * - Maintain a candidate list representing the current combination.
 * - Use a Set to store combinations in sorted order for uniqueness.
 * - At each step:
 *      - If sum(candidate) == target, add a sorted copy to the Set.
 *      - If sum(candidate) > target, prune this branch.
 *      - Otherwise, explore all numbers starting from current index (reuse allowed).
 */

public class CombinationSumWithSet {

    public static List<List<Integer>> combinationSum(int[] nums, int target) {
        Set<List<Integer>> uniqueSolutions = new HashSet<>();
        backtrack(nums, 0, new ArrayList<>(), uniqueSolutions, 0, target);
        return new ArrayList<>(uniqueSolutions);
    }

    private static void backtrack(int[] nums, int start, List<Integer> candidate,
                                  Set<List<Integer>> uniqueSolutions,
                                  int sumSoFar, int target) {

        // Base cases
        if (sumSoFar == target) {
            List<Integer> sortedCandidate = new ArrayList<>(candidate);
            Collections.sort(sortedCandidate); // ensure uniqueness
            uniqueSolutions.add(sortedCandidate);
            return;
        }

        if (sumSoFar > target) return; // prune

        // Explore choices
        for (int i = start; i < nums.length; i++) {
            candidate.add(nums[i]);
            backtrack(nums, i, candidate, uniqueSolutions, sumSoFar + nums[i], target); // reuse same number
            candidate.remove(candidate.size() - 1); // backtrack
        }
    }

    // DRIVER MAIN
    public static void main(String[] args) {
        int[] nums = {2, 3, 2, 6, 7}; // duplicates allowed
        int target = 7;
        List<List<Integer>> combinations = combinationSum(nums, target);
        System.out.println("Unique combinations summing to " + target + ": " + combinations);
    }
}