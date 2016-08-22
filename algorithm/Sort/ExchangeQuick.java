package Sort;

/**
 * @Data Structures:
 * @Algorithms: two pointer, partition
 * @Time Complexity:   O(nlogn)
 * @Space Complexity:  O(1)
 */
public class ExchangeQuick {
    private static int partition(int unsorted[], int lo, int hi) {
        int pivot = unsorted[lo];
        while (lo < hi) {
            if (unsorted[hi] > pivot)
                hi--;
            unsorted[lo] = unsorted[hi];

            if (unsorted[lo] < pivot)
                lo++;
            unsorted[hi] = unsorted[lo];
        }
        unsorted[lo] = pivot;
        return lo;
    }

    public static void quickSort(int[] nums, int lo, int hi) {
        if (lo < hi) {
            int loc = partition(nums, lo, hi);
            quickSort(nums, lo, loc);
            quickSort(nums, loc + 1, hi);
        }
    }

    public static void main(String[] args) {
        int[] nums = {5, 2, 3, 1, 6, 7};
        quickSort(nums, 0, nums.length - 1);
        for (int i = 0; i < nums.length; i++) {
            System.out.print(nums[i] + ",");
        }
    }
}
