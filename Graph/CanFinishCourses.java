package Graph;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

/**You are given a total number of courses numCourses, labeled from 0 to numCourses-1.
 * Prerequisites: an array where the prerequisites[i]=[a,b] means that you mus first take course b
 * before course a. Return true if you it's possible to finish all the courses, otherwise return false
 * 
 * UNTUTION: Topological sort, you sort the courses and if there are cycles, you know you can't complete
 * otherwise you check courseOrder == numCourses.
 */

class Course{
    public int preq;
    public int course;
    public Course(int preq,int course){
        this.preq=preq;
        this.course=course;
    }
}

public class CanFinishCourses {
    
     public static boolean canFinish(int numCourses, Course[] prerequisites) {
        Map<Integer, Set<Integer>> courseMap = new HashMap<>();
        Map<Integer, Integer> inDegree = new HashMap<>();

        // Initialize indegree of all courses
        for (int i = 0; i < numCourses; i++) {
            inDegree.put(i, 0);
            courseMap.put(i, new HashSet<>()); // ensure every course has adjacency list
        }

        // Build graph
        for (Course c : prerequisites) {
            //the requirement is simple if [a,b] where b must be done before a, it means it's a directed edge
            //b->a that's why we do preq->course, and for indegree wefollow the arrow i.e. a->count
            courseMap.get(c.preq).add(c.course);
            inDegree.put(c.course, inDegree.get(c.course) + 1);
        }

        // BFS Topological Sort
        Queue<Integer> queue = new ArrayDeque<>();
        int completedCourses = 0;

        // Add all courses with indegree 0
        for (Map.Entry<Integer, Integer> entry : inDegree.entrySet()) {
            if (entry.getValue() == 0) {
                queue.add(entry.getKey());     // FIXED
            }
        }

        while (!queue.isEmpty()) {
            int currCourse = queue.poll();
            completedCourses++;

            for (int nextCourse : courseMap.get(currCourse)) {
                inDegree.put(nextCourse, inDegree.get(nextCourse) - 1);

                if (inDegree.get(nextCourse) == 0) {
                    queue.add(nextCourse);
                }
            }
        }

        return completedCourses == numCourses;
    }

    public static void main(String[] args) {

        Course[] courses1 = {
                new Course(0, 1),
                new Course(1, 2)
        };

        boolean finishable = canFinish(3, courses1);

        System.out.println(finishable); // true
    }
}
