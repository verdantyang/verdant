package Bit.P371_SumOfTwoIntegers;

/**
 * @Data Structures:
 * @Algorithms used:
 * @Time Complexity:   O(n)
 * @Space Complexity:  O(1)
 */
public class Solution371 {
    public int getSum(int a, int b) {
        int carry = 0;
        int sum = 0;
        do {
            sum = a ^ b;
            carry = (a & b) << 1;
            a = sum;
            b = carry;
        } while (carry != 0);
        return sum;
    }

    public static void main(String[] args) {
        Solution371 sol = new Solution371();
        System.out.println(sol.getSum(5, 6));
    }
}
