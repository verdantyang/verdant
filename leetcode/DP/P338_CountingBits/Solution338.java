package DP.P338_CountingBits;

public class Solution338 {
    public static int[] countBits(int num) {
        int[] rets = new int[num + 1];
        rets[0] = 0;
        for (int i = 1; i <= num; i++)
            rets[i] = rets[i & (i - 1)] + 1;
        return rets;
    }

    public static void main(String[] args) {
        int[] rets = Solution338.countBits(5);
        for (int elem : rets)
            System.out.println(elem);
    }
}
