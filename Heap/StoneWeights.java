package Heap;

import java.util.Arrays;
import java.util.PriorityQueue;

/**Given an array of positive integers where each integers represent the weight of a stone. You pick two of the heaviest
 * stones and collide them together. If their weight is equal, they are both destroyed, if not the smaller one is destroyed
 * and the bigger one reduced to a weight equal to the difference of their weights.
 * 
 * Do this until only one stone remains and return its weight.
 * 
 * INTUTION:
 * 
 * This sounds like a maxHeap problem. We insert the stones into a heap and while heap.size()>1 we 
 * extract x,y, and if x-y=0, we don't reinsert and if x-y>0 we insert x-y .
 * 
 * Time O(nlogn) but if we had used an array and sort it every time it'd be O(n*nlogn)
 * 
 */
public class StoneWeights {
    public static int getLastStoneWeight(int[] weights){

        PriorityQueue<Integer> maxHeap=new PriorityQueue<>((a,b)->Integer.compare(b,a));

        //insert into heap
        for(int w: weights){
            maxHeap.add(w);
        }

        while(maxHeap.size()>1){  //notice the condition since we are polling twice
            int x=maxHeap.poll();
            int y=maxHeap.poll();

            if(x!=y){
                maxHeap.add(x-y);
            }
        }

        return maxHeap.isEmpty()?0:maxHeap.poll();
    }

     public static void main(String[] args) {
        int[][] testCases = {
            {2, 7, 4, 1, 8, 1},   // classic example
            {1},                // single stone
            {1, 1},             // equal stones
            {3, 3},             // equal stones
            {10, 4},            // simple difference
            {9, 3, 2, 10},      // unordered input
            {},                 // empty input
            {1, 2, 3, 4, 5, 6}, // increasing
            {6, 5, 4, 3, 2, 1}  // decreasing
        };

        for (int[] test : testCases) {
            System.out.println(
                "Input: " + Arrays.toString(test) +
                " → Output: " + getLastStoneWeight(test)
            );
        }
    }
}
