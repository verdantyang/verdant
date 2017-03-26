package Bit.P318_MaximumProductOfWordLengths;

import java.util.HashMap;
import java.util.Map;

/**
 * @Data Structures:
 * @Algorithms used:
 * @Time Complexity:   O(n)
 * @Space Complexity:  O(1)
 */
public class Solution318 {
    public int maxProduct(String[] words) {
        Map<Integer, Integer> maxLen = new HashMap<>();
        int result = 0;
        for (String word : words) {
            int mask = 0;
            for (char c : word.toCharArray())
                mask |= 1 << (c - 'a');
            maxLen.put(mask, Math.max(maxLen.get(mask) == null ? 0 : maxLen.get(mask), word.length()));
            for (Integer maskElem : maxLen.keySet())
                if ((mask & maskElem) == 0)
                    result = Math.max(result, word.length() * maxLen.get(maskElem));
        }
        return result;
    }

    public static void main(String[] args) {
    }
}
