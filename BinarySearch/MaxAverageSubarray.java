package BinarySearch;

/*
======================================================================
PROBLEM: MAXIMUM AVERAGE SUBARRAY II (Binary Search + Prefix Sums)
======================================================================

Problem Statement
-----------------
Given an integer array nums and an integer k, find the maximum possible
average of any subarray with length >= k.

Return the maximum average with precision up to 1e-5.

Example:
nums = [1,12,-5,-6,50,3], k = 4

Subarrays of length >= 4:
[1,12,-5,-6]      avg = 0.5
[12,-5,-6,50]     avg = 12.75
[-5,-6,50,3]      avg = 10.5
[1,12,-5,-6,50]   avg = 10.4
[12,-5,-6,50,3]   avg = 10.8
[1,12,-5,-6,50,3] avg = 9.17

Answer: 12.75

----------------------------------------------------------------------
Reasoning
----------------------------------------------------------------------

We are asked to maximize an average.

Classic signal for:
    Binary Search Over Answer (BSOA)

Let candidate average = mid.

Transform the problem:

avg(subarray) >= mid
(sum(subarray) / len) >= mid
sum(subarray) >= mid * len

Rearrange:

sum(nums[i] - mid) >= 0

So we transform the array:

A[i] = nums[i] - mid

Now the problem becomes:

"Is there a subarray of length >= k whose sum >= 0?"

We solve this using prefix sums.

Let:
prefix[i] = sum of A[0..i-1]

For subarray [j..i]:
sum = prefix[i] - prefix[j]

We need:

prefix[i] - prefix[j] >= 0
=> prefix[i] >= prefix[j]

while maintaining:
i - j >= k

So while scanning:
- maintain the minimum prefix seen k positions behind
- check if current prefix - minPrefix >= 0

If yes → feasible(mid)

Then we binary search the answer.

----------------------------------------------------------------------
Time Complexity
----------------------------------------------------------------------

Binary search iterations ≈ 50
Each feasibility check = O(n)

Total:
O(n log precision)

Space:
O(1)

======================================================================
*/


public class MaxAverageSubarray {

    public static double findMaxAverage(int[] nums, int k) {

        double lo = Integer.MAX_VALUE;
        double hi = Integer.MIN_VALUE;

        for (int x : nums) {
            lo = Math.min(lo, x);
            hi = Math.max(hi, x);
        }

        while (hi - lo > 1e-5) {

            double mid =lo+ (hi-lo) / 2;

            if (canFind(nums, k, mid))
                lo = mid;
            else
                hi = mid;
        }

        return lo;
    }

    private static boolean canFind(int[] nums, int k, double mid) {

        int n = nums.length;

        double sum = 0;
        double prev = 0;
        double minPrev = 0;

        // Treat the first part of the array as a start subarray
        for (int i = 0; i < k; i++)
            sum += nums[i] - mid;

        if (sum >= 0)
            return true;

        // Start with size k and generate the sum
        for (int i = k; i < n; i++) {

            sum += nums[i] - mid;   //ps[i]
            prev += nums[i - k] - mid; // ps[j]

            // Takes care of it reducing due to a negative number being encountered.
            minPrev = Math.min(minPrev, prev);

            if (sum - minPrev >= 0)
                return true;
        }

        return false;
    }

    public static void main(String[] args) {

        int[] nums = {1,12,-5,-6,50,3};
        int k = 4;

        double ans = findMaxAverage(nums, k);

        System.out.println("Maximum average: " + ans);
    }
}