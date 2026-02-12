package Heap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

/**Given a set of tasks represented by capital letters e.g. ['A',A','B,'C','D'] etc and a cooldown period
 * of time n, schedyle the tasks such that:
 *  1. The same task can't run run until n units of time are over.
 *  2. Each unit of time executes a task or is idle.
 *  
 * Return the minimum amount of time needed to perform all tasks.
 * 
 * INTUTION:
 * 
 * .If a task appears many times, it forces gaps (cooldowns) between its executions.
 *  i.e. 'A' to 'A', you have to wait n times before you can execute one to the next. So if you execute A (most frequent),
 *  in between, you can execute the other tasks e.g 'B'
 * 
 * We spread out the most frequent tasks so their forced cooldown gaps can be filled by other,
 *  less frequent tasks, instead of turning into idle time.
 * 
 * i.e.in the loop, you remove the most frequent task and addit when time n ends, add it less one count.
 * in that time, the tasks can execute.
 * 
 * To finish in smallest amount of time
 *   1. Always run the most frequent task first
 *   2. Wait on idle only when all other tasks are cooling down
 * Generate a task->count map .
 * Schedule the tasks to run with a cool down.
 * Using max heap to prioritize the most frequent task, if a t
 * 
 * KEY: We execute other tasks during a given task's cool down period.
 * 
 * if asked for maximum time, sort the tasks by frequency and execute the most frequent tasks inorder.
  */
public class TaskScheduling {

    public static int getMinExecutionTime(char[] tasks, int n){
        if(tasks==null || tasks.length==0){
            return 0;
        }

        if(n<0){
            throw new IllegalArgumentException("Cool down time must be positive.");
        }
        if(n==0){
            return tasks.length; //if the cool down time is 0, we execute each task immediately
        }

        //create a task->frequency count
        Map<Character, Integer> taskCount=new HashMap<>();
        for(char t: tasks){
           taskCount.put(t,taskCount.getOrDefault(t,0)+1);
        }

        //use a max heap
       PriorityQueue<Integer> maxHeap = new PriorityQueue<>((a, b) -> Integer.compare(b, a)); //use this style

        for(Map.Entry<Character,Integer> entry: taskCount.entrySet()){
            maxHeap.add(entry.getValue());
        }

        int time=0;
        final int coolDownWindow=n+1;// 1. we to wait an entire n duration to to get on, it's 0..1..2..3......n gaps
        //of n,so if we did from 0 to n, we'd be getting n-1 gaps not n, so we do from 0 to n+1

        while(!maxHeap.isEmpty()){
            List<Integer> temp=new ArrayList<>();
            //run task simulate time.
           
            for(int i=0;i<=coolDownWindow;i++){  //cool down from i=0 to i=n ,count from 0 to n

                //we've got to check this condition since we know that the size of head could drop to within an iteration
                if(maxHeap.size()>0){
                    int currentTaskCount=maxHeap.poll();// remove from heap
                    if(currentTaskCount>1){ //if a task is completely done we don't hold it in queue
                        temp.add(currentTaskCount-1); //execute task
                    }
                }

                time++; //advance time

                //check if we have run out of tasks i.e. maxHeap and temp are empty
                if(maxHeap.isEmpty() && temp.isEmpty()){
                    return time;
                }

                //cool down we'll count from i=0 to i=n
           } 
           
           //reinsert the remaining task
           for(int remainingTask: temp){
              maxHeap.add(remainingTask);
           }
        }

        return time;
    }

    public static void main(String[] args){
        char[] tasks={'A','A','A','B','B','B'};

        System.out.println("Min Time:"+getMinExecutionTime(tasks, 2));
    }
    
}
