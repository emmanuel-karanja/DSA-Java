package Backtracking;

import java.util.ArrayList;
import java.util.List;

/**
 * PROBLEM:
 * Given an array of integers nums and a target value, 
 * find all unique subsets of nums whose sum equals the target.
 *
 * EXAMPLE:
 * Input: nums = [1,2,3], target = 3
 * Output: [[1,2],[3]]
 *
 * APPROACH:
 * - Use backtracking to explore all subsets.
 * - Maintain a candidate list of selected numbers.
 * - Track the starting index to avoid reusing elements in the same subset.
 * - Calculate the sum of the candidate list at each step.
 *   - If sum == target, add a copy of candidate to solutions.
 *   - If sum > target, prune this branch.
 * - Recurse for each number starting from current index.
 * - Backtrack by removing the last added number before exploring next.
 */

public class TargetSumSubsets {

    public static List<List<Integer>> getTargetSum(int[] nums, int target) {
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
            return; // stop further recursion for this path
        }

        if (sum > target) return; // prune branches exceeding target

        for (int i = start; i < nums.length; i++) {
            candidate.add(nums[i]);
            backtrack(nums, i + 1, candidate, solutions, target); // next index
            candidate.remove(candidate.size() - 1); // backtrack
        }
    }

    // DRIVER MAIN
    public static void main(String[] args) {
        int[] nums = {1, 2, 3};
        int target = 3;
        List<List<Integer>> subsets = getTargetSum(nums, target);
        System.out.println("Subsets summing to " + target + ": " + subsets);
    }
}