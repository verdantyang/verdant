/**
 * @Time Complexity: O(n)
 * @Space Complexity: O(2n)
 */
#include <string.h>
#include <stdio.h>

/* Solution */
int titleToNumber(char* s) {
	int res = 0;
	for (int i = 0; i < strlen(s); i++) {
		res = res * 26 + s[i] - 64;
	}
	return res;
}

/* Test Case */
int main() {
	char in[10] = "AA";
	printf("%d\n", titleToNumber(in));
}
