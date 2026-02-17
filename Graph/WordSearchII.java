package Graph;

import java.util.*;

/**
 * Word Search II
 * ----------------
 * Given a 2D grid of characters and a list of words, find all words that exist in the board.
 *
 * NAIVE APPROACH:
 * 
 * Create word search 1, and then iterate over the list of words and try to find it exists. If one word doesn't exist,
 * return false; 
 * 
 * 
 * Approach:
 * 1. Build a Trie for all words (to check prefixes efficiently)
 * 2. DFS from each cell:
 *    - Track visited cells to avoid revisiting
 *    - Pass the current Trie node along the path
 *    - Prune DFS immediately if current path is not a prefix in Trie
 *    - If Trie node marks a word, add it to result
 */
public class WordSearchII {

    // Trie Node definition
    static class TrieNode {
        Map<Character, TrieNode> children = new HashMap<>();
        String word = null; // if not null, this node marks the end of a word
        //store the word itself so that you don't have to rebuild it every time.
    }

    // Build Trie from word list
    private static TrieNode buildTrie(String[] words) {
        TrieNode root = new TrieNode();
        for (String word : words) {
            TrieNode node = root;
            for (char c : word.toCharArray()) {
                node = node.children.computeIfAbsent(c, k -> new TrieNode());
            }
            node.word = word; // mark end of word
        }
        return root;
    }

    public static List<String> findWords(char[][] board, String[] words) {
        List<String> result = new ArrayList<>();
        if (board == null || board.length == 0 || words == null || words.length == 0) return result;

        TrieNode root = buildTrie(words);
        int m = board.length, n = board[0].length;

        for (int r = 0; r < m; r++) {
            for (int c = 0; c < n; c++) {
                dfs(board, r, c, root, result);
            }
        }

        return result;
    }

    private static void dfs(char[][] board, int r, int c, TrieNode node, List<String> result) {
        char ch = board[r][c];

        if (!node.children.containsKey(ch)) return; // prune path

        TrieNode nextNode = node.children.get(ch);  //descend that path
        if (nextNode.word != null) {
            result.add(nextNode.word);  // found a word
            nextNode.word = null;       // avoid duplicates
        }

        board[r][c] = '#'; // mark visited

        int[][] directions = {{1,0},{0,1},{-1,0},{0,-1}};
        for (int[] dir : directions) {
            
            int nr = r + dir[0], nc = c + dir[1];
            
            if (nr >= 0 && nr < board.length && nc >= 0 && nc < board[0].length && board[nr][nc] != '#') {
                dfs(board, nr, nc, nextNode, result);
            }
        }

        board[r][c] = ch; // backtrack
    }

    public static void main(String[] args) {
        char[][] board = {
            {'o','a','a','n'},
            {'e','t','a','e'},
            {'i','h','k','r'},
            {'i','f','l','v'}
        };

        String[] words = {"oath","pea","eat","rain"};

        List<String> found = findWords(board, words);
        System.out.println(found); // should print ["oath","eat"]
    }
}
