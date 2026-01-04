package BinarySearch;

/**
 * PROBLEM: Allocate Books to Students
 *
 * Given:
 * - An array pages[] where pages[i] is the number of pages in the i-th book (in order)
 * - An integer students representing the number of students
 *
 * Constraints:
 * 1. Each student must be assigned **contiguous books**.
 * 2. Each book must be assigned to exactly **one student**.
 * 3. Each student must receive **at least one book**.
 *
 * Goal:
 * - Minimize the **maximum number of pages** assigned to a student.
 *
 * INTUITION:
 * LowerBound: The minimum possible maximum pages per student is the **largest single book**.
 *   (Because every student must read at least one book). Someone must read the biggest book no matterhow
 *   you assign them.
 * 
 * Upperbound: The maximum possible maximum pages per student is the **sum of all books**.
 *   (If a single student reads all books)
 * 
 * 
 * - This gives a **search range**: left = max(pages), right = sum(pages)
 *
 * BINARY SEARCH ON THE ANSWER:
 * - Try a candidate maxPages (mid)
 * - Check feasibility:
 *      Can we allocate books to students so that no student has more than mid pages?
 * - If feasible → try smaller maxPages (move right)
 * - If not feasible → need more pages per student (move left)
 *
 * FEASIBILITY FUNCTION:
 * - Assign books greedily to the current student until adding the next book
 *   would exceed maxPagesPerStudent
 * - Then move to the next student
 * - If we exceed the number of students → mid is not feasible
 *
 * TIME COMPLEXITY:
 * - O(n log(sum - max)) where n = number of books
 */
public class AllocateBookPages {

    public static int allocate(int[] pages, int students) {
        int left = 0;   // minimum possible maximum (the largest book)
        int right = 0;  // maximum possible maximum (sum of all books)

        for (int p : pages) {
            left = Math.max(left, p);
            right += p;
        }

        int answer = right;

        while (left <= right) {
            int mid = left + (right - left) / 2;

            if (canAllocate(pages, mid, students)) {
                // Feasible: try a smaller maximum
                answer = mid;
                right = mid - 1;
            } else {
                // Not feasible: need larger max
                left = mid + 1;
            }
        }

        return answer;
    }

    private static boolean canAllocate(int[] pages, int maxPagesPerStudent, int students) {
        int studentsUsed = 1;
        int currentTotalPages = 0;

        for (int p : pages) {
            if (currentTotalPages + p > maxPagesPerStudent) {
                // Current student cannot take this book, move to next
                studentsUsed++;
                currentTotalPages = 0;
            }

            currentTotalPages += p;

            if (studentsUsed > students) {
                return false; // Too many students required → not feasible
            }
        }

        return true; // Allocation possible within given number of students
    }

    public static void main(String[] args) {
        int[] books = {12, 34, 67, 90};
        int students = 2;

        int result = allocate(books, students);
        System.out.println("Minimum maximum pages = " + result); // Expected: 113
    }
}
