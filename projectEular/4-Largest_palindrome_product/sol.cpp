/**
 * @Time Complexity:    O(10^5)
 * @Space Complexity:   O(1)
 */
#include <cmath>
#include <cstdio>
#include <vector>
#include <map>
#include <iostream>
#include <algorithm>
using namespace std;

bool chkPal(int num) {
	char b[7];
	sprintf(b, "%d", num);
	if ((b[0] == b[5]) && (b[1] == b[4]) && (b[2] == b[3]))
		return true;
	return false;
}

int main() {
	/* Enter your code here. Read input from STDIN. Print output to STDOUT */
	vector<int> palList;
	for (int i = 100; i < 999; i++) {
		for (int j = 100; j < 999; j++)
		{
			if (chkPal(i * j))
				palList.push_back(i * j);
		}
	}

	int N, num, max = 0;

	cin >> N;
	for (int i = 1; i <= N; i++) {
		cin >> num;
		max = 0;
		for (auto palElem : palList) {
			if (palElem < num && max < palElem)
				max = palElem;
		}
		cout << max << endl;
	}
	return 0;
}