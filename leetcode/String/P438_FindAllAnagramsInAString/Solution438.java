package String.P438_FindAllAnagramsInAString;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Data Structures:  HashMap
 * @Algorithms used:
 * @Time Complexity:   O(m+n)
 * @Space Complexity:  O(1)
 */
public class Solution438 {
    public List<Integer> findAnagrams(String s, String p) {
        List<Integer> result = new ArrayList<>();
        if (s.length() < p.length())
            return result;
        Map<Character, Integer> map = new HashMap<>();
        for (Character c : p.toCharArray()) {
            map.put(c, map.getOrDefault(c, 0) + 1);
        }
        int counter = map.size();
        int begin = 0, end = 0;
        while (end < s.length()) {
            char ec = s.charAt(end);
            if (map.containsKey(ec)) {
                map.put(ec, map.get(ec) - 1);
                if (map.get(ec) == 0)
                    counter--;
            }
            end++;
            while (counter == 0) {
                char bc = s.charAt(begin);
                if (map.containsKey(bc)) {
                    map.put(bc, map.get(bc) + 1);
                    if (map.get(bc) > 0)
                        counter++;
                }
                if (end - begin == p.length()) {
                    result.add(begin);
                }
                begin++;
            }
        }
        return result;
    }

    public static void main(String[] args) {
        Solution438 sol = new Solution438();
        String str1 = "cbaebabacd";
        String str2 = "abc";
        System.out.println(sol.findAnagrams(str1, str2));
    }
}