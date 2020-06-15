package String.P771_JewelsAndStones;

import java.util.HashSet;
import java.util.Set;

/**
 * @Data Structures:  HashMap
 * @Algorithms used:
 * @Time Complexity:   O(m+n)
 * @Space Complexity:  O(1)
 */
public class Solution771 {
    public int numJewelsInStones(String J, String S) {
        Set<Character> set = new HashSet<>();
        int result = 0;
        for (char j : J.toCharArray()) {
            set.add(j);
        }
        for (char s : S.toCharArray()) {
            if (set.contains(s))
                result++;
        }
        return result;
    }

    public static void main(String[] args) {
        Solution771 sol = new Solution771();
        String str1 = "aA";
        String str2 = "aAAbbbb";
        System.out.println(sol.numJewelsInStones(str1, str2));
    }
}