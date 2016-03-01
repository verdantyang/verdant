/**
 * @Time Complexity:	O(n)
 * @Space Complexity:	O(1)
 */

/* Solution */
int singleNumber(int* nums, int numsSize) {
	int res = 0;
	for (int i = 0; i < numsSize; i++)
		res = res ^ nums[i];
	return res;
}

/* Test Case */
int main() {
}