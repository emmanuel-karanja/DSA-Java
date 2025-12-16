package DP;

import java.util.*;

class Box{
    int width, depth, height;

    public Box(int w, int d, int h) {
        width = w;
        depth = d;
        height = h;
    }

    public int getBaseArea(){
        return width*depth;
    }
}

public class BoxStacking {

    /*
     Given an array of boxed with w,d,h, find the best arrangement to maximize the height if the boxes are stacked.

      INTUTION:

      First of all, each box is rotated to represent 3 boxes with several different sized bases. With the original
      height, width and depth taking the place for height and the others acting as bases respectively.

      1. Rotate a generated array of boxes and normalize the width and depth so that width>=depth
      2. Sort by descending order of base area so that for j<i we have base[j]>=base[i]
      3. Initialize the base case i.e. at dp[i] set the height to be box at i.
      4. Fill the dp array using the logic if base[j] >= base[j] then dp[i]=Max(dp[i],height[i]+dp[j])
      5. Iterate over the dp array to get the maxHeight.

     * DP Approach:
     *
     * Goal: Maximize total stack height under width/depth constraints.
     *
     * State: dp[i] = max stack height with box i at the bottom
     *
     * Choices: For each box j above i, if j.width < i.width and j.depth < i.depth,
     *          we can place it on top of box i. i.e. the box at position i must have a smaller base than the one at i-1 or any
     *         other previous positions.
     *
     * Recurrence: dp[i] = height[i] + max(dp[j]) for all j that can be on top
     *
     * Base case: dp[i] = height[i] (if no box can go on top)
     */

    public static int maxStackHeight(Box[] boxes) {
        int n = boxes.length;

        // Generate all rotations of boxes
        List<Box> allBoxes = new ArrayList<>();
        for (Box b : boxes) {
            //we max(width,depth) and min(width,depth) to normalize. Why? so that width>=depth
            //without it (4 × 6)  vs  (6 × 4) would be treated the same even though they are conceptually different
            allBoxes.add(new Box(Math.max(b.width, b.depth), Math.min(b.width, b.depth), b.height));
            allBoxes.add(new Box(Math.max(b.width, b.height), Math.min(b.width, b.height), b.depth));
            allBoxes.add(new Box(Math.max(b.height, b.depth), Math.min(b.height, b.depth), b.width));
        }

        // Sort boxes by base area descending
     
       // We sort that we can guaranteed j <i then base[j] >= base[i]
       Collections.sort(allBoxes, (a, b) -> Integer.compare(b.getBaseArea(), a.getBaseArea()));

        int m = allBoxes.size();
        int[] dp = new int[m];

        for (int i = 0; i < m; i++) {
            dp[i] = allBoxes.get(i).height;  //the base case for each box
        }

        // Fill DP table
        for (int i = 1; i < m; i++) {
            for (int j = 0; j < i; j++) {
                if (allBoxes.get(i).width < allBoxes.get(j).width &&  //we can only stack this box on top of the previous
                    //box if and only if the current box has a smaller base
                    allBoxes.get(i).depth < allBoxes.get(j).depth) {
                    
                    dp[i] = Math.max(dp[i], dp[j] + allBoxes.get(i).height);
                }
            }
        }

        // Find max stack height
        int maxHeight = 0;
        for (int h : dp) {
            maxHeight = Math.max(maxHeight, h);
        }

        return maxHeight;
    }

    public static void main(String[] args) {
        Box[] boxes = {
            new Box(4, 6, 7),
            new Box(1, 2, 3),
            new Box(4, 5, 6),
            new Box(10, 12, 32)
        };

        System.out.println("Max stack height: " + maxStackHeight(boxes)); // Output: 60
    }
}
