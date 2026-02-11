package Graph;

import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Queue;
import java.util.Set;

/**You are given targer cordinates x,y, on an infinite chessboard. A knight starts at 0,0. Return the minimum
 * number of moves it takes to for the knight to land at x,y.
 * 
 * INTUTION:
 * Use BFS and know how many directions a knight can move, even for another piece, you'dconsider this,
 * 
 * SYMMETRY: The valid board is (x, y), (-x, y), (x, -y), (-x, -y)

  and to move to those squares it'll still the same number of moves.

  We use x >=-2 and y>=-2 because shortest path may temporarily go negative.

  i.e. BFS because you expand on multiple directions simultaneously, until you find the first on that
  hits the target.
 * 
 */

class Node{
    public int x;
    public int y;
    public int steps;

    public Node(int x, int y, int steps){
        this.x=x;
        this.y=y;
        this.steps=steps;
    }
}
public class KnightMoves {

    public static int getMoveCount(int x,int y){
        // 1. x or y could be negative, normalize the board.
        final int absX=Math.abs(x);
        final int absY=Math.abs(y);

        int moves=0;

        //2.Directions

        final int[][] DIRECTIONS={{2,1},{1,2},{-2,1},{-1,2},
                                  {-2,-1},{-1,-2},{2,-1},{1,-2}};

        Queue<Node> queue=new ArrayDeque<>();

        // 3. Visited set 
        Set<String> visited=new HashSet<>();  //you are not immediately aware of the dimensions.

        queue.add(new Node(0,0,0));

        while(!queue.isEmpty()){
            Node currNode=queue.poll();

            int currX=currNode.x;
            int currY=currNode.y;
            int currSteps=currNode.steps;

            //check for end of board
            if(currX==absX && currY==absY){
                moves=currSteps;
                return moves;
            }

            //explore neighbours
            for(int d[]: DIRECTIONS){
                int newX=currX+d[0];
                int newY=currY+d[1];

                //4. Bounds check, we check against the -2 due to symmetry 
                if(newX >= -2 && newY>=-2 && !visited.contains(newX+":"+newY)){
                   visited.add(newX+":"+newY);
                   queue.add(new Node(newX,newY,currSteps+1));
                }
            }
        }

        return moves;
    }


    public static void main(String[] args){

        System.out.println(getMoveCount(5,5));
    }    
}
