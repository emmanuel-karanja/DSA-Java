

import java.util.*;

public class SmallestSufficientTeam2D {

    /**
     * Forward-looking 2D DP version:
     * ------------------------------
     * dp[mask][i] = minimum team size to cover 'mask' using first i people
     * Instead of checking prevMask, we compute nextMask = mask | personMask[i]
     * and update dp[nextMask][i] accordingly.
     */
    public static int[] smallestSufficientTeam(String[] reqSkills, List<List<String>> people) {
        int m = reqSkills.length;
        int n = people.size();
        int FULL_MASK = (1 << m) - 1;

        // Map skill -> index, co-ordination compression
        Map<String, Integer> skillToId = new HashMap<>();
        for (int i = 0; i < m; i++) {
            skillToId.put(reqSkills[i], i);
        }

        // Precompute person masks
        int[] personMask = new int[n];
        for (int i = 0; i < n; i++) {
            // Start with a clean slate
            int mask = 0;
            for (String skill : people.get(i)) {
                // Check if the skill is available
                if (skillToId.containsKey(skill)) {
                    mask |= (1 << skillToId.get(skill));
                }
            }
            personMask[i] = mask;
        }

        // DP table
        int[][] dp = new int[1 << m][n + 1];
        for (int mask = 0; mask <= FULL_MASK; mask++) {
            Arrays.fill(dp[mask], Integer.MAX_VALUE / 2);
        } 
        dp[0][0] = 0;

        // Parent pointers for reconstruction
        int[][] parentMask = new int[1 << m][n + 1];
        int[][] parentPerson = new int[1 << m][n + 1];

        // Forward DP
        for (int i = 0; i < n; i++) {
            int pMask = personMask[i];  // Get person i's skillset mask
            for (int mask = 0; mask <= FULL_MASK; mask++) {
                if (dp[mask][i] < Integer.MAX_VALUE / 2) {  //
                    // Option 1: skip person i, We do it like this since will update backlinks as well
                    if (dp[mask][i] < dp[mask][i + 1]) { 
                        dp[mask][i + 1] = dp[mask][i];

                        // Backlinks update
                        parentMask[mask][i + 1] = mask;
                        parentPerson[mask][i + 1] = -1;
                    }

                    // Option 2: take person i, the trick here is that we take all the people and their skills in mask
                    // And add the current person there only if they upgrade the mask
                    int nextMask = mask | pMask;

                    // This is in lieu of Math.min
                    if (dp[mask][i] + 1 < dp[nextMask][i + 1]) {
                        dp[nextMask][i + 1] = dp[mask][i] + 1;

                        //Update backlinks
                        parentMask[nextMask][i + 1] = mask;
                        parentPerson[nextMask][i + 1] = i;
                    }
                }
            }
        }

        // Reconstruct team
        List<Integer> result = new ArrayList<>();
        int mask = FULL_MASK;
        int i = n;
        while (mask > 0 && i > 0) {
            if (parentPerson[mask][i] != -1) {
                result.add(parentPerson[mask][i]);
                mask = parentMask[mask][i];  // Look at the one before it, i.e. the parent of this
            }
            i--;
        }

        Collections.reverse(result);
        return result.stream().mapToInt(x -> x).toArray();
    }

    public static void main(String[] args) {
        String[] reqSkills = {"java", "python", "aws"};
        List<List<String>> people = List.of(
                List.of("java"),
                List.of("python"),
                List.of("aws"),
                List.of("java", "aws")
        );

        int[] team = smallestSufficientTeam(reqSkills, people);
        System.out.println("Smallest sufficient team indices (forward DP): " + Arrays.toString(team));
    }
}