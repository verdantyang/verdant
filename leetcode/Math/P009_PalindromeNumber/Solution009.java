package Math.P009_PalindromeNumber;

/**
 * @Time Complexity:    O(n)
 * @Space Complexity:   O(1)
 */
public class Solution009 {
    public static boolean isPalindrome(int x) {
        if (x < 0 || (x != 0 && x % 10 == 0))
            return false;
        int rev = 0;
        while (x > rev) {
            rev = rev * 10 + (x % 10);
            x /= 10;
        }
        return x == rev || x == rev / 10;
    }

    public static void main(String[] args) {
        System.out.println(Solution009.isPalindrome(12321));
    }
}
