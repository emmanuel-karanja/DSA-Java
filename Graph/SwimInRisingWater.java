package Graph;

/*You are given a nxm grid of integers, where each integer represents the elevation in that cell.
 Water rises with time t and at timet, you can only enter cells with elevation <=t. Starting from
 top-left (0,0), you want to reach the bottom-right (n-1,m-1) in the minimum possible time.
 INTUITION:
 Think of this as finding a path from (0,0) to the (n-1,m-1).
 The cost of stepping into a cell is its elevation (you can only step when water>=elevation).
 We want to minimize the maximum elevation encountered along the path.
 
 DJIKSTRA'S algorithm always expand the cell with the lowest elevation.
 
 This is a weighted graph really because moving to each of the neighbours doesn't have the same
 cost. i.e. the elevation is the cost. 
 
 KEY: The elevation for the cell is the time for that cell. 
 */

import java.util.PriorityQueue;


  class State{
    public int x;
    public int y;
    public int elevation;

    public State(int elevation, int x, int y){
        this.elevation=elevation;
        this.x=x;
        this.y=y;
    }
}

public class SwimInRisingWater {


    public int swim(int [][] grid){

       if(grid == null || grid.length==0) return -1;

        int m = grid.length;
        int n=grid[0].length;

        int[][] directions={{1,0},{0,1},{-1,0},{0,-1}};
        //use the minHeap for this to find find the minimum elevation
        PriorityQueue<State> minHeap=new PriorityQueue<>( (a, b) -> Integer.compare(a.elevation, b.elevation));

        //keep track of visited nodes, by default boolean is false
        boolean [][] visited =new boolean[m][n];

        visited[0][0]=true;

        minHeap.offer(new State(grid[0][0],0,0));

        //BFS
        int time=Integer.MIN_VALUE;

        while(!minHeap.isEmpty()){
                State curr=minHeap.poll();

                int elevation=curr.elevation;  //we don't even need to store the elevation since we can get it from the grid
                //and we are not doing any thing to it.
                int x=curr.x;
                int y=curr.y;

                time=Math.max(time,elevation);


                //if we reach the end return the time
                if(x==n-1 && y==m-1){
                    return time;
                }

                //explore neighbours

                for(int[] d : directions){
                    int nx=x+d[0];
                    int ny=y+d[1];

                    //check if the new cell is within bounds
                    if(nx < 0 || nx >= m || ny<0 || ny >= n || visited[nx][ny]) continue;

                    //mark as visited
                    visited[nx][ny]=true;
                        //we just get its elevation from the grid
                     minHeap.add(new State(grid[nx][ny],nx,ny));
            
                }

        }

        return -1; //not found

    }  
    public static void main(String [] args){

        int[][] grid1 = {
                    {0, 2},
                    {1, 3}
            };
        System.out.println("Min time (example 1): " + new SwimInRisingWater().swim(grid1));
        int[][] grid2 = {
                    {0, 1, 2, 3, 4},
                    {24, 23, 22, 21, 5},
                    {12, 13, 14, 15, 16},
                    {11, 17, 18, 19, 20},
                    {10, 9, 8, 7, 6}
            };
            System.out.println("Min time (example 2): " + new SwimInRisingWater().swim(grid2));

        int[][] grid3 = {
                    {5, 4, 6},
                    {7, 3, 2},
                    {8, 9, 1}
            };
            System.out.println("Min time (example 3): " + new SwimInRisingWater().swim(grid3));
    }
}
