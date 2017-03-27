package Math.P319_BulbSwitcher;

/**
 * @Data Structures:
 * @Algorithms used:
 * @Time Complexity:   O(logn)
 * @Space Complexity:  O(1)
 */
public class Solution319 {
    public int bulbSwitch(int n) {
        int counts = 0;
        for (int i = 1; i * i <= n; ++i) {
            ++counts;
        }
        return counts;
    }

    public static void main(String[] args) {
        Solution319 sol = new Solution319();
        System.out.println(sol.bulbSwitch(3));
    }
}
