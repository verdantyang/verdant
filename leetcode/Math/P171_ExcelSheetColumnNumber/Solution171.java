package Math.P171_ExcelSheetColumnNumber;

/**
 * @Data Structures:
 * @Algorithms used:
 * @Time Complexity:   O(n)
 * @Space Complexity:  O(1)
 */
public class Solution171 {
    public int titleToNumber(String s) {
        int result = 0;
        for (int i = 0; i < s.length(); i++) {
            result = result * 26 + s.charAt(i) - 'A' + 1;
        }
        return result;
    }

    public static void main(String[] args) {
        Solution171 sol = new Solution171();
        System.out.println(sol.titleToNumber("AA"));
    }
}
