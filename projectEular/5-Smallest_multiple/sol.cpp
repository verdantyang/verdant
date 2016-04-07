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

int smallestMultiple(int num)  {
	int ret = 1;
	int factor = 1;
	int primeNow = 0;
	vector<int> primes = {2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37};
	for (int prime : primes) {
		primeNow = prime;
		if (prime > num)
			break;
		while (prime <= num) {
			factor = prime;
			prime *= primeNow;
		}
		ret *= factor;
	}
	return ret;
}

int main() {
	/* Enter your code here. Read input from STDIN. Print output to STDOUT */
	int N;
	int num = 0;
	smallestMultiple(6);
	cin >> N;
	for (int i = 1; i <= N; i++) {
		cin >> num;
		cout << smallestMultiple(num) << endl;
	}
	return 0;
}