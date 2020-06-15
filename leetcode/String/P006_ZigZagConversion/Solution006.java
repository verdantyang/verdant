package String.P006_ZigZagConversion;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;

/**
 * @Data Structures:   Queue
 * @Algorithms used:
 * @Time Complexity:   O(n)
 * @Space Complexity:  O(m)
 */
public class Solution006 {
    public String convert(String s, int numRows) {
        int row = 0;
        boolean down = true;
        StringBuilder sb = new StringBuilder();
        Map<Integer, Queue<Character>> queues = new HashMap<>();
        for (int i = 0; i < numRows; i++)
            queues.put(i, new ArrayDeque<>());

        if (numRows == 1)
            return s;
        for (int i = 0; i < s.length(); i++) {
            row %= numRows;
            queues.get(row).offer(s.charAt(i));
            if (down) {
                row++;
                if (row == numRows) {
                    down = false;
                    row -= 2;
                }
            } else {
                row--;
                if (row < 0) {
                    down = true;
                    row += 2;
                }
            }
        }

        for (int i = 0; i < numRows; i++) {
            while (queues.get(i).size() != 0)
                sb.append(queues.get(i).poll());
        }

        return sb.toString();
    }

    public static void main(String[] args) {
        Solution006 sol = new Solution006();
        String str = "PAYPALISHIRING";
        String str1 = "ABCDE";
        System.out.println(sol.convert(str1, 3));
    }
}
