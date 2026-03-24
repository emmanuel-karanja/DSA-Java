package Backtracking;

import java.util.*;

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
 * APPROACH (Canonical Backtracking + Memoization):
 * - Maintain a candidate list representing the current combination.
 * - Track a stateMarker representing the sum so far (can encode additional info if needed).
 * - Use a memo map to store previously explored states to avoid redundant work.
 * - Prune branches if sum(candidate) > target.
 * - For each number starting from current index:
 *      - Add number to candidate.
 *      - Update stateMarker (sum so far).
 *      - Recurse with the same index (allowing reuse of the same number).
 *      - Backtrack by removing the last number.
 */

public class CombinationSumMemo {

    public static List<List<Integer>> combinationSum(int[] nums, int target) {
        List<List<Integer>> solutions = new ArrayList<>();
        Map<Integer, List<List<Integer>>> memo = new HashMap<>();
        backtrack(nums, 0, new ArrayList<>(), solutions, memo, 0, target);
        return solutions;
    }

    private static void backtrack(int[] nums, int start, List<Integer> candidate,
                                  List<List<Integer>> solutions,
                                  Map<Integer, List<List<Integer>>> memo,
                                  int sumSoFar, int target) {

        // 1. Check memo
        if (memo.containsKey(sumSoFar)) {
            // Already explored this sum, skip
            return;
        }

        // 2️.Check if candidate is a solution
        if (sumSoFar == target) {
            solutions.add(new ArrayList<>(candidate));
            memo.put(sumSoFar, new ArrayList<>(solutions)); // store current solutions at this state
            return;
        }

        // 3️. Prune if candidate sum exceeds target
        if (sumSoFar > target) return;

        // 4️. Explore choices
        for (int i = start; i < nums.length; i++) {
            candidate.add(nums[i]);
            int newSum = sumSoFar + nums[i]; // update stateMarker
            backtrack(nums, i, candidate, solutions, memo, newSum, target); // reuse same number
            candidate.remove(candidate.size() - 1); // backtrack
        }

        // 5️. Store this state in memo (optional if pruning is expensive)
        memo.put(sumSoFar, new ArrayList<>(solutions));
    }

    // DRIVER MAIN
    public static void main(String[] args) {
        int[] nums = {2, 3, 6, 7};
        int target = 7;
        List<List<Integer>> combinations = combinationSum(nums, target);
        System.out.println("Combinations summing to " + target + ": " + combinations);
    }
}