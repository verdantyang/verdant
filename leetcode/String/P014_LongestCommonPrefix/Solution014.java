package String.P014_LongestCommonPrefix;

/**
 * @Data Structures:
 * @Algorithms used:
 * @Time Complexity:   O(n^2)
 * @Space Complexity:  O(1)
 */
public class Solution014 {
    public String longestCommonPrefix(String[] strs) {
        if (strs == null || strs.length == 0)
            return "";
        String prefix = strs[0];
        int len = 0;
        for (int i = 1; i < strs.length; i++) {
            if (prefix == null || prefix.length() == 0)
                return "";
            len = prefix.length() > strs[i].length() ? strs[i].length() : prefix.length();
            int j = 0;
            for (; j < len; j++) {
                if (prefix.charAt(j) != strs[i].charAt(j))
                    break;
            }
            prefix = prefix.substring(0, j);
        }
        return prefix;
    }

    public static void main(String[] args) {
        Solution014 sol = new Solution014();
        String[] strs = new String[3];
        strs[0] = "abc";
        strs[1] = "abcd";
        strs[2] = "aba";
        System.out.println(sol.longestCommonPrefix(strs));
    }
}
