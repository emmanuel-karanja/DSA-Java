package BinarySearch;
//**This is the modified binary search problem thematically identical to the CapacityToShipPackages, PaintersPartition,
// AllocateBooksPages. GOOGLE usually just marginally modifies the problem for interviews. It's one of the most repeated
// Google interview problems. */
public class KokoBananas {

    public static int minEatingSpeed(int[] piles, int H) {
        int left = 1, right = 0;
        for (int p : piles) right = Math.max(right, p);

        int answer = right;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (canEat(piles, H, mid)) {
                answer = mid;
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        return answer;
    }

    private static boolean canEat(int[] piles, int H, int speed) {
        int hours = 0;
        for (int p : piles) {
            hours += (p + speed - 1) / speed; // ceil division
            if (hours > H) return false;
        }
        return true;
    }

    public static void main(String[] args) {
        int[] piles = {3, 6, 7, 11};
        int H = 8;
        int result = minEatingSpeed(piles, H);
        System.out.println("Minimum eating speed = " + result); // Expected: 4
    }
}

