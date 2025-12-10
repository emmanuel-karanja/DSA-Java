package Intervals;

import java.util.Arrays;

/**Given an array of intervals where each element is a [startTime,endTime]  and these intervals represent meetings,
 * figure out of a person can attend all meetings;
 * 
 * INTUTION
 * 
 * Find if there is an overlap in the intervals.
 * 
 * how? 
 * Sort by starttime, and then, find if an overlap ocurrs if for adjacent meetings A and B, an
 * overlap occuirs of A.endTime>b.startTime
 */

class Interval{
    public int startTime;
    public int endTime;

    public Interval(int start,int end){
        this.startTime=start;
        this.endTime=end;
    }
}
public class CanAttendAllMeetings {
   
    public static boolean canAttendAll(Interval[] meetings){

       if(meetings==null || meetings.length==0){
         throw new IllegalArgumentException("Intervals list empty or null.");
       }

       if(meetings.length==1) return true;

       //sort
       Arrays.sort(meetings,(a,b)->a.startTime-b.startTime); //we can use this here since time is positive


       //find an overlap

       for(int i=0;i<meetings.length-1;i++ ){
         Interval current=meetings[i];
         Interval next=meetings[i+1];

         if(current.endTime>next.startTime){
            return false;
         }
       }

       return true;
    }

    public static void main (String[] args){
        Interval[] testMeetings={
            new Interval(0,30),new Interval(5,10),new Interval(15,20)
        };

        System.out.println(canAttendAll(testMeetings));
            Interval[] testMeetings1={
            new Interval(7,10),new Interval(2,4)
        };

        System.out.println(canAttendAll(testMeetings1));
    }
}
