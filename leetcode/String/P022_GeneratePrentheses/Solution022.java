package String.P022_GeneratePrentheses;

import java.util.*;

/**
 * @Data Structures:   Set
 * @Algorithms used:  Recursion
 * @Time Complexity:   O(n)
 * @Space Complexity:  O(1)
 */
public class Solution022 {
    public List<String> generateParenthesis(int n) {
        List<String> res = new ArrayList<>();
        Set<String> cache = new HashSet<>();
        if (n == 1) {
            res.add("()");
            return res;
        }
        List<String> resRecursion = generateParenthesis(n - 1);
        System.out.println(1);
        for (String s : resRecursion) {
            System.out.println(s);
            cache.add("(" + s + ")");
            cache.add("()" + s);
            cache.add(s + "()");
        }
        for (String s : cache)
            res.add(s);
        return res;
    }

    public static void main(String[] args) {
        Solution022 sol = new Solution022();
        for (String s : sol.generateParenthesis(5))
            System.out.println(1);
    }
}
