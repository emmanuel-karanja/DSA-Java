package Arrays;

/*
============================================================
PROBLEM: LONGEST SUBARRAY WITH MOVING AVERAGE >= TARGET
============================================================

Problem Statement
-----------------
You are given:
    - An integer array nums
    - An integer k (window size for moving average)
    - A target value T

Define the moving average of a subarray of length >= k as the
average of every contiguous window of size k inside that subarray.

Example:
nums = [2, 1, 3, 4, 5], k = 2

windows:
[2,1] -> 1.5
[1,3] -> 2
[3,4] -> 3.5
[4,5] -> 4.5

A subarray is VALID if every k-window inside it has average >= T.

Return the length of the longest valid subarray.

Example
-------
nums = [2,1,3,4,5], k = 2, T = 2

window averages:
[2,1] -> 1.5 (bad)
[1,3] -> 2
[3,4] -> 3.5
[4,5] -> 4.5

Longest valid subarray:
[1,3,4,5] -> length = 4

Return 4.

Constraints
-----------
1 <= nums.length <= 10^5
1 <= nums[i] <= 10^5
1 <= k <= nums.length

------------------------------------------------------------
Reasoning
------------------------------------------------------------

Observation:
avg(window) >= T
(sum(window) / k) >= T
sum(window) >= T * k

So we convert the condition to a sum constraint.

Steps:

1. Precompute k-window sums using sliding window
   This produces an array W of size (n-k+1)

2. Each element W[i] represents the window starting at i

3. We want the longest segment where all window sums >= T*k

4. A window i affects the subarray [i, i+k-1]

5. Therefore a valid subarray must only include windows
   whose sums satisfy the constraint.

We can scan through W and find the longest consecutive region
where W[i] >= T*k.

Length mapping:
if window indices range from L..R

subarray length = (R - L) + k

Time Complexity
---------------
Sliding window: O(n)
Scan windows: O(n)

Total: O(n)

Space: O(n)

============================================================
*/


public class MovingAverageSubarray {

    public static int longestValidSubarray(int[] nums, int k, int T) {

        int n = nums.length;
        if (n < k) return 0;

        long target = (long)T * k;

        long[] windowSums = new long[n - k + 1];

        long sum = 0;

        for (int i = 0; i < n; i++) {
            sum += nums[i];

            
            if (i >= k)
                sum -= nums[i - k];

            // We have a valid window
            if (i >= k - 1)
                windowSums[i - k + 1] = sum;
        }

        int best = 0;
        int current = 0;

        for (long w : windowSums) {
            if (w >= target) {
                current++;
                best = Math.max(best, current);
            } else {
                current = 0;
            }
        }

        if (best == 0) return 0;

        return best + k - 1;
    }


    public static void main(String[] args) {

        int[] nums = {2,1,3,4,5};
        int k = 2;
        int T = 2;

        int ans = longestValidSubarray(nums, k, T);

        System.out.println("Longest valid subarray length: " + ans);
    }
}