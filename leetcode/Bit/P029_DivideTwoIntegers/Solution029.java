package Bit.P029_DivideTwoIntegers;

/**
 * @Data Structures:
 * @Algorithms used:
 * @Time Complexity:   O(n)
 * @Space Complexity:  O(1)
 */
public class Solution029 {
    public int divide(int dividend, int divisor) {
        boolean neg = (dividend > 0) ^ (divisor > 0);
        long posDividend = dividend > 0 ? (long) dividend : -(long) dividend;
        long posDivisor = divisor > 0 ? (long) divisor : -(long) divisor;
        if (posDivisor == 0)
            return Integer.MAX_VALUE;
        if ((posDividend == 0) || (posDividend < posDivisor))
            return 0;

        int msb = 0; //msb记录除数需要左移的位数
        for (; msb < 64; msb++) {
            if ((posDivisor << msb) >= posDividend)
                break;
        }
        long quotient = 0; //记录每次除法的商
        for (int i = msb; i >= 0; i--) {
            if ((posDivisor << i) > posDividend)
                continue;
            quotient |= (1L << i);
            posDividend -= (posDivisor << i);
        }
        if (neg)
            quotient = -quotient;
        if (quotient > Integer.MAX_VALUE || quotient < Integer.MIN_VALUE)
            return Integer.MAX_VALUE;
        return (int) quotient;
    }

    public static void main(String[] args) {
        Solution029 sol = new Solution029();
        System.out.println(sol.divide(-2147483648, -1));
    }
}
