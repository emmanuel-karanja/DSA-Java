package Graph;

import java.util.ArrayDeque;
import java.util.Queue;

/**
 * You are given a 2D grid representing roomsin a building. Each cell can be :
 * 
 *  A wall or obstacle: -1
 *  Gate: 0,
 *  Empty Room: INF
 * 
 * Fill each empty room with the distance to its nearest gate. If it's impossible to reach a gate,
 * it should be INF, 
 * 
 * INTUTION:
 * 1. Add all gates (0s) to a queue as a strting point.
 * 2. Run BFS from these gates simultaneously,
 * 3. As you expand fill the grid with the distances from the nearest gate
 */
 class Cell{
        public int r;
        public int c;
        public Cell(int r,int c){
            this.r=r;
            this.c=c;
        }
    }
public class WallsAndGates {
    
    public static int[][] fillDistances(int[][] rooms){
        //edge case
        if(rooms==null || rooms.length==0){
            throw new IllegalArgumentException("Empty Grid");
        }

        int rows=rooms.length;
        int cols=rooms[0].length;

        int[][] directions={{1,0},{0,1},{-1,0},{0,-1}};

        Queue<Cell> queue=new ArrayDeque<>();

        int EMPTY_ROOM=Integer.MAX_VALUE;

        // add all gates to the queue
        for(int r=0;r<rows;r++){
            for(int c=0;c<cols;c++){
                if(rooms[r][c]==0){
                    queue.add(new Cell(r,c));
                }
            }
        }

        //DO BFS
        while(!queue.isEmpty()){
            Cell currCell=queue.poll();
            int cr=currCell.r;
            int cc=currCell.c;
            int distance=rooms[cr][cc]; //we store the distance in the cell itself, not as a separate field in the queueNode

            for(int[]d : directions){
                int nr=currCell.r+d[0];
                int nc=currCell.c+d[1];

                //check bounds and it's an empty room as opposed to a room with an obstacle or a gate.
                // No need for explicit visited checking since any room viisted will have dist+1 in it.
                
                if(nr>=0 && nr <rows && nc >=0 && nc<cols && rooms[nr][nc]==EMPTY_ROOM){
                    // KEY
                    int newDistance=distance+1;
                    rooms[nr][nc]=newDistance; //increase direction by one
                    queue.add(new Cell(nr,nc));
                }
            }
        }

        return rooms;
    }
   
    public static void main(String[] args){

        final int INF = Integer.MAX_VALUE;

        int[][] rooms = {
            {INF,  -1,   0,  INF},
            {INF, INF, INF,  -1},
            {INF,  -1, INF,  -1},
            {0,    -1, INF, INF}
        };

        
        int[][] result = fillDistances(rooms);

        // Print the resulting grid
        for (int r = 0; r < result.length; r++) {
            for (int c = 0; c < result[0].length; c++) {
                int val = result[r][c];
                if (val == INF) {
                    System.out.print("INF ");
                } else {
                    System.out.print(val + "   ");
                }
            }
            System.out.println();
        }
    }
}
