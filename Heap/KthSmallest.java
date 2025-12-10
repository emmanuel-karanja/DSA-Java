package Heap;

import java.util.Comparator;
import java.util.PriorityQueue;

//* n(logk) to find kthLargest, either use a MinHeap or you can find the n-kth element where n is the arr.length */

public class KthSmallest {

     public static int getKthSmallest(int[] arr, int k){
        //we insert the elements into a maxHeap and every time the heap.size()>k we poll

        //edge cases
        if (k <= 0 || k > arr.length) {
            throw new IllegalArgumentException("k must be between 1 and array length");
        }

        if (arr.length == 0) {
            throw new IllegalArgumentException("Array is empty");
        }


        PriorityQueue<Integer> maxHeap=new PriorityQueue<>(Comparator.reverseOrder());
        for(int val: arr){
            maxHeap.add(val);
            if(maxHeap.size()>k){
                 maxHeap.poll();
            }
        }

        return maxHeap.peek();

    }
    public static void main(String[] args){
        int[] arr1 = {1, 5, 12, 2, 11, 5};
        System.out.println("K=3 → " + getKthSmallest(arr1, 3)); // expected 5

        int[] arr2 = {7, 10, 4, 3, 20, 15};
        System.out.println("K=4 → " + getKthSmallest(arr2, 4)); // expected 10

        int[] arr3 = {3, 2, 1, 5, 6, 4};
        System.out.println("K=2 → " + getKthSmallest(arr3, 2)); // expected 2
    }
}
