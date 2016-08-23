package String.P020_ValidParentheses;

import java.util.Stack;

/**
 * @Data Structures:   Stack
 * @Algorithms used:
 * @Time Complexity:   O(n)
 * @Space Complexity:  O(1)
 */
public class Solution020 {
    public boolean isValid(String s) {
        if (s.length() == 0)
            return true;

        Stack<Character> cache = new Stack<>();
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '(')
                cache.push(')');
            else if (s.charAt(i) == '{')
                cache.push('}');
            else if (s.charAt(i) == '[')
                cache.push(']');
            else if (cache.isEmpty() || cache.pop() != s.charAt(i))
                return false;
        }
        return cache.isEmpty();
    }

    public static void main(String[] args) {
        Solution020 sol = new Solution020();
        String str = "[]{}()";
        System.out.println(sol.isValid(str));
    }
}
