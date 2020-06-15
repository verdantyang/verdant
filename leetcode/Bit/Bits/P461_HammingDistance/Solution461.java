package Bit.Bits.P461_HammingDistance;

/**
 * @Data Structures:
 * @Algorithms used:
 * @Time Complexity:   O(logn)
 * @Space Complexity:  O(1)
 */
public class Solution461 {
    public int hammingDistance(int x, int y) {
        int hamming = 0;
        while (x != 0 || y != 0) {
            if (((x - (x >>> 1 << 1)) ^ (y - (y >>> 1 << 1))) == 1) {
                hamming += 1;
            }
            x >>= 1;
            y >>= 1;
        }
        return hamming;
    }

    public static void main(String[] args) {
        Solution461 sol = new Solution461();
        System.out.println(sol.hammingDistance(1, 4));
    }
}
