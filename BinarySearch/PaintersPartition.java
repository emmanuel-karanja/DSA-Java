package BinarySearch;
/**YYou are given:
     - An array boards[]
     - k painters

Rules (these are non-negotiable):

1.Each painter paints only contiguous boards (boards cannot be reshuffled or sorted)
2. Boards must be painted in order
3. A board cannot be split
4. All painters work at the same speed

The objective is to minimize the maximum time taken by any painter. Basically maximize the speed.


INTUTION

This is a binary search on the answer.

Lower bound = max(boards)
(a painter must paint the largest board)

PAINTERS: WILL BE AS SLOW AS THE PAINTER WITH THE LONGEST BOARD so, the lowerBound is the length of the
longestboard and max is all the boards.

And How long will one painter take to paint all of them? That'a the upper limit.


Upper bound = sum(boards)
(one painter paints everything)

The feasibility function:


“Can we paint all boards using ≤ k painters if each painter paints at most mid total length?”

This is monotonic, so binary search applies.

If order matters and partitions must be contiguous, think “binary search on the answer.”
*/
public class PaintersPartition {

    public static int minTime(int[] boards, int k) {
        int left = 0;
        int right = 0;

        for (int b : boards) {
            left = Math.max(left, b); // max board
            right += b;               // total length
        }

        int answer = right;

        while (left <= right) {
            int mid = left + (right - left) / 2;

            if (canPaint(boards, k, mid)) {
                answer = mid;
                right = mid - 1; // try smaller max time
            } else {
                left = mid + 1;  // need more time
            }
        }

        return answer;
    }

    private static boolean canPaint(int[] boards, int painters, int maxTimePerPainter) {
        int paintersUsed = 1;
        int currentTotalTime = 0;

        for (int b : boards) {
            if (currentTotalTime + b > maxTimePerPainter) {
                paintersUsed++;
                currentTotalTime = 0;
            }
            currentTotalTime+= b;

            if (paintersUsed > painters) {
                return false;
            }
        }

        return true;
    }

    public static void main(String[] args) {
        int[] boards = {10, 20, 30, 40};
        int painters = 2;
        int result = minTime(boards, painters);
        System.out.println("Minimum time to paint boards = " + result); // Expected: 60
    }
}
