#include <cmath>
#include <cstdio>
#include <vector>
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
	vector<long> ar,sr;
	long num, cursor, sum = 0;
	cin >> N;
	for (int i = 1; i <= N; i++) {
		cin >> num;
		ar.push_back(num);
	}
	sr = sort(ar.begin(), ar.end());


	long fe_0 = 2;
	long fe_1 = 8;
	sum = fe_0;
	while (fe_1 < num) {
        sum += fe_1;
        int fe_2 = 4 * fe_1 + fe_0;
        fe_0 = fe_1;
        fe_1 = fe_2;
	}
	cout << sum << endl;
	return 0;
}