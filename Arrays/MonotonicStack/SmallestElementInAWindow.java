package Arrays.MonotonicStack;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;

/**Given an an array of integers(positive and negative) and a window of size k, generate a result array
 * containing the smallest elements in a window of size k.
 * 
 * INTUTION
 * 
 * A fixed sliding window of size k and at each step, find the min of the window. Gives you O(nlogk) time complexity.
 * 
 * To do this in O(n):
 *   A deque, elements enter on the right, and you always ensure the element at end i.e. i-k is the smallest element
 *   as 
 * 
 * Take a note on how the deque is used here: We only use Front for removing those elements leaving the window.
 * And, we now treat the Back like a normal stack wih removeLast() and addLast(), however the smallest
 * element in this case is guaranteed to be at the front.
 */
public class SmallestElementInAWindow {
    
    public static List<Integer> smallestInWindow(int[] nums, int k){
        if(nums==null || nums.length==0){
            throw new IllegalArgumentException("Nums is empty or null.");
        }

        if(k>nums.length){
            throw new IllegalArgumentException("K is longer than array size.");
        }

        //we store indices
        // For Deque Front/Left<----->Back/Right  
        // Front always holds the min or max, new elements come in from the Back
        ArrayDeque<Integer> deque=new ArrayDeque<>();
        List<Integer> result= new ArrayList<>();

        for(int i=0;i<nums.length;i++){
            // Remove indices that are out of the current window 
            while (!deque.isEmpty() && deque.peekFirst() <= i - k) {
                deque.removeFirst();
            }

            // Maintain monotonic increasing order for max use > not <
            while (!deque.isEmpty() && nums[i]< nums[deque.peekLast()] ) {
                deque.removeLast();
            }

            // Add current index
            deque.addLast(i); //push 

            //find if we have a valid window
            if(i>=k-1){
                result.add(nums[deque.peekFirst()]); //we find the first element in the deque
            }
        }

        return result;
    }

    public static void main(String[] args) {
        int[] nums = {4, 2, 12, 3, 5, 1, 7};
        int k = 3;

        List<Integer> mins = smallestInWindow(nums, k);

        System.out.println("Input array: " + java.util.Arrays.toString(nums));
        System.out.println("Window size k = " + k);
        System.out.println("Minimum per window: " + mins);
    }
}
