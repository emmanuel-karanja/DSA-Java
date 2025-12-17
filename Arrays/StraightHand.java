package Arrays;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Problem Definition:
 * -------------------
 * Given an array of integers `cards` representing cards and an integer `W` representing group size,
 * determine if the cards can be rearranged into groups of `W` consecutive cards.
 * Each card can only be used once. Return true if possible, else false.
 *
 * Example:
 *   cards = [1,2,3,6,2,3,4,7,8], W = 3
 *   Possible grouping: [1,2,3], [2,3,4], [6,7,8] -> returns true.
 *
 * Intuition:
 * ----------
 * 
 * 
 * 1. If the total number of cards is not divisible by W, return false.
 * 2. Count the frequency of each unique card.
 * 3. Sort the unique card values.
 * 4. For each smallest available card, attempt to form a group of W consecutive cards.
 *    - If at any point a needed card is missing or insufficient, return false.
 *    - Otherwise, decrement the counts accordingly.
 * 
 * KEY: If we are at card key, we'll use up all the cards for the key+1 card i.e. we'll decreement the key+1 card count
 *  by the key card count. i.e. 
 * 
  KEY REASONING: Starting from the current card,can we form full straights? e.g. if we have 2 with a count 3 as the starting
  card, and we need 4 cards for the hand we'll need 3 counts of 3, 3 counts of 4 and 3 counts of 5 to form full straights.
  If any of them has less, we can't and hence return false.
 * 
 * time O(n log n + n * W)  --? nlogn from sorting and n*W for the loop but we pick the dominant one
 * so O(nlogn)
 */

public class StraightHand {

    public static boolean canFormStraightHands(int[] cards, int W) {
        // Total number of cards must be divisible by W
        if (cards.length % W != 0) return false;

        // Count frequency of each card
        Map<Integer, Integer> cardToCountMap = new HashMap<>();
        for (int card : cards) {
            cardToCountMap.put(card, cardToCountMap.getOrDefault(card, 0) + 1);
        }

        // Sort the unique card values
        List<Integer> sortedKeys = cardToCountMap.keySet().stream().sorted().collect(Collectors.toList());

        for (int key : sortedKeys) {
            int keyFreq = cardToCountMap.get(key);
            if (keyFreq > 0) {
                // Try forming a group of W consecutive cards starting at 'key'
                for (int i = 0; i < W; i++) {
                    int nextCard = key + i;
                    int nextCount = cardToCountMap.getOrDefault(nextCard, 0);
                    // If there aren't enough cards to continue the group, return false
                    //the nextCArd count can be equal and to the keyFreq and that's fine or more meaning we can
                    //start a new grouping from the next card but not less.
                    if (nextCount < keyFreq) return false;
                    // Decrement the count of the used cards, 
                    cardToCountMap.put(nextCard, nextCount - keyFreq);
                }
            }
        }
        return true;
    }

    public static void main(String[] args) {
        int[] cards = {1,2,3,6,2,3,4,7,8};
        int W = 3;
        System.out.println("Can form straight hands? " + canFormStraightHands(cards, W)); // true
    }
}
