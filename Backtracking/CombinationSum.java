package Backtracking;

import java.util.ArrayList;
import java.util.List;

/**
 * PROBLEM:
 * Given an array of distinct integers nums and a target integer target,
 * return all unique combinations of nums where the chosen numbers sum to target.
 *
 * - Each number in nums may be chosen an unlimited number of times.
 * - Two combinations are considered the same if they have the same frequency of chosen numbers.
 * - Combinations can be returned in any order.
 *
 * EXAMPLE:
 * Input: nums = [2,3,6,7], target = 7
 * Output: [[2,2,3],[7]]
 *
 * APPROACH:
 * - Use backtracking to explore all combinations.
 * - Maintain a candidate list representing the current combination.
 * - Track a starting index to avoid revisiting numbers before it.
 * - At each step:
 *     - If sum(candidate) == target, add a copy of candidate to solutions.
 *     - If sum(candidate) > target, prune this branch.
 *     - Otherwise, for each number starting from 'start':
 *         - Add number to candidate.
 *         - Recurse using the same index 'i' (to allow reuse of the same number).
 *         - Backtrack by removing the last number.
 */

public class CombinationSum {

    public static List<List<Integer>> combinationSum(int[] nums, int target) {
        List<List<Integer>> result = new ArrayList<>();

        if(nums==null || nums.length==0) return result;
        backtrack(nums, 0, new ArrayList<>(), result, target);
        return result;
    }

    private static void backtrack(int[] nums, int start, List<Integer> candidate, List<List<Integer>> solutions, int target) {
        int sum = 0;
        for (int num : candidate) sum += num;

        if (sum == target) {
            solutions.add(new ArrayList<>(candidate));
            return;
        }

        if (sum > target) return;

        for (int i = start; i < nums.length; i++) {
            candidate.add(nums[i]);
            backtrack(nums, i, candidate, solutions, target); // reuse same number
            candidate.remove(candidate.size() - 1); // backtrack
        }
    }

    // DRIVER MAIN
    public static void main(String[] args) {
        int[] nums = {2, 3, 6, 7};
        int target = 7;
        List<List<Integer>> combinations = combinationSum(nums, target);
        System.out.println("Combinations summing to " + target + ": " + combinations);
    }
}