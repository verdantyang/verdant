package DP.P010_RegularExpressionMatching;

public class Solution010 {

    /**
     * @Data Structures:
     * @Algorithms used:  Recursion
     * @Time Complexity:   O((m*n)^2)
     * @Space Complexity:  O(1)
     */
    public boolean isMatch(String s, String p) {
        if (p.isEmpty())
            return s.isEmpty();
        if (p.length() > 1 && '*' == p.charAt(1))
            return isMatch(s, p.substring(2)) ||
                    !s.isEmpty() && (s.charAt(0) == p.charAt(0) || '.' == p.charAt(0)) && isMatch(s.substring(1), p);
        else
            return !s.isEmpty() && (s.charAt(0) == p.charAt(0) || '.' == p.charAt(0)) && isMatch(s.substring(1), p.substring(1));
    }

    /**
     * @Data Structures:
     * @Algorithms used:   DP
     * @Time Complexity:   O(m*n)
     * @Space Complexity:  O(m*n)
     * f[i][j]: if s[0..i-1] matches p[0..j-1]
     * 1）if p[j - 1] != '*'
     * f[i][j] = f[i - 1][j - 1] && s[i - 1] == p[j - 1]
     * 2）if p[j - 1] == '*'
     * denote p[j - 2] with x，f[i][j] is true if any of the following is true
     * a) "x*" repeats 0 time and matches empty: f[i][j - 2]
     * b) "x*" repeats >= 1 times and matches "x*x": s[i - 1] == x && f[i - 1][j]
     * '.' matches any single character
     */
    public boolean isMatch2(String s, String p) {
        int m = s.length(), n = p.length();
        boolean f[][] = new boolean[m + 1][n + 1];

        f[0][0] = true;
        for (int i = 1; i <= m; i++)
            f[i][0] = false;
        // p[0.., j - 3, j - 2, j - 1] matches empty if p[j - 1] is '*' and p[0..j - 3] matches empty
        for (int j = 1; j <= n; j++)
            f[0][j] = j > 1 && '*' == p.charAt(j - 1) && f[0][j - 2];

        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (p.charAt(j - 1) != '*')
                    f[i][j] = f[i - 1][j - 1] && (s.charAt(i - 1) == p.charAt(j - 1) || '.' == p.charAt(j - 1));
                else
                    f[i][j] = f[i][j - 2] || (s.charAt(i - 1) == p.charAt(j - 2) || '.' == p.charAt(j - 2)) && f[i - 1][j];
            }
        }
        return f[m][n];
    }

    public static void main(String[] args) {
        Solution010 sol = new Solution010();
        String str = "aa";
        String pattern = "a";
        System.out.println(sol.isMatch(str, pattern));
        System.out.println(sol.isMatch2(str, pattern));
    }
}
