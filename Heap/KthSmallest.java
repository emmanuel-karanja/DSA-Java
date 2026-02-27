package Heap;

import java.util.Comparator;
import java.util.PriorityQueue;

/* n(logk) to find kthLargest, either use a MinHeap or you can find the n-kth element where n is the arr.length 

Why is it minHeap for top K largest and maxHeap for top k smallest? -->Passive Retention" strategy. In a Min-Heap of size K, 
the "big boys" are 
safely buried in the lower levels of the tree, while the "weakest link" is constantly pushed to the root,
 effectively volunteering as tribute for the next poll() operation.

 This is the same reverse reason for maxHeap for top k smallest--> the big ones ar near the top and the smaller
  ones are pushed to the bottom and hence less eligible for polling when pq.size() > k. 


*/

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
