package Array.P011_ContainerWithMostWater;

/**
 * @Data Structures:
 * @Algorithms used:  TwoPointers
 * @Time Complexity:   O(n)
 * @Space Complexity:  O(1)
 */
public class Solution011 {
    public int maxArea(int[] height) {
        int lo = 0;
        int hi = height.length - 1;
        int h;
        int res = 0;
        while (lo < hi) {
            h = height[lo] > height[hi] ? height[hi] : height[lo];
            res = res > h * (hi - lo) ? res : h * (hi - lo);
            while (height[lo] <= h && lo < hi)
                lo++;
            while (height[hi] <= h && lo < hi)
                hi--;
        }
        return res;
    }

    public static void main(String[] args) {
        Solution011 sol = new Solution011();
        int[] height = {3, 2, 4, 1};
        System.out.println(sol.maxArea(height));
    }
}
