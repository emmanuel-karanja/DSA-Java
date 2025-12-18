package Arrays.MonotonicStack;

import java.util.ArrayDeque;
import java.util.Arrays;

/**You are given an array of integers representing temperatures where the index is the day.
 * Generate a results array such that it contains the day with the next warmer temperature.
 * 
 * INTUTION:
 * 
 * Use a monotonic decreasing stack so that the top contains the last colder temperature.
 *  When a warmer temperature is encountered, we pop until the stack maintains monotonic decreasing order.
 * 
 * MONOTONIC STACKS
 * 
 * Monotonic decreasing-->the TOP of the stack contains the smallest element in the stack and they increase down the
 * stack. Now if the current element x is greater than the top, we pop all the elements until we encounter one greater.
 * We remove it because it breaks the monotonic order. 
 * 
 * Vice versa for the monotonic increasing. The TOP has the largest.
 * 
 * Using ArrayDeque we push to the front/top/left, and remove from the front only.
 * 
 * MONOTONIC DECREASING = BOTTOM to TOP decreasing basically older to newer order.
 * 
 * FRONT/TOP/LEFT<----->BOTTOM/BACK/RIGHT 
 *  */
public class NextWarmerDay {

    public static  int[] getWarmerDay(int[] temperatures){
        if(temperatures==null || temperatures.length==0){
            throw new IllegalArgumentException("Temperatures array is empty or null.");
        }

        final int n=temperatures.length;

        int[] results=new int[n];  //array entries in java are usually initialized to 0.

        //prefer ArrayDeque over the Stack, it's faster, nonsynchronized and recommended by Java docs. Stack is legacy(deprecated)
        ArrayDeque<Integer> stack=new ArrayDeque<>(); 

        for(int i=0;i<n;i++){
            //If we encounter a warmer day,we pop the top
            while(!stack.isEmpty() && temperatures[i]>temperatures[stack.peek()]){
                 int prevIndex=stack.pop(); 
                 //The stack only contains indices whose warmer day hasn’t been found yet.
                 
                 results[prevIndex]=i-prevIndex; //we want how many days until the next warmer day? i.e.
                 // how many days between today and the next warmer day.
            }

            stack.push(i); //addFirst(), Basically add today's day
            System.out.println("Stack so far:"+Arrays.toString(stack.toArray()));
        }
         return results;
    }
 
     public static void main(String[] args) {
        int[] temperatures = {73, 74, 75, 71, 69, 72, 76, 73};

        int[] res = getWarmerDay(temperatures);

        System.out.print("Result: ");
        System.out.println(Arrays.toString(res));
    }

   
}
