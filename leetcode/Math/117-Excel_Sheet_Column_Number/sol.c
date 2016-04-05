/**
 * @Time Complexity:	O(n)
 * @Space Complexity:	O(2n)
 */
#include <string.h>
#include <stdio.h>

/* Solution */
int titleToNumber(char* s) {
	int ret = 0;
	for (int i = 0; i < strlen(s); i++) {
		ret = ret * 26 + s[i] - 64;
	}
	return ret;
}

/* Test Case */
int main() {
	char in[10] = "AA";
	printf("%d\n", titleToNumber(in));
}
