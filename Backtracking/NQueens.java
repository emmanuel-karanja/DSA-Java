package Backtracking;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**Given an nxn board, how many ways can you place queens on it so that they don't attack each other?
 * 
 * INTUTION
 *  Use backtracking to place a queen, we will place a queen row to row to ensure that that we don't
 * have col attack, mainDiagonal attack or antiDiagonal attack, when we get to the n-1 row,
 * we know we have placed all the queens successfuly and save that board.
 * 
 * A board is a nxn matric or an array of arrays, and we return a list of them.
 * 
 * O(N!)
 */
public class NQueens {
    
    public static List<char[][]> placeQueens(int n) {
        if (n == 0) return Collections.emptyList();

        List<char[][]> solutions = new ArrayList<>();
        char[][] board = new char[n][n];

        // fill with '.'
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                board[i][j] = '.';
            }
        }

        Set<Integer> cols = new HashSet<>();
        Set<Integer> mainDiag = new HashSet<>();
        Set<Integer> antiDiag = new HashSet<>();

        backtrack(0, n, cols, mainDiag, antiDiag, board, solutions);

        return solutions;
    }

    private static void backtrack(int row,
                                  int n,
                                  Set<Integer> cols,
                                  Set<Integer> mainDiag,
                                  Set<Integer> antiDiag,
                                  char[][] board,
                                  List<char[][]> solutions) {

        if (row == n) {
            solutions.add(cloneBoard(board));
            return;
        }

        for (int col = 0; col < n; col++) {
            int md = row - col;
            int ad = row + col;

            if (cols.contains(col) || mainDiag.contains(md) || antiDiag.contains(ad))
                continue;

            // place queen
            cols.add(col);
            mainDiag.add(md);
            antiDiag.add(ad);
            board[row][col] = 'Q';

            backtrack(row + 1, n, cols, mainDiag, antiDiag, board, solutions);

            // remove queen
            cols.remove(col);
            mainDiag.remove(md);
            antiDiag.remove(ad);
            board[row][col] = '.';
        }
    }

    private static char[][] cloneBoard(char[][] board) {
        int n = board.length;
        char[][] copy = new char[n][n];
        for (int i = 0; i < n; i++)
            copy[i] = board[i].clone();
        return copy;
    }

    public static void main(String[] args) {
        int n = 4; // try 4, 5, 6, etc.
        List<char[][]> solutions = NQueens.placeQueens(n);

        System.out.println("Total solutions for " + n + "-Queens = " + solutions.size());
        int counter = 1;

        for (char[][] board : solutions) {
            System.out.println("Solution " + counter + ":");
            printBoard(board);
            System.out.println();
            counter++;
        }
    }

    private static void printBoard(char[][] board) {
        int n = board.length;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
    }
}
