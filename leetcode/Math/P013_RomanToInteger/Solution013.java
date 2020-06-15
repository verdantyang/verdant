package Math.P013_RomanToInteger;

import java.util.HashMap;

/**
 * @Data Structures:
 * @Algorithms used:
 * @Time Complexity:   O(n)
 * @Space Complexity:  O(1)
 */
public class Solution013 {
    public int romanToInt(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        HashMap<Character, Integer> map = new HashMap<>();
        map.put('I', 1);
        map.put('V', 5);
        map.put('X', 10);
        map.put('L', 50);
        map.put('C', 100);
        map.put('D', 500);
        map.put('M', 1000);
        int num = 0;
        num += map.get(s.charAt(0));
        for (int i = 1; i < s.length(); i++) {
            if (map.get(s.charAt(i)) <= map.get(s.charAt(i - 1))) {
                num += map.get(s.charAt(i));
            } else {
                num = num - 2 * map.get(s.charAt(i - 1)) + map.get(s.charAt(i));
            }
        }
        return num;
    }
}
