/**
 * @Time Complexity:	O(n)
 * @Space Complexity:	O(1)
 */

/* Solution */
int hammingWeight(uint32_t n) {
	int hw = 0;
	while (n != 0) {
		hw += n % 2;
		n /= 2;
	}
	return hw;
}

/* Test Case */
int main() {
}