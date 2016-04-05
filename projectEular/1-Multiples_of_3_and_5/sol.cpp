/**
 * @Time Complexity:    O(n)
 * @Space Complexity:   O(1)
 */
#include <cmath>
#include <cstdio>
#include <vector>
#include <iostream>
#include <algorithm>
using namespace std;


int main() {
	/* Enter your code here. Read input from STDIN. Print output to STDOUT */
	int N;
	long num, sum, three, five, fifteen = 0;
	cin >> N;
	for (int i = 1; i <= N; i++) {
		cin >> num;
		three = (num - 1) / 3;
		five = (num - 1) / 5;
		fifteen = (num - 1) / 15;
		sum = 3 * (three * (three + 1) / 2)
		      + 5 * (five * (five + 1) / 2) +
		      -15 * (fifteen * (fifteen + 1) / 2);
		cout << sum << endl;
	}
	return 0;
}