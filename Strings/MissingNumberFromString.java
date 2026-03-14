package Strings;


public class MissingNumberFromString {

    public static int findMissing(String s, int n) {
        boolean[] used = new boolean[n + 1];
        return dfs(s, 0, used, n);
    }

    private static int dfs(String s, int index, boolean[] used, int n) {

        // Base condition 
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

        int num = 0;

        for (int len = 1; len <= 2 && index + len <= s.length(); len++) {

            num = Integer.parseInt(s.substring(index, index + len));

            if (num == 0 || num > n || used[num])
                continue;

            used[num] = true;

            int res = dfs(s, index + len, used, n);
            if (res != -1)
                return res;

            used[num] = false;
        }

        return -1;
    }

    public static void main(String[] args) {

        String s = "123567";
        int n = 7;

        System.out.println(findMissing(s, n)); // Output: 4
    }
}