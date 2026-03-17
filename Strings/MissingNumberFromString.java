package Strings;

public class MissingNumberFromString {

    public static int findMissing(String s, int n) {
        boolean[] used = new boolean[n + 1];
        int maxDigits = String.valueOf(n).length(); // max digits for n
        return dfs(s, 0, used, n, maxDigits);
    }

    private static int dfs(String s, int index, boolean[] used, int n, int maxDigits) {
        // Base case
        if (index == s.length()) {
            int missing = -1;
            int count = 0;
            for (int i = 1; i <= n; i++) {
                if (!used[i]) {
                    missing = i;
                    count++;
                }
            }
            return count == 1 ? missing : -1;
        }

        // Try parsing numbers of length 1 to maxDigits, len ranges from 1 to maxLength
        // First you an entire iteration with len==1 and then another with len==3 etc
        for (int len = 1; len <= maxDigits && index + len <= s.length(); len++) {
            int num = Integer.parseInt(s.substring(index, index + len));
            // Exclude numbers we've seen or that are greater than n
            if (num == 0 || num > n || used[num]) continue;

            // mark as used
            used[num] = true;
            // find the next number
            int res = dfs(s, index + len, used, n, maxDigits);

            // check if we are at the end
            if (res != -1) return res;

            used[num] = false;
        }

        return -1;
    }

    public static void main(String[] args) {
        String s = "123567";
        int n = 7;
        System.out.println(findMissing(s, n)); // Output: 4

        // Test with larger n
        String s2 = "1234567891011121314151617181920";
        int n2 = 21;
        System.out.println(findMissing(s2, n2)); // Output: 21
    }
}