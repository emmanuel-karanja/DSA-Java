package FenwickTrees;

/**
 * PROBLEM: "Global Rank-by-Score Leaderboard"
 * * STATEMENT:
 * Given a stream of millions of player scores (integers 1 to 1,000,000), 
 * implement a system that can:
 * 1. updateScore(score, delta): Add or remove players with a specific score.
 * 2. getRank(score): Return the player's rank (1-based) based on the current
 * global score distribution.
 *
 * INTUITION:
 * - A simple sorted list makes updates O(N). A Heap makes ranking O(N).
 * - Since the score range is fixed (1 to 1M), we treat the scores as 
 * indices in an array. 
 * - A Fenwick Tree (Binary Indexed Tree) allows us to store "prefix sums"
 * (counts of players with scores <= X) in O(log MaxScore).
 * - Rank calculation: TotalPlayers - PlayersWithHigherScore. 
 * - Using Bit Manipulation (i & -i) allows us to traverse the tree 
 * structure stored inside a flat array.
 * 
 * The "Senior" Notepad Review
 * 
 * The Array Choice: Notice we use int[]. In a gaming system, if totalPlayers exceeds $2^{31}-1$, you must pivot to long[].
 *  Mentioning this "overflow" check is a high-level signal.
 * 
 * .The Complexity: The time complexity is $O(\log M)$, where $M$ is the max score, not the number of players ($N$). 
 * This is critical! If $M=1,000,000$, the max steps for any operation is $\approx 20$.
 * Space: $O(M)$. We are trading memory for extreme speed.
 */

public class LeaderboardTracker {
    private final int[] bit; // Binary Indexed Tree
    private int totalPlayers;
    private final int maxScore;

    public LeaderboardTracker(int maxScore) {
        this.maxScore = maxScore;
        // Fenwick Trees are 1-indexed for bit manipulation
        this.bit = new int[maxScore + 1];
        this.totalPlayers = 0;
    }

    /**
     * Adds 'delta' number of players who achieved 'score'.
     * Time: O(log MaxScore)
     */
    public void updateScore(int score, int delta) {
        // Only update if the score is within range
        if (score <= 0 || score > maxScore) return;
        totalPlayers += delta;
        for (; score <= maxScore; score += score & -score) {
            bit[score] += delta;
        }
    }

    /**
     * Returns the number of players with score <= currentScore.
     * Time: O(log MaxScore)
     */
    private int getCountLessThanOrEqual(int score) {
        int count = 0;
        for (; score > 0; score -= score & -score) {
            count += bit[score];
        }
        return count;
    }

    /**
     * Rank 1 is the highest score.
     * Formula: (Players with strictly higher score) + 1
     */
    public int getRank(int score) {
        int playersWithSameOrLower = getCountLessThanOrEqual(score);
        int playersWithHigher = totalPlayers - playersWithSameOrLower;
        return playersWithHigher + 1;
    }
}
