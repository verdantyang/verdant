package String.P028_ImplementStrStr;

/**
 * @Data Structures:
 * @Algorithms used:  KMP
 * @Time Complexity:   O(n+m)
 * @Space Complexity:  O(m)
 */
public class Solution028 {

    public int strStr(String haystack, String needle) {
        if (needle.length() == 0)
            return 0;
        int j = 0;
        int[] backIndex = this.getNext(needle);
        for (int i = 0; i < haystack.length(); i++) {
            while (j > 0 && haystack.charAt(i) != needle.charAt(j)) {
                j = backIndex[j];
            }
            if (haystack.charAt(i) == needle.charAt(j)) {
                j++;
            }
            if (j == needle.length()) {
                return i + 1 - needle.length();
            }
        }
        return -1;
    }

    private int[] getNext(String needle) {
        int[] backIndex = new int[needle.length() + 1];
        int j = 0;
//        backIndex[0] = 0;
        backIndex[1] = 0;
        for (int i = 1; i < needle.length(); i++) {
            while (j > 0 && needle.charAt(i) != needle.charAt(j)) { //回溯
                j = backIndex[j];
            }
            if (needle.charAt(i) == needle.charAt(j)) {
                j++;
            }
            backIndex[i + 1] = j;
        }
        return backIndex;
    }

    public static void main(String[] args) {
        Solution028 sol = new Solution028();
        System.out.println(sol.strStr("affaabcdabdafd", "abcdabceabcf"));
    }
}
