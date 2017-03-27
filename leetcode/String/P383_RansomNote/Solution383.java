package String.P383_RansomNote;

/**
 * @Data Structures:  HashMap
 * @Algorithms used:
 * @Time Complexity:   O(m+n)
 * @Space Complexity:  O(1)
 */
public class Solution383 {
    public boolean canConstruct(String ransomNote, String magazine) {
        int counts[] = new int[26];
        for (int i = 0; i < magazine.length(); i++)
            counts[magazine.charAt(i) - 'a']++;
        for (int i = 0; i < ransomNote.length(); i++)
            if (--counts[ransomNote.charAt(i) - 'a'] < 0)
                return false;
        return true;
    }

    public static void main(String[] args) {
        Solution383 sol = new Solution383();
        String str1 = "abc";
        String str2 = "aab";
        System.out.println(sol.canConstruct(str1, str2));
    }
}