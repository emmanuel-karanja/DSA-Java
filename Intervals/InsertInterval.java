package Intervals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**\
 * Given an unsorted array of intervals, insert a new interval and merge any overlaps.
 * 
 * INTUTION:
 * 
 * The easiest approach is to just append the interval to the list of intervals and then sort and merge that list
 */

class Interval{
    public int startTime;
    public int endTime;

    public Interval(int start,int end){
        this.startTime=start;
        this.endTime=end;
    }
}
public class InsertInterval {

    public static List<Interval> getInsertedAndMerged(List<Interval> intervals, Interval newInterval){
        //add to list
        if(intervals==null){
            intervals=new ArrayList<>();
        }

        intervals.add(newInterval);
        if(intervals.size()==1)return intervals;

        //sort

        List<Interval> sorted=intervals.stream()
                              .sorted((a,b)->a.startTime-b.startTime)
                              .collect(Collectors.toList());

        //detect and merge overlaps
        List<Interval> merged=new ArrayList<>();
        Interval current=sorted.get(0);

        for(int i=1;i<sorted.size();i++){
            Interval next=sorted.get(i);
            if(current.endTime>=next.startTime){
                //overlap
                current.endTime=Math.max(current.endTime,next.endTime);
            }else{ //no overlap add
                merged.add(current);
                current=next;
            }
        }

        //add the last current
        merged.add(current);
    
        return merged;

    }

    public static void main(String[] args){
        List<Interval> intervals=new ArrayList<Interval>(
            Arrays.asList( 
                new Interval(1,2),
                new Interval(3,5),
                new Interval(6,7),
                new Interval(8,10),
                new Interval(12,16)
              )
            );

        Interval newInterval=new Interval(4,8);

        List<Interval> newList=getInsertedAndMerged(intervals, newInterval);

        for(Interval i: newList){
            System.out.println("["+i.startTime+","+i.endTime+"]");
        }
    }   
}
