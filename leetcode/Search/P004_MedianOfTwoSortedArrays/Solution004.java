package Search.P004_MedianOfTwoSortedArrays;

/**
 * @Time Complexity:    O(logn)
 * @Space Complexity:   O(1)
 */
public class Solution004 {
    private static int findKth(int[] A, int aStart, int[] B, int bStart, int k) {
        if (aStart >= A.length) {
            return B[bStart + k - 1];
        }
        if (bStart >= B.length) {
            return A[aStart + k - 1];
        }
        if (k == 1)
            return Math.min(A[aStart], B[bStart]);

        int aMid = aStart + k / 2 - 1;
        int bMid = bStart + k / 2 - 1;
        int aValue = aMid < A.length ? A[aMid] : Integer.MAX_VALUE;
        int bValue = bMid < B.length ? B[bMid] : Integer.MAX_VALUE;

        if (aValue < bValue) {
            return findKth(A, aMid + 1, B, bStart, k - k / 2);
        } else {
            return findKth(A, aStart, B, bMid + 1, k - k / 2);
        }
    }

    public static double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int m = nums1.length;
        int n = nums2.length;
        if ((m + n) % 2 == 0) {
            return (findKth(nums1, 0, nums2, 0, (m + n) / 2) + findKth(nums1, 0, nums2, 0, (m + n) / 2 + 1)) / 2.0;
        } else {
            return findKth(nums1, 0, nums2, 0, (m + n) / 2 + 1);
        }
    }

    public static void main(String[] args) {
        int[] nums1 = {1, 2};
        int[] nums2 = {3};
        System.out.println(Solution004.findMedianSortedArrays(nums1, nums2));
    }
}
