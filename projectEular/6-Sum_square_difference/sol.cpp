/**
 * @Time Complexity:    O(1)
 * @Space Complexity:   O(1)
 */

#include <cmath>
#include <cstdio>
#include <vector>
#include <map>
#include <iostream>
#include <algorithm>
using namespace std;

unsigned long long sumOfSquares(unsigned long long n) {
	return (n * (n + 1) * (2 * n + 1)) / 6;
}

unsigned long long squareOfSums(unsigned long long n) {
	return pow(n * (n + 1), 2) / 4;
}

int main() {
	/* Enter your code here. Read input from STDIN. Print output to STDOUT */
	int N;
	unsigned long long num = 0;
	cin >> N;
	for (int i = 1; i <= N; i++) {
		cin >> num;
		cout << squareOfSums(num) - sumOfSquares(num) << endl;
	}
	return 0;
}