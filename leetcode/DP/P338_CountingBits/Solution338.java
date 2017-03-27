package DP.P338_CountingBits;

/**
 * @Data Structures:
 * @Algorithms used:  DP
 * @Time Complexity:   O(n)
 * @Space Complexity:  O(n)
 */
public class Solution338 {
    public int[] countBits(int num) {
        int[] rets = new int[num + 1];
        rets[0] = 0;
        for (int i = 1; i <= num; i++)
            rets[i] = rets[i & (i - 1)] + 1;
        return rets;
    }

    public static void main(String[] args) {
        Solution338 sol = new Solution338();
        int[] rets = sol.countBits(5);
        for (int elem : rets)
            System.out.println(elem);
    }
}
