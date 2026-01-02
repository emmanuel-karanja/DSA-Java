package DP;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**You’re given a list of envelopes, each with a width and height.
One envelope can fit inside another only if both width and height are strictly larger.
Find the maximum number of envelopes you can nest (like Russian dolls).

INTUTION:
This feels like a DP problem:

GOAL: Find the maximumnumber of envelopes to nest
STATE: dp[i] maximum number of envelopes you can nest.
CHOICES: At i for all j's from 0 to i-1 compare h[i]<h[i] && w[i] <w[j], then we can takE
    dp[i]=Math.max(dp[i],dp[j]);

  *We are either increasing the streak or not
BASE CASE: At each given index, the max is itself 1.

We cannot find area since 6x6  > 7x5 and yet the second can't fit into the first due to the 7, order by n

NOTE: Notice: once envelopes are sorted by width, the DP only depends on height. i.e.we have collapsed
the state into a simple 1D case which is case. Box stacking does this too by using area.

*/

class Envelope{
    public int width;
    public int height;

    public Envelope(int height, int width){
        this.height=height;
        this.width=width;
    }

    public int getArea(){
        return width *height;
    }
}
public class RussianDollEnvelopes {
    
    public static int getEnvelopesCount(List<Envelope> envelopes) {

        if (envelopes == null || envelopes.isEmpty()) return 0;  // again the base case and not a base state.

        // 1. Sort Width Ascending. If widths equal, Height Descending.
        Collections.sort(envelopes, (a, b) -> {
            if (a.width != b.width) return a.width - b.width;
            return b.height - a.height; 
        });

        int n = envelopes.size();
        int[] dp = new int[n];
        int maxOverall = 0;

        for (int i = 0; i < n; i++) {
            dp[i] = 1; // Base case: every envelope is a sequence of 1
            for (int j = 0; j < i; j++) {
                // Since width is already handled by sorting (and the tie-breaker),
                // we only need to check height strictly increasing.
                if (envelopes.get(i).height > envelopes.get(j).height) {
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
            }
            maxOverall = Math.max(maxOverall, dp[i]);
        }
        return maxOverall;
    }
}
