/**
 * @Time Complexity:    O(n)
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
int main() {
	/* Enter your code here. Read input from STDIN. Print output to STDOUT */
	int N;
	unsigned long long num = 0;
	vector<unsigned long long> oriInput, sortInput;
	vector<unsigned long long>::iterator iter;
	map<unsigned long long, unsigned long long> rets;

	cin >> N;
	for (int i = 1; i <= N; i++) {
		cin >> num;
		oriInput.push_back(num);
	}

	copy(oriInput.begin(), oriInput.end(), back_inserter(sortInput));
	sort(sortInput.begin(), sortInput.end());

	unsigned long long fe_0 = 2;
	unsigned long long fe_1 = 8;
	unsigned long long sum = fe_0;
	unsigned long long fe_2 = 0;
	for (iter = sortInput.begin(); iter != sortInput.end(); iter++)
	{
		while (fe_1 < *iter) {
			sum += fe_1;
			fe_2 = 4 * fe_1 + fe_0;
			fe_0 = fe_1;
			fe_1 = fe_2;
		}
		rets[*iter] = sum;
	}

	for (iter = oriInput.begin(); iter != oriInput.end(); iter++)
	{
		cout << rets[*iter] << endl;
	}
	return 0;
}