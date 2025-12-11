package Graph;

import java.util.ArrayDeque;
import java.util.Queue;

/**Given a 2d binary grid that represents a map where 1 is  0 is water, find the shortest bridge that connectd the nearest
 * island
 * 
 * INTUTION:
 * 
 * Identify the first island using dfs
 * Expand using bfs to until you hit one clearly keeping track of the number of steps needed.
*/

public class ShortestBridgeBetweenIslands {

  private static void dfs(int[][] matrix, int r,int c, Queue<int[]> queue){
    int m=matrix.length;
    int n=matrix[0].length;

    if(r<0 || r>=m || c<0 || c>=n || matrix[r][c]!=1) return;
        //visit
        matrix[r][c]=2; //visit
        int[] cell={r,c,0}; //add steps
        queue.add(cell );

        dfs(matrix,r+1,c,queue);
        dfs(matrix, r,c+1,queue);
        dfs(matrix,r-1,c,queue);
        dfs(matrix,r,c-1,queue);
    
  }

  public static int getShortestBridge(int[][] matrix){
    if(matrix==null || matrix.length==0){
        throw new IllegalArgumentException("Grid is empty or null.");
    }

    //do a dfs to find the island
    boolean found=false;

    final int[][] directions={{1,0},{0,1},{-1,0},{0,-1}};
    
    Queue<int[]> queue=new ArrayDeque<>();
    for(int r=0;r<matrix.length;r++){
        if(found) break;
       for(int c=0;c<matrix[0].length;c++){
            if(matrix[r][c]==1){
               dfs(matrix, r, c, queue);
               found=true;
               break;
            }         
       }
    }

    // do a BFS to find the closest island
    
    while(!queue.isEmpty()){
        int[] cell=queue.poll();

        int r=cell[0];
        int c=cell[1];
        int steps=cell[2];

        for(int[] d:directions){
            int nr=r+d[0];
            int nc=c+d[1];

            if(nr<0 || nr>=matrix.length ||nc<0 || nc>=matrix[0].length) continue;

            if(matrix[nr][nc]==1){
                return steps;
            }

            if(matrix[nr][nc]==0){
                //visit
                matrix[nr][nc]=2;
                int[] newCell={nr,nc,steps+1};
                queue.add(newCell);
            }
           
        }
    }

    return -1; //fail
  }

  public static void main(String[] args) {
        int[][] grid = {
            {0, 1, 0},
            {0, 0, 0},
            {0, 0, 1}
        };

        System.out.println("Shortest Bridge Length: " + getShortestBridge(grid));
        // Expected output: 2
    }
    
}
