package DP.P070_ClimbingStairs;

/**
 * @Data Structures:
 * @Algorithms used:   DP
 * @Time Complexity:   O(n)
 * @Space Complexity:  O(1)
 * f[n]= f[n-1] + f[n-2]
 */
public class Solution070 {
    public int climbStairs(int n) {
        // base cases
        if (n <= 0) return 0;
        if (n == 1) return 1;
        if (n == 2) return 2;

        int one_step_before = 2;
        int two_steps_before = 1;
        int all_ways = 0;

        for (int i = 2; i < n; i++) {
            all_ways = one_step_before + two_steps_before;
            two_steps_before = one_step_before;
            one_step_before = all_ways;
        }
        return all_ways;
    }

    public static void main(String[] args) {
        Solution070 sol = new Solution070();
        System.out.println(sol.climbStairs(3));
    }
}
