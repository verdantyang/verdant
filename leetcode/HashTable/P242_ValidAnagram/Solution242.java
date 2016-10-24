package HashTable.P242_ValidAnagram;

/**
 * @Data Structures:
 * @Algorithms used:
 * @Time Complexity:   O(n)
 * @Space Complexity:  O(1)
 */
public class Solution242 {
    public boolean isAnagram(String s, String t) {
        if (s.length() != t.length())
            return false;
        long counts[] = new long[26];
        int len = s.length();
        for (int i = 0; i < len; i++) {
            counts[s.charAt(i) - 'a']++;
            counts[t.charAt(i) - 'a']--;
        }
        for (int i = 0; i < 26; i++) {
            if (counts[i] != 0)
                return false;
        }
        return true;
    }

    public static void main(String[] args) {
        Solution242 sol = new Solution242();
        System.out.println(sol.isAnagram("abc", "cba"));
        System.out.println(sol.isAnagram("abe", "cba"));
    }
}
