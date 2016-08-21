package String.P003_LongestSubstringWithoutRepeatingCharacters;

import java.util.HashMap;
import java.util.Map;

/**
 * @Time Complexity:    O(n)
 * @Space Complexity:   O(n)
 */
public class Solution003 {
    public static int lengthOfLongestSubstring(String s) {
        int cursor = 0;
        int ret = 0;
        int counts = 0;
        Map<Character, Integer> pos = new HashMap<>();

        for (int i = 0; i < s.length(); i++) {
            if (pos.containsKey(s.charAt(i)) && pos.get(s.charAt(i)) >= cursor) {
                cursor = pos.get(s.charAt(i)) + 1;
                pos.put(s.charAt(i), i);
                ret = ret > counts ? ret : counts;
                counts = i - cursor + 1;
            } else {
                pos.put(s.charAt(i), i);
                counts++;
                ret = ret > counts ? ret : counts;
            }
        }
        return ret;
    }

    public static void main(String[] args) {
        String str = "abcabcbb";
        String str1 = "aab";
        System.out.println(Solution003.lengthOfLongestSubstring(str1));
    }
}
