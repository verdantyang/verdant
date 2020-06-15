package String.P003_LongestSubstringWithoutRepeatingCharacters;

import java.util.HashMap;
import java.util.Map;

/**
 * Longest Substring Without Repeating Characters
 * 引入一个类似字串窗口的cursor
 *
 * @Data Structures:   Map
 * @Algorithms used:
 * @Time Complexity:   O(n)
 * @Space Complexity:  O(n)
 */
public class Solution003 {
    public int lengthOfLongestSubstring(String s) {
        int cursor = 0;
        int ret = 0;
        int counts = 0;
        Map<Character, Integer> pos = new HashMap<>();

        for (int i = 0; i < s.length(); i++) {
            if (pos.containsKey(s.charAt(i)) && pos.get(s.charAt(i)) >= cursor) {
                cursor = pos.get(s.charAt(i)) + 1;
                counts = i - cursor + 1;
            } else {
                counts++;
            }
            pos.put(s.charAt(i), i);
            ret = ret > counts ? ret : counts;
        }
        return ret;
    }

    public static void main(String[] args) {
        Solution003 sol = new Solution003();
        String str = "abcabcbb";
//        String str1 = "aab";
        System.out.println(sol.lengthOfLongestSubstring(str));
    }
}
