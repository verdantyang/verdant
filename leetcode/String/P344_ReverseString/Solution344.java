package String.P344_ReverseString;

/**
 * @Data Structures:
 * @Algorithms used:  TwoPointer
 * @Time Complexity:   O(n)
 * @Space Complexity:  O(1)
 */
public class Solution344 {
    public String reverseString(String s) {
        int len = s.length();
        char[] chars = s.toCharArray();
        for (int i = 0; i < len / 2; i++) {
            chars[i] = s.charAt(len - i - 1);
            chars[len - i - 1] = s.charAt(i);
        }
        return new String(chars);
    }

    public static void main(String[] args) {
        Solution344 sol = new Solution344();
        String str = "hello";
        System.out.println(sol.reverseString(str));
    }
}