package DP;

/**Given an array of positive integers, determine if it can be partitioned into two subsets whose sumsare equal.
 * 
 * INTUTION:
 * 
 * If their sum is odd, we can't do that.
 * 
 FIRST THING — GOAL
------------------
Determine whether it is possible to split the given array into two subsets
with equal sum.

This is a YES / NO question.
Dynamic Programming problems ask for quantifiable aggregates:
"is it possible", "how many ways", "min", "max".
We are NOT asked to list subsets or permutations.

Let totalSum = sum(nums).
If totalSum is odd, the goal is impossible.
Otherwise, the problem reduces to:

GOAL:
Is it possible to form a subset whose sum equals target = totalSum / 2?


STATE
-----
We define a DP state that directly stores the goal.

dp[s] = true if it is possible to form sum s using SOME of the numbers
we have processed so far.
dp[s] = false otherwise.

This state uniquely represents a subproblem:
"Can I make sum s with a prefix of the array?"


CHOICES / DECISIONS
-------------------
For each number num in nums, we make exactly two decisions:

1) Take num → this contributes to forming sum s
2) Do NOT take num → sum s remains unchanged

These are the only valid choices.
There is no greedy dominance: taking a number early does not dominate
all future decisions.


RECURRENCE RELATION
-------------------
To form sum s after seeing num:

dp[s] = dp[s] OR dp[s - num]

Meaning:
- Either sum s was already achievable without num
- Or we can achieve s by taking num, if s - num was achievable before

This is structured brute force:
we explore all valid include/exclude paths but remember results.


BASE CASE
---------
dp[0] = true

Why?
A sum of 0 is always achievable by choosing nothing.
This is the smallest stable sub-solution.


EVALUATION ORDER RULE
---------------------
Each dp[s] must depend only on states from the PREVIOUS iteration.

To enforce this:
- We iterate s from target DOWN TO num.

This prevents reusing the same number multiple times.
We start from the "end" because stable subsolutions live behind us.


SOLUTION
--------
After processing all numbers:
If dp[target] is true, the array can be partitioned into two equal subsets.
 */
public class PartitionEqualSubsets {

    public static boolean canPartition(int[] nums){
        if(nums==null || nums.length==0) {
            return false;
        }

        int sum=0;
        for(int e: nums){
            sum+=e;
        }

        if(sum % 2!=0) return false;

        final int target=sum/2; 

        boolean[] dp=new boolean[target+1]; // fills to false

        for(int num: nums){
            for(int i=target; i >=num;i--){
                dp[i]=dp[i] || dp[i-num];
            }
        }
        
        return dp[target];
    }
    
}

