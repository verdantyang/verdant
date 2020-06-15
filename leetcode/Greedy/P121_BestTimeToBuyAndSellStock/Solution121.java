package Greedy.P121_BestTimeToBuyAndSellStock;

/**
 * @Data Structures:
 * @Algorithms used:
 * @Time Complexity:   O(n)
 * @Space Complexity:  O(1)
 */
public class Solution121 {
    public int maxProfit(int[] prices) {
        if (prices.length == 0)
            return 0;
        int max = 0;
        int low = 0;
        for (int i = 1; i < prices.length; i++) {
            if (prices[i] - prices[low] > max) {
                max = prices[i] - prices[low];
            }
            if (prices[i] < prices[low]) {
                low = i;
            }
        }
        return max;
    }

    public static void main(String[] args) {
        Solution121 sol = new Solution121();
        System.out.println(sol.maxProfit(new int[]{7, 6, 4, 3, 1}));
    }
}
