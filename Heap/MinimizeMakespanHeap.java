package Heap;

import java.util.*;

/*

You are given:

   n jobs, each with a processing time job[i]
   m machines, each with a speed speed[j]

Goal: Assign jobs to machines such that the total completion time (makespan) is minimized.
Total completion time = max time any machine finishes its assigned jobs.

Constraints:

Each job goes to exactly one machine

INTUITION:

This is a minimize the maximum type problem, because you are trying to minimize the longest time any machine spends.

Candidate answer x = a possible maximum completion time (makespan).
Feasibility test: Can we assign all jobs to machines such that no machine exceeds time x?
Greedy approach often works: assign jobs to the fastest available machine that can finish without exceeding x.

the total time for the jobs to be completed by machine j  (with speed[j]) is

 totalTime=sum of (job[i]/speed[j])

 So, the upper time limit for the range is maxTime=sum(jobs[i]/min(speed)) or the sum of scaled times
  for the slowest machine.

Can I assign all jobs to machines such that no machine’s total time exceeds minTime?”

A machine can process multiple jobs sequentially, but processing time is scaled by speed:
LOGIC (Greedy + Min-Heap Scheduling):

We want to assign jobs to machines so that the maximum completion time
(makespan) is minimized.

Key idea:
- Always assign the next job to the machine that becomes free the earliest.
- This locally optimal choice minimizes the current maximum load and
  leads to a globally optimal makespan for this scheduling model.

Steps:
1. Sort jobs in descending order so large jobs are placed first.
2. Use a min-heap ordered by current completion time of each machine.
3. For each job:
   - Pop the machine with the smallest current completion time.
   - Assign the job to it (jobTime = job / speed).
   - Push the machine back with updated completion time.
4. The answer is the maximum completion time across all machines.

Time Complexity:
- O(n log m), where n = jobs, m = machines.
*/

public class MinimizeMakespanHeap {

    static class Machine {
        double time;
        int speed;

        Machine(double time, int speed) {
            this.time = time;
            this.speed = speed;
        }
    }

    public static double minimizeMakespan(int[] jobs, int[] speeds) {

        //sort in descending order
        Arrays.sort(jobs);

        PriorityQueue<Machine> pq = new PriorityQueue<>(
            Comparator.comparingDouble(m -> m.time)
        );

        for (int speed : speeds) {
            pq.add(new Machine(0.0, speed));
        }

        for (int i = jobs.length - 1; i >= 0; i--) {
            Machine m = pq.poll();
            double curJobTime=jobs[i] / (double) m.speed;
            m.time += curJobTime;
            pq.add(m);
        }

        double makespan = 0.0;
        for (Machine m : pq) {
            makespan = Math.max(makespan, m.time);
        }

        return makespan;
    }

    public static void main(String[] args) {
        int[] jobs = {7, 3, 2};
        int[] speeds = {2, 1};
        System.out.println(minimizeMakespan(jobs, speeds));
    }
}
