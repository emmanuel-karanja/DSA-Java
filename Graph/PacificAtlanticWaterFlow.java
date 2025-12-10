package Graph;

import java.util.ArrayList;
import java.util.List;

/**Given a grid that represents a map of an island. And the element at grid[i][j] represents the elevation at that point.
 * You are further told that the left and bottom represent the Atlantic Ocean and the top and right represent
 * the Pacific side. Find out from which cells water can flow into both oceans.
 * 
 * INTUITION:
 * DFS.
 * Do each area at a time, i.e. a grid representing pacific and one representing atlantic 
 * Find common cells marked as true in both;
 * 
 * KEY: we are checking whether the current cell’s height is greater than or equal to the previous cell’s height.
 * i.e.Can water from this cell to the other cell? 
*/

class Point{
    public int x;
    public int y;
    public Point(int x,int y){
        this.x=x;
        this.y=y;
    }
}
public class PacificAtlanticWaterFlow {

    public static List<Point> canFlowToBothOceans(int[][] board){

        if(board==null || board.length==0){
            throw new IllegalArgumentException("Grid cannot be empty or null.");
        }

        final int rows=board.length;
        final int cols=board[0].length;

        boolean[][] atlantic=new boolean[rows][cols];
        boolean[][] pacific=new boolean[rows][cols];

        //do a dfs from the edges where cols are const atlantic
    
       // Pacific: top row + left column
        for (int c = 0; c < cols; c++){
            dfs(board, 0, c, rows, cols, pacific);
        } 
        for (int r = 0; r < rows; r++){
             dfs(board, r, 0, rows, cols, pacific);
        }

        // Atlantic: bottom row + right column
        for (int c = 0; c < cols; c++){
            dfs(board, rows - 1, c, rows, cols, atlantic);
        } 
        for (int r = 0; r < rows; r++){
            dfs(board, r, cols - 1, rows, cols, atlantic);
        } 


        //board is now marked

        //iterate and reconvert
        List<Point> commonCells=new ArrayList<>();
        for(int r=0;r<rows;r++){
            for(int c=0;c<cols;c++){
                //cell
                if(pacific[r][c]&& atlantic[r][c]){
                    commonCells.add(new Point(r,c));
                }
            }
        }

        return commonCells;
    }

    private static void dfs(int[][] heights, int r,int c, int rows,int cols,boolean[][] ocean){

        if(ocean[r][c]) return;

        ocean[r][c]=true;
        final int[][] directions={{1,0},{0,1},{-1,0},{0,-1}};

         //visit neighbours
         for(int[] d: directions){
            int nr=r+d[0];
            int nc=c+d[1];

            //Can water flow from the this cell to the previous cell? i.e. currentHeight>=prevHeight
            if (nr >= 0 && nr < rows && nc >= 0 && nc < cols
                    && heights[nr][nc] >= heights[r][c]) {
                //visit
                dfs(heights, nr, nc,rows,cols, ocean);
            }
         }
    }

     public static void main(String[] args) {
        int[][] heights = {
            {1, 2, 2, 3, 5},
            {3, 2, 3, 4, 4},
            {2, 4, 5, 3, 1},
            {6, 7, 1, 4, 5},
            {5, 1, 1, 2, 4}
        };

        List<Point> result = PacificAtlanticWaterFlow.canFlowToBothOceans(heights);

        System.out.println("Cells where water can flow to both oceans:");
        for (Point p : result) {
            System.out.println("(" + p.x + ", " + p.y + ")");
        }
    }
}
