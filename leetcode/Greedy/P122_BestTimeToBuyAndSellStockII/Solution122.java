package Greedy.P122_BestTimeToBuyAndSellStockII;

/**
 * @Data Structures:
 * @Algorithms used:
 * @Time Complexity:   O(n)
 * @Space Complexity:  O(1)
 */
public class Solution122 {
    public int maxProfit(int[] prices) {
        if (prices.length == 0)
            return 0;
        int sum = 0;
        for (int i = 0; i < prices.length - 1; i++) {
            if (prices[i + 1] > prices[i])
                sum += prices[i + 1] - prices[i];
        }
        return sum;
    }

    public static void main(String[] args) {
        Solution122 sol = new Solution122();
        System.out.println(sol.maxProfit(new int[]{1, 2, 2, 3}));
    }
}
