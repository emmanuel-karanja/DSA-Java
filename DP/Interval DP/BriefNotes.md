
18.12.2025 at 0947 hrs

Interval DP where the key decision is choosing a pivot inside an interval, and the problem splits into two 
independent subintervals.

For interval dps, there are several common characteristics:


1. Dp[i][j] means the state between i and j.
2. WE usually find ourselves starting from the end since the neighbours are known.
3. The final solution is taken considering what DP[i][j] means i.e. from i to j and hence you'll find that
   it's something like DP[0][n-1]



Interval Independence
 Once you fix a decision at index k, the left and right sides:
   1. Never interact again
   2. Can be solved optimally on their own
   3. Combine without conflict

This is stronger than standard DP — it’s structural decomposition.

16.12.2025 at 14xx hrs

## INTERVAL DP – GENERIC RUBRIC

First thing.

Interval Dynamic Programming is just brute force with memory over contiguous ranges,
where you carefully define what you’re remembering for each interval.

Interval DP is structured brute force over the solution space of intervals,
where overlapping sub-intervals are solved once and remembered.

This class of problems appears when:
- The problem operates on a contiguous range (subarray / substring / segment).
- Making ONE decision inside the range splits the problem into two independent ranges.

Examples:
- Cutting a Stick
- Burst Balloons
- Longest Palindromic Subsequence
- Matrix Chain Multiplication

We follow the same order every time.

1. GOAL

Identify what we are optimizing over an interval.

DP problems only ask for a quantifiable aggregate:
min cost, max value, max length, etc.

Examples:
- Minimum cost to fully cut a stick
- Maximum coins from bursting balloons
- Length of the longest palindromic subsequence

The goal immediately tells us what the DP state must store.

2. STATE

Define a state that uniquely represents a sub-interval and directly stores the
quantity being optimized.

dp[l][r] = the optimal answer for operating only inside interval [l, r]

Plain English:
“What is the best possible result if I only consider elements between l and r?”

This state fully captures the subproblem.

3. CHOICES / DECISIONS

At interval [l, r], we must choose ONE pivot k such that:

l < k < r

This choice represents:
- the first cut (Cut Stick)
- the last balloon to burst (Burst Balloons)
- the boundary decision (LPS)

The key property:
Once k is fixed, the problem splits into two independent subproblems:
- left interval [l, k]
- right interval [k, r]

This independence is what makes DP valid.

4. RECURRENCE RELATION

The recurrence is always:

dp[l][r] = best over all valid k:
    dp[l][k] + dp[k][r] + local_cost(l, k, r)

Where:
- dp[l][k] solves the left interval optimally
- dp[k][r] solves the right interval optimally
- local_cost depends on the problem

Examples of local_cost:
- (r - l) for Cutting Stick
- nums[l] * nums[k] * nums[r] for Burst Balloons
- +2 for matching characters in LPS

This is structured brute force over all possible pivots.

5. BASE CASES

The simplest subprobl
