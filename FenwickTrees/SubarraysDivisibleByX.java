package FenwickTrees;

import java.util.HashMap;
/**
     * PROBLEM:
     * Given an array of integers nums and an integer X, count the number of subarrays 
     * whose sum is divisible by X.
     *
     * EXAMPLE:
     * nums = [4, 5, 0, -2, -3, 1], X = 5
     * Output: 7
     * Explanation: The subarrays are:
     * [4, 5, 0, -2, -3, 1], [5], [5, 0], [5, 0, -2, -3], [0], [0, -2, -3], [-2, -3]
     *
     * REASONING:
     * Let prefixSum[i] = sum of first i elements (0-based indexing)
     * We want sum(nums[i..j]) % X == 0
     * => (prefixSum[j+1] - prefixSum[i]) % X == 0
     * => prefixSum[j+1] % X == prefixSum[i] % X
     * 
     * So, we can count the frequency of prefixSum % X values and for each value with frequency f,
     * it contributes f * (f-1)/2 valid subarrays. Also, we initialize count of 0 remainder as 1
     * to handle subarrays starting from index 0.
     */
public class SubarraysDivisibleByX {

    

    public static int countSubarraysDivisibleByX(int[] nums, int X) {
        // Map to store frequency of prefix sum remainders
        HashMap<Integer, Integer> remainderFreq = new HashMap<>();
        remainderFreq.put(0, 1); // empty prefix sum counts as remainder 0

        int prefixSum = 0;
        int count = 0;

        for (int num : nums) {
            prefixSum += num;
            int remainder = ((prefixSum % X) + X) % X; // handle negative numbers

            count += remainderFreq.getOrDefault(remainder, 0);
            remainderFreq.put(remainder, remainderFreq.getOrDefault(remainder, 0) + 1);
        }

        return count;
    }

    // DRIVER MAIN
    public static void main(String[] args) {
        int[] nums1 = {4, 5, 0, -2, -3, 1};
        int X1 = 5;
        System.out.println("Number of subarrays divisible by " + X1 + " is: " +
                countSubarraysDivisibleByX(nums1, X1));

        int[] nums2 = {1, 2, 3, 4, 5};
        int X2 = 3;
        System.out.println("Number of subarrays divisible by " + X2 + " is: " +
                countSubarraysDivisibleByX(nums2, X2));

        int[] nums3 = {-1, 2, 9};
        int X3 = 2;
        System.out.println("Number of subarrays divisible by " + X3 + " is: " +
                countSubarraysDivisibleByX(nums3, X3));
    }
}