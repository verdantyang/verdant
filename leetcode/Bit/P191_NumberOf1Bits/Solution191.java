package Bit.P191_NumberOf1Bits;

/**
 * @Data Structures:
 * @Algorithms used:
 * @Time Complexity:   O(n)
 * @Space Complexity:  O(1)
 */
public class Solution191 {
    public static int hammingWeight(long n) {
        int weight = 0;
        while (n != 0) {
            weight += (n & 1);
            n = n >>> 1;
        }
        return weight;
    }

    public static void main(String[] args) {
        System.out.println(Solution191.hammingWeight(2147483648L));
    }
}
