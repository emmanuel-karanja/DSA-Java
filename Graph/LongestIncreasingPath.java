package Graph;

//*Find the longest increasing path in an integer matrix. 
// INTUTION:
//  Use DFS at each point and keep track of the max path length. 
//  Use memoization to prevent doing the same thing over and over again. */
public class LongestIncreasingPath {
    public static int getLongestPath(int[][] matrix){
        final int m=matrix.length;
        final int n=matrix[0].length;

    

        int[][] memo=new int[m][n];

        int maxLength=0;
        for(int i=0;i<m;i++){
            for(int j=0;j<n;j++){
                maxLength=Math.max(maxLength,dfs(i,j,matrix,memo,m,n));
            }
        }

        return maxLength;

    }

    private static int dfs(int x, int y, int[][] matrix,int[][] memo ,int m,int n){
        final int[][] directions={{1,0},{0,1},{-1,0},{0,-1}};

        if(memo[x][y]!=0) return memo[x][y];
        
        int maxLength=0;

        for(int[] d: directions){
            int nx=x+d[0];
            int ny=y+d[1];

            if(nx>=0 && nx< m && ny >=0 && ny<n && matrix[nx][ny]>matrix[x][y]){
                maxLength=Math.max(maxLength,1+dfs(nx, ny, matrix, memo, m, n));
            }
        }

        memo[x][y]=maxLength; //memoize 

        return maxLength;
        
    }
}
