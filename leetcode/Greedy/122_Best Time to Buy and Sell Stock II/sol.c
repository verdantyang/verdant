/**
 * @Time Complexity:	O(n)
 * @Space Complexity:	O(1)
 */

/* Solution */
int maxProfit(int* prices, int pricesSize) {
	if (pricesSize == 1)
		return 0;
	int sum = 0;
	for (int i = 0; i < pricesSize - 1; i++) {
		if (prices[i + 1] > prices[i])
			sum += prices[i + 1] - prices[i];
	}
	return sum;
}

/* Test Case */
int main() {
}