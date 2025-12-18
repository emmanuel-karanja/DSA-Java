
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