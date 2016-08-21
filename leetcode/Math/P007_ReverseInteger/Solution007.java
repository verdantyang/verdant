package Math.P007_ReverseInteger;

/**
 * @Time Complexity:    O(n)
 * @Space Complexity:   O(1)
 */
public class Solution007 {
    public static int reverse(int x) {
        long ret = 0;
        while (x != 0) {
            ret = ret * 10 + (x % 10);
            if (ret > Integer.MAX_VALUE || ret < Integer.MIN_VALUE) {
                return 0;
            }
            x /= 10;
        }
        return (int) ret;
    }

    public static void main(String[] args) {
        System.out.println(Solution007.reverse(1534236469));
    }
}
