package Graph;
/**Given a 2D board containing 'X' and 'O' capture all regions surrounded by 'X'. To capture means turning it
 * from 'O' to 'X'. Border 'O' or border connected 'O' cannot be captured.
 * 
 * INTUITION:
 * DFS.
 * Mark border 'O'. Do a dfs from the borderds and mark all 'O' encountered into 'S' or some temporary
 * marker, this will allow you to find all border and border connected Os.
 * 
 * Then do a matrix traversal and capture all remaining 'O' and then convert any 'S' back to 'O'
*/
public class SurroundedRegions {

    public static void captureSurroundedRegions(char[][] board){

        if(board==null || board.length==0) return; 

        final int rows=board.length;
        final int cols=board[0].length;

        char marker='T';

        //do a dfs from the edges where cols are const
        for(int r=0;r<rows;r++){
          dfs(board,r,0,rows,cols,marker);
          dfs(board,r,cols-1,rows,cols,marker);
        }

        for(int c=0;c<cols;c++){
           dfs(board,0,c,rows,cols,marker);
           dfs(board,rows-1,c,rows,cols,marker);
        }

        //board is now marked

        //iterate and reconvert
        for(int r=0;r<rows;r++){
            for(int c=0;c<cols;c++){
                if(board[r][c]=='O'){  //capture
                    board[r][c]='X'; 
                }else if(board[r][c]==marker){
                    board[r][c]='O';//change it back
                }
            }
        }

    }

    private static void dfs(char[][] board, int r,int c, int rows,int cols, char marker){
        if(r>=0 && r<rows && c>=0 && c<cols && board[r][c]=='O'){
            //do a dfsd
            board[r][c]=marker;

            dfs(board,r+1,c,rows,cols,marker);
            dfs(board,r,c+1,rows,cols,marker);
            dfs(board,r-1,c, rows,cols,marker);
            dfs(board,r,c-1,rows,cols,marker);
        }
    }

    public static void main(String[] args) {

        char[][] board = {
            {'X','X','X','X'},
            {'X','O','O','X'},
            {'X','X','O','X'},
            {'X','O','X','X'}
        };

        System.out.println("Before:");
        printBoard(board);

        captureSurroundedRegions(board);

        System.out.println("\nAfter:");
        printBoard(board);
    }

    private static void printBoard(char[][] board) {
        for (char[] row : board) {
            for (char c : row) {
                System.out.print(c + " ");
            }
            System.out.println();
        }
    }
    
}
