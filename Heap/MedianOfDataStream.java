package Heap;

import java.util.PriorityQueue;

/**find median of a data stream. 
 * 
 * INTUTION:
 * 
 * Use two heaps a maxHeap and a minHeap. Size propertymeans left.size()>= right.size(); with a difference of 
 * 1.
 */
public class MedianOfDataStream {

//maxHeap
  private PriorityQueue<Integer> left=new PriorityQueue<>((a,b)->b - a);

//minHeap
 private PriorityQueue<Integer> right=new PriorityQueue<>();//

  public void add(int value){
    //Insert, Balance, maintain Size property
    left.add(value);

    //balance
    right.add(left.poll());

    //Maintain size property left.size>=right.size
    if(left.size()<right.size()){
        left.add(right.poll());
    }

  }

  public void addAll(int[] nums){
    for(int i: nums){
        add(i);
    }
  }
    public double getMedian(){
        //odd case
        if(left.size()>right.size()){
            return left.peek();
        }
        //else it'seven case
        return (left.peek()+right.peek())/2.0;
    }

  public static void main(String[] args){
    MedianOfDataStream medianFinder=new MedianOfDataStream();

    int[] nums={1,2,3,4,7,11,15,8};
    medianFinder.addAll(nums);

    
    System.out.println(medianFinder.getMedian());
  }
}
