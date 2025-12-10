package Intervals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**Given an array of meeting intervals, find the minimum number of meeting rooms
 * 
 * INTUTION:
 * 
 * For each pair of meetings, find if they overlap if they do,you need 2 rooms if not,
 * we need 1 room only.
 * 
 * To do this; we'll begin with 0  rooms, and then we check if a meeting is starting,
 * if a meeting is starting, we add a room, if a meeting ends, we substract to the total meetings
 * onceall the startings are exhausted, wecan return the meetings
 */

class Interval{
    public int startTime;
    public int endTime;

    public Interval(int start,int end){
        this.startTime=start;
        this.endTime=end;
    }
}

public class MinimumMeetingRooms {

    public static int getMinMeetingRooms(List<Interval> meetings){

        //extract startTimes and endTimes into separate arrays and sort
        List<Integer> startTimes=meetings.stream()
                                  .map(i->i.startTime)
                                  .sorted()
                                  .collect(Collectors.toList());
        List<Integer> endTimes=meetings.stream()
                                       .map(i->i.endTime)
                                       .sorted()
                                       .collect(Collectors.toList());

      //pointers for both
      int i=0;
      int j=0;
      int rooms=0;
      int minRooms=0;
      while(i <startTimes.size()){
        if(startTimes.get(i) <endTimes.get(j)){  //a meeting starts
             rooms++;
             minRooms=Math.max(minRooms,rooms);
             i++;
        }else{ //a meeting ends
            rooms--;
            j++;
        }
      }

      return minRooms;
    }
    
    public static void main(String[] args){
        List<Interval> meetings=new ArrayList<>(
            Arrays.asList(
                new Interval(0,30),
                new Interval(5,10),
                new Interval(15,20)
            )
        );

        int minMeetingRooms=getMinMeetingRooms(meetings);

        System.out.println(minMeetingRooms);
    }
}
