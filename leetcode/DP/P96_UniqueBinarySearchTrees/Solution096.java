package DP.P96_UniqueBinarySearchTrees;

import java.util.ArrayList;
import java.util.List;

/**
 * @Data Structures:
 * @Algorithms used:  DP
 * @Time Complexity:
 * @Space Complexity:  O(n)
 */
public class Solution096 {

    public int numTrees(int n) {
        int[] recur = new int[n];
        recur[0] = recur[1] = 1;
        for (int i = 2; i <= n; ++i) {
            for (int j = 1; j <= i; ++j) {
                recur[i] += recur[j - 1] * recur[i - j];
            }
        }
        return recur[n];
    }

    public static void main(String[] args) {
        Solution096 sol = new Solution096();
        System.out.println(sol.numTrees(3));
    }
}
