package Arrays.MonotonicStack;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;

/**Given an array of integers, return a result of the first negative number in the window of size K in O(n) time.
 * If no nonegative number exists, insert 0 in the result
 * 
 * INTUTION:
 * 
 * Use a queue, to give it a single pass.
 */
public class FirstNegativeNumberInWindow {

    public static List<Integer> getFirstNegativeInWindow(int[] nums, int k){
        if(nums==null || nums.length==0){
            throw new IllegalArgumentException("Nums is empty or null.");
        }

        if(k>nums.length){
            throw new IllegalArgumentException("K cannot be greater than size of array.");
        }

        List<Integer> results=new ArrayList<>();
        ArrayDeque<Integer> queue=new ArrayDeque<>();

        final int n=nums.length;

        for(int i=0;i<n;i++){

            //those leaving the window
            if(!queue.isEmpty() && queue.peekFirst()<=i-k){
                queue.removeFirst();
            }

            //add index only if the number if -1
            if(nums[i]<0){
                queue.addLast(i);
            }

            //we have a good sized window
            if(i>=k-1){
                if(!queue.isEmpty()){
                    results.add(nums[queue.peekFirst()]);
                }else{
                    //add zero
                    results.add(0);
                }
            }
        }

        return results;
    }

    public static void main(String[] args) {
        int[] nums = {12, -1, -7, 8, 15, 30, -16, 28};
        int k = 3;

        List<Integer> negatives = getFirstNegativeInWindow(nums, k);
        System.out.println("First negative numbers in each window: " + negatives);
        // Output: [-1, -1, -7, 0, -16, -16]
    }
    
}
