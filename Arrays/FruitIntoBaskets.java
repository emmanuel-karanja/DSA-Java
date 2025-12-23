package Arrays;
/**
 * PROBLEM: Fruit Into Baskets
 * 
 * You are visiting a farm with a single row of fruit trees, left to right. 
 * Each tree has a specific type of fruit. 
 * You have two baskets; each can hold only one type of fruit, but with unlimited capacity.
 * 
 * You must pick exactly one fruit from each tree, moving to the right, starting at any tree.
 * You must stop picking when you reach a tree with a fruit type that cannot fit in your baskets.
 * 
 * Task: Determine the maximum number of fruits you can pick in total.
 * 
 * MODELING THE STATE (Using User Rubric):
 * 1. GOAL: Find the longest contiguous subarray with at most 2 distinct fruit types.
 * 2. TYPE: Sliding Window / Two Pointers
 * 3. INSIGHT:
 *    - The problem reduces to maintaining a window of at most 2 distinct elements.
 *    - Expand the window to the right, and shrink from the left if there are more than 2 types.
 * 4. ALGORITHM:
 *    - Use a HashMap<Integer, Integer> to store the count of each fruit type in the window.
 *    - Initialize left pointer at 0, iterate right pointer from 0 to n-1:
 *       - Add fruit at right to the map.
 *       - If map size > 2, shrink window from left until size <= 2.
 *       - Update maximum window size.
 * 5. COMPLEXITY:
 *    - Time: O(n) where n = number of trees
 *    - Space: O(1) to O(2) = O(1) (since at most 2 distinct fruits in map)
 */

import java.util.*;

public class FruitIntoBaskets {

    public int totalFruit(int[] fruits) {
        Map<Integer, Integer> count = new HashMap<>();
        int left = 0, maxFruits = 0;

        for (int right = 0; right < fruits.length; right++) {
            count.put(fruits[right], count.getOrDefault(fruits[right], 0) + 1);

            while (count.size() > 2) {   //if the count exceeds what we need that.
                int leftFruit = fruits[left];
                count.put(leftFruit, count.get(leftFruit) - 1);
                if (count.get(leftFruit) == 0) {
                    count.remove(leftFruit);
                }
                left++;
            }

            maxFruits = Math.max(maxFruits, right - left + 1);
        }

        return maxFruits;
    }

    public static void main(String[] args) {
        FruitIntoBaskets solver = new FruitIntoBaskets();
        int[] fruits = {1,2,1,2,3};
        System.out.println("Maximum fruits you can pick: " + solver.totalFruit(fruits));
    }
}
