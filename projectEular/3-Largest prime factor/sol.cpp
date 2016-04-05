/**
 * @Time Complexity:    O(log(n))
 * @Space Complexity:   O(1)
 */


#include <cmath>
#include <cstdio>
#include <vector>
#include <map>
#include <iostream>
#include <algorithm>
using namespace std;

/**
Fibonacci(n%2): 1 0 1 1 0 1 1 0 ...
F(n) = F(n-1) + F(n-2) = 4F(n-3) + F(n-6)
E(n) = 4E(n-1) + E(n-2)  E(0)=2, E(1)=8
*/

unsigned long long largestPrimeFactor(unsigned long long num)  {
	while (0 == num % 2) {
		num = num / 2;
		if (1 == num)
			return 2;
	}
	unsigned long long j = 3;
	for (; j <= sqrt(num); j += 2) {
		while (0 == num % j) {
			num /= j;
			if (1 == num)
				return num * j;
		}
	}
	return num;
}

int main() {
	/* Enter your code here. Read input from STDIN. Print output to STDOUT */
	int N;
	unsigned long long num = 0;

	cin >> N;
	for (int i = 1; i <= N; i++) {
		cin >> num;
		cout << largestPrimeFactor(num) << endl;
	}
	return 0;
}