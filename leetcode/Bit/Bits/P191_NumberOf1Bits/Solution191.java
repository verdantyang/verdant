package Bit.Bits.P191_NumberOf1Bits;

/**
 * @Data Structures:
 * @Algorithms used:
 * @Time Complexity:   O(n)
 * @Space Complexity:  O(1)
 */
public class Solution191 {
    public int hammingWeight(long n) {
        int weight = 0;
        while (n != 0) {
            weight += (n & 1);
            n = n >>> 1;
        }
        return weight;
    }

    public static void main(String[] args) {
        Solution191 sol = new Solution191();
        System.out.println(sol.hammingWeight(2147483648L));
    }
}
