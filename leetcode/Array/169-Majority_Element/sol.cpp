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
    int majorityElement(vector<int>& nums) {
        int len = nums.size();
        int pivot, counts = 0;
        for (int i = 0; i < len; i++) {
            if (0 == counts) {
                pivot = nums[i];
                counts = 1;
            } else if (nums[i] == pivot) {
                counts++;
            } else if (nums[i] != pivot) {
                counts--;
            }
        }
        return pivot;
    }
};

/* Test Case */
int main() {
    Solution sol;
    return 0;
}