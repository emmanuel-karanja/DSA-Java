package Strings;

/**
 * PROBLEM: "Chat Guardian" (Banned Word Filter)
 * INTUITION: 
 * - Use a Trie to store the dictionary of banned words.
 * - Scan the message using a 'sliding' start pointer.
 * - For each start position, traverse the Trie as far as possible.
 */

import java.util.*;

class TrieNode {
    // Map handles case-sensitivity or special chars better than a 26-char array
    Map<Character, TrieNode> children = new HashMap<>();
    boolean isEndOfWord = false;
}

public class ChatGuardianMaskBannedWord {
    private final TrieNode root = new TrieNode();

    // 1. Build the dictionary
    public void addBannedWord(String word) {
        TrieNode current = root;
        for (char ch : word.toLowerCase().toCharArray()) {
            current = current.children.computeIfAbsent(ch, c -> new TrieNode());
        }
        current.isEndOfWord = true;
    }

    // 2. The Filter Logic
    public String filterMessage(String message) {
        char[] chars = message.toCharArray();
        int n = chars.length;
        
        // We iterate through the message, attempting to find a word starting at index 'i'
        for (int i = 0; i < n; i++) {
            TrieNode current = root;
            int longestMatchEnd = -1;

            for (int j = i; j < n; j++) {
                char ch = Character.toLowerCase(chars[j]);
                if (!current.children.containsKey(ch)) break;
                
                current = current.children.get(ch);
                if (current.isEndOfWord) {
                    longestMatchEnd = j; // Greedy match: find the longest possible banned word
                }
            }

            // If a match was found, mask it
            if (longestMatchEnd != -1) {
                for (int k = i; k <= longestMatchEnd; k++) {
                    chars[k] = '*';
                }
                // Optional: i = longestMatchEnd; // Optimization to skip ahead
            }
        }
        return new String(chars);
    }

    private char normalize(char c) {
    if (c == '0') return 'o';
    if (c == '1' || c == '!') return 'i';
    if (c == '4' || c == '@') return 'a';
    // etc...
    return c;
}
}
