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
 *    - dpSize[mask] = size of the smallest team that covers skills in 'mask'
 *    - Base case: dpSize[0] = 0 (no skills → empty team)
 *
 * 4. Transition:
 *    - For each person i:
 *        Freeze the previous DP state
 *        For each existing mask:
 *            newMask = mask | personSkillMask
 *            If using person i improves the team size:
 *                update dpSize[newMask]
 *                record parent pointers for reconstruction
 *
 * 5. Result:
 *    - dp[fullMask], where fullMask = (1 << m) - 1
 *
 * Key Invariant:
 * --------------
 * - DP(mask) for person i must only depend on states that existed
 *   *before* considering person i.
 *
 */
public class SmallestSufficientTeam {

    public static int[] smallestSufficientTeam(String[] reqSkills, List<List<String>> people) {
        int m = reqSkills.length;
        int n = people.size();

        // Map each required skill to a bit position
        Map<String, Integer> skillToId = new HashMap<>();
        for (int i = 0; i < m; i++) {
            skillToId.put(reqSkills[i], i);
        }

        int FULL_MASK = (1 << m) - 1;

        // dpSize[mask] = minimum number of people needed to cover 'mask'
        int[] dpSize = new int[1 << m];
        Arrays.fill(dpSize, Integer.MAX_VALUE / 2);
        dpSize[0] = 0;

        // Parent pointers for reconstruction
        int[] parentMask = new int[1 << m];
        int[] parentPerson = new int[1 << m];

        // Iterate through each person
        // The main question is per iteration: Can we add this person to the set? 
        // We check if we can actually minimize the dp[mask | personMask] value if w add them?
        for (int i = 0; i < n; i++) {
            int personSkillMask = 0;
            for (String skill : people.get(i)) {
                if (skillToId.containsKey(skill)) {
                    personSkillMask |= (1 << skillToId.get(skill));
                }
            }

            // Skip people who contribute no required skills
            if (personSkillMask == 0) continue;

            // Freeze previous DP state to avoid reusing the same person multiple times
            int[] prevDp = dpSize.clone();

            for (int mask = 0; mask <= FULL_MASK; mask++) {
                int nextMask = mask | personSkillMask;
                int newSize = prevDp[mask] + 1;

                // Update only if this person gives a smaller team for nextMask
                if (newSize < dpSize[nextMask]) {
                    dpSize[nextMask] = newSize;    // equivalent to Math.min(dpSize[nextMask], newSize)
                    parentMask[nextMask] = mask;   // track how we got here
                    parentPerson[nextMask] = i;    // track which person contributed
                }
            }
        }

        // Reconstruct the team using parent pointers
        List<Integer> result = new ArrayList<>();
        int currMask = FULL_MASK;
        while (currMask != 0) {
            result.add(parentPerson[currMask]);
            currMask = parentMask[currMask];
        }

        return result.stream().mapToInt(i -> i).toArray();
    }

    // ------------------------------------------------------------
    // Driver
    // ------------------------------------------------------------
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
        // Valid outputs include [0,1,2] or [1,3], depending on DP order
    }
}