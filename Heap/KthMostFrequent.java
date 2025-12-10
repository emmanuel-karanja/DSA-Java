package Heap;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

class FreqPair{
    public int freq;
    public int val;

    FreqPair(int val, int freq){
        this.val=val;
        this.freq=freq;
    }
}


public class KthMostFrequent {

    public static int getKthMostFrequent(int[] arr, int k){

        if(arr.length==0){
            throw new IllegalArgumentException("Array is empty.");
        }

        if(k>arr.length){
            throw new IllegalArgumentException("K is greater than arr size.");
        }

        if(k<=0){
            throw new IllegalArgumentException("K is be zero or less than zero.");
        }

        Map<Integer,Integer> freqMap = new HashMap<>();
       //store them in a frequency map

       for(int val: arr){
           if(freqMap.containsKey(val)){
               freqMap.put(val, freqMap.get(val)+1);
           }else{
                freqMap.put(val,1);
           }
       }

       // a-b is safe since frequences are always 1 or greater i.e. always positive and non-zero
       PriorityQueue<FreqPair> minHeap=new PriorityQueue<>((a,b)->a.freq-b.freq);
       // use a MinHeap to find the most frequent
       for(Map.Entry<Integer,Integer> entry: freqMap.entrySet()){
            //push the pair to the heap
            minHeap.add(new FreqPair(entry.getKey(),entry.getValue()));
            if(minHeap.size()> k){
                minHeap.poll();
            }
       }

       return minHeap.peek().val;        
    }  
    
    public static void main (String[] args){
        int[] arr1 = {1, 1, 1, 2, 2, 3};
        int k1 = 2;
        System.out.println(k1 + "nd most frequent element: " + getKthMostFrequent(arr1, k1));
        // Expected output: 2

        int[] arr2 = {4, 4, 4, 6, 6, 7, 7, 7, 7};
        int k2 = 1;
        System.out.println(k2 + "st most frequent element: " + getKthMostFrequent(arr2, k2));
        // Expected output: 7

        int[] arr3 = {5, 3, 1, 3, 5, 5, 2, 2, 2};
        int k3 = 3;
        System.out.println(k3 + "rd most frequent element: " + getKthMostFrequent(arr3, k3));
        // Expected output: 3
    }
}
