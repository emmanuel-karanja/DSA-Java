package Arrays;

import java.util.*;
/**
 * Yes, extending from 4-Sum to 5-Sum adds one more loop, making it O(n⁴).
In general, k-Sum is solved with k−2 loops plus a two-pointer base case, giving O(n^{k−1}). For scalability, I’d implement it recursively.”
 * 
 * kSum(nums, start, k, target):
    if k == 2:
        solve with two pointers
    else:
        for i from start to n:
            skip duplicates
            recurse kSum(i+1, k-1, target - nums[i])
 */
public class KSum {

    public static List<List<Integer>> kSum(int[] nums, int target, int k) {
        List<List<Integer>> result = new ArrayList<>();
        if (nums == null || nums.length < k) return result;

        Arrays.sort(nums);

        kSumHelper(nums, target, k, 0, new ArrayList<>(), result);
        return result;
    }

    private static void kSumHelper(
            int[] nums,
            int target,
            int k,
            int start,
            List<Integer> path,
            List<List<Integer>> result
    ) {

        int n = nums.length;

        // Base case: 2-Sum
        if (k == 2) {
            int left = start, right = n - 1;

            while (left < right) {
                int sum = nums[left] + nums[right];

                if (sum == target) {
                    List<Integer> combination = new ArrayList<>(path);
                    combination.add(nums[left]);
                    combination.add(nums[right]);
                    result.add(combination);

                    // skip duplicates
                    while (left < right && nums[left] == nums[left + 1]) left++;
                    while (left < right && nums[right] == nums[right - 1]) right--;

                    left++;
                    right--;
                } else if (sum < target) {
                    left++;
                } else {
                    right--;
                }
            }
            return;
        }

        // Recursive case: k > 2
        for (int i = start; i <= n - k; i++) {

            // skip duplicates
            if (i > start && nums[i] == nums[i - 1]) continue;

            // pruning (important optimization)
            long min = (long) nums[i] + (long) nums[i + 1] * (k - 1);
            long max = (long) nums[i] + (long) nums[n - 1] * (k - 1);
            if (min > target) break;
            if (max < target) continue;

            path.add(nums[i]);

            kSumHelper(nums, target - nums[i], k - 1, i + 1, path, result);
            //backtrack
            path.remove(path.size() - 1);
        }
    }

    public static void main(String[] args) {

        int[] nums = {1, 0, -1, 0, -2, 2};
        int target = 0;

        // ---- 4-Sum ----
        List<List<Integer>> fourSum = KSum.kSum(nums, target, 4);
        System.out.println("4-Sum results:");
        printResult(fourSum);

        // ---- 3-Sum ----
        List<List<Integer>> threeSum = KSum.kSum(nums, 0, 3);
        System.out.println("\n3-Sum results:");
        printResult(threeSum);

        // ---- 5-Sum (example) ----
        int[] nums5 = {2, -1, 0, 1, -2, 2, -1};
        List<List<Integer>> fiveSum = KSum.kSum(nums5, 2, 5);
        System.out.println("\n5-Sum results:");
        printResult(fiveSum);
    }

    private static void printResult(List<List<Integer>> result) {
        if (result.isEmpty()) {
            System.out.println("No combinations found.");
            return;
        }

        for (List<Integer> combo : result) {
            System.out.println(combo);
        }
    }
}
