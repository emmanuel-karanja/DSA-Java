package Graph;

import java.util.ArrayDeque;
import java.util.Queue;

/**You are given a grid where a 2 represents a rotten orange and 1 represents a fresh orange and 0 an empty cell. 
 * You are asked to calculate
 * the minimum number of minutes if it's possible to rot all the oranges. A rotten orange can only rot an orange in the four directions
 * top,left,right, and bottom
 * 
 * INTUTION: BFS starting from the top cell. And then keep track of the times so far, and decrement fresh orange count.
 */

class Cell{
    public int time;
    public int r;
    public int c;

    public Cell(int time, int r,int c){
        this.time=time;
        this.r=r;
        this.c=c;
    }
}
public class RottingOranges {

    public static int timeToRot(int[][]oranges){
        // 
        if(oranges==null || oranges.length==0){
            throw new IllegalArgumentException("Oranges grid is empty");
        }
        final int rows=oranges.length;
        final int cols=oranges[0].length;

        final int[][] directions={{1,0},{0,1},{-1,0},{0,-1}};

        //get the freshCount and enquue all the rotten oranges first
        int freshCount=0;
        Queue<Cell> queue=new ArrayDeque<>();

        for(int r=0;r<rows;r++){
            for(int c=0;c<cols;c++){
                if(oranges[r][c]==1){
                    freshCount++;
                }
                if(oranges[r][c]==2){
                    queue.add(new Cell(0,r,c)); //euque rotten orange
                }
            }
        }

        //do a bfs
        int time=0;

        while(!queue.isEmpty()){
            Cell currCell=queue.poll();
            int r=currCell.r;
            int c=currCell.c;
            int currTime=currCell.time;

            //update time,relax time

            time=Math.max(currTime,time);

            //visit neighbours
            for(int[] d: directions){
                int nr=r+d[0];
                int nc=c+d[1];

                //if it's fresh rot it
                if(nr >= 0 && nr < rows && nc >=0 && nc<cols && oranges[nr][nc]==1){
                    oranges[nr][nc]=2;
                    freshCount--;
                    //enque
                    queue.add(new Cell(time+1,nr,nc));
                }
            }
        }

        //all the fresh oranges are rotten return time, else we return -1
        return freshCount==0? time: -1;
    }
    
    public static void main(String[] args){
        int[][] testOranges={
            {2,1,1},
            {1,1,0},
            {0,1,1}
        };

        int time=timeToRot(testOranges);

        System.out.println(time); //output 4

    }
}
