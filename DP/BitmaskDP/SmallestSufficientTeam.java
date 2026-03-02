package BitmaskDP;

import java.util.*;

/**
 * ============================================================
 * PROBLEM: SMALLEST SUFFICIENT TEAM
 * ============================================================
 *
 * Problem Statement:
 * -----------------
 * Given:
 * 1. A list of required skills: reqSkills[0..m-1]
 * 2. A list of people, where each person has a set of skills
 *
 * Goal:
 * - Find the smallest subset of people such that the union of their skills
 *   covers all required skills.
 *
 * Constraints:
 * - Multiple people may have overlapping skills.
 * - There may be multiple smallest teams; returning any valid one is acceptable.
 *
 *
 * Approach (Bitmask DP):
 * ---------------------
 * 1. Represent each skill as a bit in a bitmask of length m (number of skills).
 *    - For example, skill i is represented by 1 << i
 *
 * 2. For each person, compute a bitmask representing the skills they have.
 *
 * 3. DP state definition:
 *    - dp[mask] = smallest team (list of people indices) that covers all skills
 *      represented by bits set in 'mask'.
 *    - Base case: dp[0] = empty list (no skills required → empty team)
 *
 * 4. Transition:
 *    - For each person i with skill mask personMask:
 *        For each existing DP entry (mask, team):
 *            newMask = mask | personMask  // skills covered if we include this person
 *            If newMask is not in dp or including person i gives a smaller team:
 *                dp[newMask] = team + [i]
 *
 * 5. Result:
 *    - dp[fullMask], where fullMask = (1 << m) - 1 (all skills), gives
 *      the indices of the smallest sufficient team.
 *
 * Complexity:
 * -----------
 * - Number of DP states: 2^m (all subsets of skills)
 * - For each person, we update DP for all existing states → O(n * 2^m)
 * - Feasible for m ≤ 16-20
 *
 * Key Insights:
 * -------------
 * - Using bitmask allows representing all combinations of skills efficiently.
 * - DP ensures we **always keep the smallest team** for each skill combination.
 * - This approach naturally handles overlapping skills and multiple people efficiently.
 *
 */
public class SmallestSufficientTeam {

    public static int[] smallestSufficientTeam(String[] reqSkills, List<List<String>> people) {
        int m = reqSkills.length;
        int n = people.size();
        
        Map<String, Integer> skillToId = new HashMap<>();
        for (int i = 0; i < m; i++) skillToId.put(reqSkills[i], i);

        // dp[mask] stores the minimum size of the team for that mask
        int[] dpSize = new int[1 << m];
        Arrays.fill(dpSize, Integer.MAX_VALUE / 2);
        dpSize[0] = 0;

        // To reconstruct the team:
        // parentMask[mask] = the mask we came from
        // parentPerson[mask] = the person index added to get to this mask
        int[] parentMask = new int[1 << m];
        int[] parentPerson = new int[1 << m];

        for (int i = 0; i < n; i++) {
            int personSkillMask = 0;
            for (String s : people.get(i)) {
                if (skillToId.containsKey(s)) {
                    personSkillMask |= (1 << skillToId.get(s));
                }
            }
            if (personSkillMask == 0) continue; // Skip if person has no useful skills

            // Iterate through all masks to see if this person improves them
            for (int mask = 0; mask < (1 << m); mask++) {
                int nextMask = mask | personSkillMask;
                if (dpSize[nextMask] > dpSize[mask] + 1) {
                    dpSize[nextMask] = dpSize[mask] + 1;
                    parentMask[nextMask] = mask;
                    parentPerson[nextMask] = i;
                }
            }
        }

        // Reconstruct the result list from the parent pointers
        List<Integer> result = new ArrayList<>();
        int curr = (1 << m) - 1;
        while (curr > 0) {
            result.add(parentPerson[curr]);
            curr = parentMask[curr];
        }

        return result.stream().mapToInt(i -> i).toArray();
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
        System.out.println("Smallest sufficient team indices: " + Arrays.toString(team));
        // Output might be [0,1,2] or [1,3] depending on DP update order
    }
}