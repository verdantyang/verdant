package String.P058_LengthOfLastWord;

/**
 * @Data Structures:
 * @Algorithms used:  TwoPointer
 * @Time Complexity:   O(n)
 * @Space Complexity:  O(1)
 */
public class Solution058 {
    public int lengthOfLastWord(String s) {
        if (s == null || s.trim().length() == 0)
            return 0;
        else
            return s.trim().length() - s.trim().lastIndexOf(" ") - 1;
    }

    public static void main(String[] args) {
        Solution058 sol = new Solution058();
        String str = "abc hello";
        System.out.println(sol.lengthOfLastWord(str));
    }
}