/**
 * @Time Complexity:    O(n)
 * @Space Complexity:   O(1)
 */
#include <vector>
#include <iostream>
using namespace std;

/* Solution */
class Solution {
public:
    vector<int> twoSum(vector<int>& numbers, int target) {
        int lo = 0;
        int hi = numbers.size() - 1;
        int sum = 0;
        while ((sum = numbers[lo] + numbers[hi]) != target) {
            if (sum < target)
                lo++;
            else
                hi--;
        }
        return vector<int>({lo+1,hi+1});
    }
};

/* Test Case */
int main() {
    Solution sol;
    return 0;
}