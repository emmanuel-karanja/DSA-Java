package BinarySearch;
/**YYou are given:
     - An array boards[]
     - k painters

Rules (these are non-negotiable):

1. Each painter paints only contiguous boards (boards cannot be reshuffled or sorted)
2. Boards must be painted in order
3. A board cannot be split
4. All painters work at the same speed

Find the minimum possible time required to paint all boards.


INTUTION

What does “time” mean here?

All painters:
  - work at the same speed
  - paint board length per unit time

So if a painter paints boards whose total length is L, the time they take is L.

The total time of the job is:

Lowerbound: the maximum time taken by any painter. Someone has to pick the longest plank. This is the shortest
 time.
:Upperbound: We have to ask what if one painter was assigned all the planks? 

(because they work in parallel)”

Lowerbound: if I have 2 painters, and one has a board of length 5 and the other length 8, the unit time here is 
8 since the 5 one will need to wait for the 8 board painter to finish before taking another board?

i.e. the fastest time is the time it finish painting the longest board.

Upperbound: the time it takes one painter to paint all the boards.

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
                currentTotalTime = 0;  //reset here, it's extremely important
            }
            currentTotalTime+= b;  //increment the time

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
