package DP;
/**Given an mxn grid and a robot starting at position 0,0, the robot can either move up, down or right or left
 * at point. How many unique paths can it use to get to the bottom.
 * 
 * INTUITION:
 * 
 * You hear how many  and you know this is a recursive problem since to get to 0,0, we can follow from the one on the left
 * or the one bottom.  Since the robot can't move diagonally.
 * 
 * The recurrence: uniqueWays(m-1,n)+uniqueWays(m,n-1) i.e. it can only 
 * 
 * the base case is: m==1 or n==1, then only 1 way.
 */
public class UniquePaths {

    //Time O(2^(m+n)) why? You are calling the recursive function twice in each iteration
    public static int uniqueWaysRecursive(int m, int n){
        if(m==1 || n==1) return 1;

        return uniqueWaysRecursive(m-1,n)+uniqueWaysRecursive(m, n-1);
    }

    //Time O(m*n)
    public static int uniqueWaysDP(int m, int n){
        int[][] dp=new int[m][n]; //it will be filled with0

        for(int i=1;i<m;i++){
            for(int j=1;j<n;j++){
                dp[i][j]=dp[i-1][j]+dp[i][j-1];
            }
        }

        return dp[m-1][n-1];
    }

    public static void main(String[] args) {
        int m = 3;
        int n = 7;

        System.out.println("Grid size: " + m + " x " + n);

        int recursiveResult = UniquePaths.uniqueWaysRecursive(m, n);
        System.out.println("Unique paths (Recursive): " + recursiveResult);

        int dpResult = UniquePaths.uniqueWaysDP(m, n);
        System.out.println("Unique paths (DP): " + dpResult);
    }
    
}
