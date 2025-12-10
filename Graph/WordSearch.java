package Graph;
/**Given a 2Dgrid of characters and a word, check of the word exists in the grid:
 * 
 * INTUTION:
 * 
 * Do an iteration of the grid from 0,0, and everytime you encounter the next character of the word, 
 * ddo a DFS to locate the other chars
 */
public class WordSearch {

    public static boolean wordExists(char[][] board, String word){
        if(board ==null || board.length==0){
            return false;
        }

        final int m=board.length;
        final int n=board[0].length;

        //iterate over the board
        for(int r=0;r<m;r++){
            for(int c=0;c<n;c++){
                 if(dfs(board,word,r,c,m,n,0)){ return true;}
            }
        }

        return false;
    }

    private static boolean dfs(char[][] board, String word, int r,int c, int rows, int cols, int index){
        //index points to the current char
        if(index==word.length()) return true; //we've reached end of the word

        if(r < 0 || r >= rows || c<0 ||c >= cols || board[r][c]!=word.charAt(index)){
              return false;
        }

        //temporarily mark the current cell 
        char temp=board[r][c];

        board[r][c]='#';

        boolean result=dfs(board,word, r+1,c,rows,cols,index+1) || 
                       dfs(board,word, r,c+1,rows,cols,index+1) || 
                       dfs(board, word,r-1,c,rows,cols,index+1) || 
                       dfs(board,word,r,c-1, rows,cols,index+1);

        //reset the char
        board[r][c]=temp;

        return result;
        
    }

    public static void main(String[] args) {
        char[][] board = {
            {'A','B','C','E'},
            {'S','F','C','S'},
            {'A','D','E','E'}
        };

        String word1 = "ABCCED";
        String word2 = "SEE";
        String word3 = "ABCB";

        System.out.println("Word: " + word1 + " -> " + WordSearch.wordExists(board, word1));
        System.out.println("Word: " + word2 + " -> " + WordSearch.wordExists(board, word2));
        System.out.println("Word: " + word3 + " -> " + WordSearch.wordExists(board, word3));
    }
    
}
