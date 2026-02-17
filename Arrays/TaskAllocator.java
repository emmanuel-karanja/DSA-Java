package Arrays;

/**
 * PROBLEM: Task Allocation Optimization
 * * Given N tasks with 'difficulty' levels and M workers with 'skill' levels.
 * A worker can complete a task if skill[i] >= difficulty[j]. 
 * Each worker can handle at most one task.
 * * GOAL: Maximize the number of completed tasks.
 * 
 * * APPROACH: Greedy + Two Pointers.
 * By matching the smallest capable skill to the smallest difficulty, 
 * we preserve high-skill workers for potentially higher-difficulty tasks.
 * * TIME COMPLEXITY: O(N log N + M log M) - Dominated by sorting.
 * SPACE COMPLEXITY: O(1) or O(log N) - Depends on the primitive sort implementation.
 */
import java.util.Arrays;

public class TaskAllocator {

    public int maxTasksCompleted(int[] difficulties, int[] skills) {
        // Defensive check for null or empty inputs
        if (difficulties == null || skills == null || difficulties.length == 0 || skills.length == 0) {
            return 0;
        }

        // Step 1: Sorting establishes the monotonicity required for the two-pointer approach
        Arrays.sort(difficulties);
        Arrays.sort(skills);

        int taskPointer = 0;
        int workerPointer = 0;
        int completedCount = 0;

        // Step 2: Iterate through workers and find the first one that clears the "Gate"
        while (workerPointer < skills.length && taskPointer < difficulties.length) {
            
            // GATE: Does current worker have the capability?
            if (skills[workerPointer] >= difficulties[taskPointer]) {
                // Successful match: "consume" the task
                completedCount++;
                taskPointer++;
            }
            
            // Increment worker regardless: 
            // If they matched, they are now busy.
            // If they didn't match, they are too weak for this (and all subsequent) tasks.
            workerPointer++;
        }

        return completedCount;
    }

    public static void main(String[] args) {
        TaskAllocator allocator = new TaskAllocator();
        
        // Test Case 1: Standard match
        int[] d1 = {1, 2, 3};
        int[] s1 = {1, 1};
        // Expect 1: Only one worker can handle the 1-difficulty task.
        System.out.println("Result 1: " + allocator.maxTasksCompleted(d1, s1)); 

        // Test Case 2: Perfect match
        int[] d2 = {1, 2};
        int[] s2 = {1, 2, 3};
        // Expect 2: Workers with 1 and 2/3 can handle both tasks.
        System.out.println("Result 2: " + allocator.maxTasksCompleted(d2, s2));
    }
}