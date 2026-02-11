package Arrays.MonotonicStack;

import java.util.*;
/**Problem Statement: We have a queue of player ratings. We want to pick a contiguous group of 5 players 
 * such that the difference between the highest and lowest rating in that group is minimized.”
 * 
 * INTUTION:
 * 
 * This is a sliding window + min/max tracking problem.
     Window size = 5 → we slide a window of 5 players across the array.
     At each position, we need max(window) - min(window).

Goal → minimum difference across all windows.
 *  */
public class MinMaxSkillDifference {
    public static int minSkillDifference(int[] ratings, int k) {
        int n = ratings.length;
        if (n < k) return -1;

        Deque<Integer> maxDeque = new ArrayDeque<>();
        Deque<Integer> minDeque = new ArrayDeque<>();
        int minDiff = Integer.MAX_VALUE;

        for (int i = 0; i < n; i++) {
            
            // Remove indices outside the window
            while (!maxDeque.isEmpty() && maxDeque.peekFirst() <= i - k){
                maxDeque.pollFirst();
            }
            while (!minDeque.isEmpty() && minDeque.peekFirst() <= i - k){
                minDeque.pollFirst();
            }
            // Maintain max deque (decreasing)
            while (!maxDeque.isEmpty() && ratings[i] > ratings[maxDeque.peekLast()]) {
                maxDeque.pollLast();
            }
            maxDeque.offerLast(i);

            // Maintain min deque (increasing)
            while (!minDeque.isEmpty() && ratings[i] < ratings[minDeque.peekLast()]) {
                minDeque.pollLast();
            }
            minDeque.offerLast(i);


            // Once we have a full window
            if (i >= k - 1) {
                int diff = ratings[maxDeque.peekFirst()] - ratings[minDeque.peekFirst()];
                minDiff = Math.min(minDiff, diff);
            }
        }

        return minDiff;
    }

    public static void main(String[] args) {
        int[] ratings = {10, 15, 12, 11, 14, 13, 16};
        System.out.println(minSkillDifference(ratings, 5)); // Example
    }
}
