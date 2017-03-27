package String.P005_LongestPalindromicSubstring;

/**
 * @Data Structures:
 * @Algorithms used:  TwoPointer
 * @Time Complexity:   O(n^2)
 * @Space Complexity:  O(1)
 */
public class Solution005 {
    private static int lo = 0;
    private static int maxLen = 0;

    private static void extendPalindrome(String s, int i, int j) {
        while (i >= 0 && j < s.length() && s.charAt(i) == s.charAt(j)) {
            i--;
            j++;
        }
        if (j - i - 1 > maxLen) {
            lo = i + 1;
            maxLen = j - i - 1;
        }
    }

    public String longestPalindrome(String s) {
        if (s.length() < 2)
            return s;
        for (int i = 0; i < s.length(); i++) {
            extendPalindrome(s, i, i);
            extendPalindrome(s, i, i + 1);
        }
        return s.substring(lo, lo + maxLen);
    }

    public static void main(String[] args) {
        Solution005 sol = new Solution005();
        String str = "aaabaaaa";
        String str1 = "aab";
        System.out.println(sol.longestPalindrome(str));
    }
}
