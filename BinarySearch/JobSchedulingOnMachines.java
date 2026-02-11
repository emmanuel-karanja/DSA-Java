package BinarySearch;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

/**You are given:

   n jobs, each with a processing time job[i]
   m machines, each with a speed speed[j]

Goal: Assign jobs to machines such that the total completion time (makespan) is minimized.
Total completion time = max time any machine finishes its assigned jobs.

Constraints:

Each job goes to exactly one machine

A machine can process multiple jobs sequentially, but processing time is scaled by speed:

INTUITION:

This is a minimize the maximum type problem, because you are trying to minimize the longest time any machine spends.

Candidate answer x = a possible maximum completion time (makespan).
Feasibility test: Can we assign all jobs to machines such that no machine exceeds time x?
Greedy approach often works: assign jobs to the fastest available machine that can finish without exceeding x.

the total time for the jobs to be completed by machine j  (with speed[j]) is

 totalTime=sum of (job[i]/speed[j])

 So, the upper time limit for the range is maxTime=sum(jobs[i]/min(speed)) or the sum of scaled times
  for the slowest machine.

Binary search: Search the range [0, sum(job[i])/min(speed)] for the smallest feasible x.

Once you pick minTime between [0...maxTime] you ask:
Can I assign all jobs to machines such that no machine’s total time exceeds minTime?”

Initialize array machineTime[j] = 0 for all machines

Each machine has the totalMachineTime it takes to complete a job which is sum(jobs[i]/speed[j])

the question here is :“Is there any machine that can take this job without exceeding maxTime?”
For each job in jobs (often sorted descending):
    Find a machine j where machineTime[j] + job[i]/speed[j] <= mid
    If such a machine exists:
        Assign job[i] to machine j
        machineTime[j] += job[i]/speed[j]
    Else:
        return false   // job cannot fit within mid time


We sort jobs in descending order to get out of the larger jobs out of the way first since they are 
the ones most likely to break maxTime.

If the greedy feasibility check fails with jobs ordered from largest to smallest, then no assignment exists for that maxTime.

This can also be solved using a heap.
*/


public class JobSchedulingOnMachines {

    // Main function to compute minimum maximum completion time (makespan)
    public static double minimizeMakespan(int[] jobs, int[] speeds) {
        int n = jobs.length;
        int m = speeds.length;

        // Sort jobs descending to assign bigger jobs first in feasibility check
        Integer[] sortedJobs = new Integer[n];
        for (int i = 0; i < n; i++) sortedJobs[i] = jobs[i];
        //1. Sort jobs in descending order
        Arrays.sort(sortedJobs, (a, b) -> b - a);

        // Binary search bounds: [0, worst-case time]
        double left = 0;
        double right = Arrays.stream(jobs).sum() / Arrays.stream(speeds).min().getAsInt();
        double eps = 1e-6;

        // Binary search to find minimum feasible max time
        while (right - left > eps) {
            double mid = left + (right - left) / 2;
            if (canAssign(sortedJobs, speeds, mid)) { // feasible → try smaller
                right = mid; 
            }
            else {// infeasible → try larger
                left = mid;
             } 
        }
        return right;
    }

    // Feasibility check: can all jobs be assigned without any machine exceeding maxTime
    // “Does there exist any machine that can take this job without exceeding maxTime?”
   static class Machine {
    int speed;
    double load; // current assigned time

    Machine(int speed) {
        this.speed = speed;
        this.load = 0.0;
    }
  }

 private static boolean canAssign(Integer[] jobs, int[] speeds, double maxTime) {
    // Sort jobs descending: largest jobs first
    Arrays.sort(jobs);
    for (int i = 0, j = jobs.length - 1; i < j; i++, j--) {
        int tmp = jobs[i]; jobs[i] = jobs[j]; jobs[j] = tmp;
    }

    // Min-heap: machine with least current load at the top
    PriorityQueue<Machine> pq = new PriorityQueue<>(Comparator.comparingDouble(m -> m.load));

        // Initialize machines
        for (int speed : speeds) {
            pq.add(new Machine(speed));
        }

        // Assign jobs one by one
        for (int job : jobs) {
            Machine machine = pq.poll(); // least loaded machine
            double jobTime = (double) job / machine.speed;

            if (machine.load + jobTime > maxTime) {
                // Even the least loaded machine can't take this job
                return false;
            }

            machine.load += jobTime;   // assign job
            pq.add(machine);           // put back into heap
        }

        return true; // all jobs assigned successfully
    }


    public static void main(String[] args) {
        int[] jobs = {7, 3, 2};
        int[] speeds = {2, 1};
        System.out.printf("Minimum maximum completion time: %.6f\n", minimizeMakespan(jobs, speeds));
    }
    
}
