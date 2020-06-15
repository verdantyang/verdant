package String.P005_LongestPalindromicSubstring;

/**
 * @Data Structures:
 * @Algorithms used:  TwoPointer
 * @Time Complexity:   O(n^2)
 * @Space Complexity:  O(1)
 */
public class Solution005 {

    private String extendPalindrome(String s, int i, int j) {
        int lo = i;
        int maxLen = 0;
        while (i >= 0 && j < s.length() && s.charAt(i) == s.charAt(j)) {
            i--;
            j++;
        }
        if (j - i - 1 > maxLen) {
            lo = i + 1;
            maxLen = j - i - 1;
        }
        return s.substring(lo, lo + maxLen);
    }

    public String longestPalindrome(String s) {
        if (s.length() < 2)
            return s;
        String result = "";
        for (int i = 0; i < s.length(); i++) {
            String even = extendPalindrome(s, i, i);
            String odd = extendPalindrome(s, i, i + 1);
            result = result.length() > even.length() ? result : even;
            result = result.length() > odd.length() ? result : odd;
        }
        return result;
    }

    public static void main(String[] args) {
        Solution005 sol = new Solution005();
//        String str = "ac";
        String str = "ccaabaaff";
        System.out.println(sol.longestPalindrome(str));
    }
}
