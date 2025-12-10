package Graph;

import java.util.HashSet;
import java.util.Set;


/**You are given a 2D grid of 0s and 1s. 0s represent water and 1s represent land.
 * An island is a group of 1s conntected horizontally or vertically.
 * 
 * Return the number of distinct islands where two islands are considered the same if they have the same shape
 * even if they are located in different places.
 * 
 * INTUTION: Use numIslands DFS but now, keep track of the direction we're moving and store the current path and
 *   record the unique path in a set. 
 */
public class DistinctIslandsCount {

    public static int getDistinctIslands(int[][] grid){
        //dimensions
        if(grid==null || grid.length==0){
            throw new IllegalArgumentException("Grid is empty");
        }

        final int rows=grid.length;
        final int cols=grid[0].length;

        //keep track of the shape we'll have S for start, R,L,U,D and B for backtrack
        Set<String> shapes=new HashSet<>();

        //do dfs

        for(int r=0;r<rows;r++){
            for(int c=0;c<cols;c++){
                //dfs if it's land
                if(grid[r][c]==1){
                    StringBuilder path=new StringBuilder();
                    dfs(r,c,'S',grid,rows,cols,path);
                    shapes.add(path.toString());
                }
            }
        }

        return shapes.size();

    }

    static void dfs(int r,int c, char direction, int[][] grid, int rows, int cols, StringBuilder path){
        // notice when doing early exit we use OR (||) 
        if(r<0 || r>=rows || c<0 || c>=cols || grid[r][c]==0){
            return;
        }

        grid[r][c]=0; //visit
       
        path.append(direction);  //String concat won't work since a concat is basically a new string with a different memory addres
        //so, use a builder.  Java passes simple values i.e. ints,longs,floats,chars, doubles by copying the value into
        //the argument itself. Objects are passed by value but the value of the reference(memory address).*A key note like this is the
        //main reason to write this code into Java and by hand.

        dfs(r+1,c,'D',grid,rows,cols,path);
        dfs(r-1,c,'U',grid,rows,cols,path);
        dfs(r,c+1,'R',grid,rows,cols,path);
        dfs(r,c-1,'L',grid,rows,cols,path);

        path.append('E');// mark end
        
    }

    public static void main(String[] args){
        int[][] testGrid={
            {1,1,0,0,0},
            {1,0,0,0,0},
            {0,0,0,1,1},
            {0,0,0,1,1}
        };

        System.out.println(getDistinctIslands(testGrid)); //expected is 2
    }
}
