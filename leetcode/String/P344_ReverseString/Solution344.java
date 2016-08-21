package String.P344_ReverseString;

/**
 * @Data Structures:
 * @Algorithms:        two pointers
 *
 * @Time Complexity:    O(n^2)
 * @Space Complexity:   O(1)
 */
public class Solution344 {
    public static String reverseString(String s) {
        int len = s.length();
        char[] chars = s.toCharArray();
        for (int i = 0; i < len / 2; i++) {
            chars[i] = s.charAt(len - i - 1);
            chars[len - i - 1] = s.charAt(i);
        }
        return new String(chars);
    }

    public static void main(String[] args) {
        String str = "hello";
        System.out.println(Solution344.reverseString(str));
    }
}