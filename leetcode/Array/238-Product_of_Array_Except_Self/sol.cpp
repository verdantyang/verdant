/**
 * @Time Complexity:    O(n)
 * @Space Complexity:   O(1)
 */
#include <stdio.h>
#include <vector>
#include <iostream>
using namespace std;

class Solution {
public:
    vector<int> productExceptSelf(vector<int>& nums) {
        int len = nums.size();
        int fromBegin = 1;
        int fromEnd = 1;
        vector<int> rets(len, 1);
        for (int i = 0; i < len; i++) {
            rets[i] *= fromBegin;
            fromBegin *= nums[i];
            rets[len - 1 - i] *= fromEnd;
            fromEnd *= nums[len - 1 - i];
        }
        return rets;
    }
};

/* Test Case */
int main() {
    Solution sol;
    int a[] = {1, 2, 3, 4, 5, 6};
    vector<int> ori(a, a + sizeof(a) / sizeof(a[0]));
    vector<int> rets = sol.productExceptSelf(ori);
    vector<int>::iterator iter;
    for (iter = rets.begin(); iter != rets.end(); iter++)
    {
        cout << *iter << ' ';
    }
    return 0;
}