package String.P383_RansomNote;

/**
 * @Data Structures:  HashMap
 * @Algorithms used:
 * @Time Complexity:   O(m+n)
 * @Space Complexity:  O(1)
 */
public class Solution383 {
    public static boolean canConstruct(String ransomNote, String magazine) {
        int counts[] = new int[26];
        for (int i = 0; i < magazine.length(); i++)
            counts[magazine.charAt(i) - 'a']++;
        for (int i = 0; i < ransomNote.length(); i++)
            if (--counts[ransomNote.charAt(i) - 'a'] < 0)
                return false;
        return true;
    }

    public static void main(String[] args) {
        String str1 = "abc";
        String str2 = "aab";
        System.out.println(Solution383.canConstruct(str1, str2));
    }
}