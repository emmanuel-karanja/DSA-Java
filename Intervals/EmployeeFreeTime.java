package Intervals;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**You are given a list of schedules for multiple employees where each schedule when they are busy. Each 
 * schedule is represented as an array of non-overlapping intervals. The goal is to find all the common
 * free times.
 * 
 * INTUTION:
 * 
 * Flatten all the intervals into a single array of intervals
 * Sort them by start times
 * Merge overlapping intervals
 * Find the gaps i.e. if we have intervals [a,b] and [c,d] and we find that b!=c, we have an interval [b,c]
  */

class Interval{
    public int startTime;
    public int endTime;
    public Interval(int start,int end){
        this.startTime=start;
        this.endTime=end;
    }
}
public class EmployeeFreeTime {

    

    private static List<Interval> flattenAndSort(List<List<Interval>> intervals){
        return intervals.stream()
                         .flatMap(List::stream)
                         .sorted((a,b)->a.startTime-b.startTime)
                         .collect(Collectors.toList());
    }

    private static List<Interval> mergeIntervals(List<Interval> intervals) {
        if(intervals==null || intervals.size()==0){
            throw new IllegalArgumentException("intervals null or empty.");
        }


        List<Interval> merged = new ArrayList<>();
        Interval current = intervals.get(0);

        for (int i = 1; i < intervals.size(); i++) {
            Interval next = intervals.get(i);

            if (current.endTime >= next.startTime) {
                // Overlap so merge
                current.endTime = Math.max(current.endTime, next.endTime);
            } else {
                // No overlap - push previous and reset
                merged.add(current);
                current = next; //this is the key
            }
        }

        merged.add(current); // Add last interval
        return merged;
    }

    private static List<Interval> getGaps(List<Interval> intervals){
        //gaps are when [a,b] and [c,d] wheere b!=c
        List<Interval> gaps=new ArrayList<>();

        for(int i=0;i<intervals.size()-1;i++){
            Interval current=intervals.get(i);
            Interval next=intervals.get(i+1);

            if(current.endTime != next.startTime){
                gaps.add(new Interval(current.endTime,next.startTime));
            }
        }

        return gaps;
    }

    public static List<Interval> getFreeTimes(List<List<Interval>> schedules){

        if(schedules==null || schedules.size()==0){
            throw new IllegalArgumentException("Schedules are empty or null.");
        }

        //sort
        List<Interval> sortedAndFlat=flattenAndSort(schedules);
        List<Interval> merged=mergeIntervals(sortedAndFlat);
        List<Interval> freeTimes=getGaps(merged);

        return freeTimes;
    }

    public static void main(String[] args){
        List<List<Interval>> testSchedules=List.of( 
            List.of(new Interval(1,3), new Interval(6,7)), 
            List.of(new Interval(2,4)), 
            List.of(new Interval(2,5),new Interval(9,12))
        );

        List<Interval> freeTime=getFreeTimes(testSchedules);

        for(Interval interval: freeTime){
            System.out.println("FreeFrom:"+interval.startTime+":"+interval.endTime);
        }      
    }   
}
