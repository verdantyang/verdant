/**
 * @Time Complexity:    O(n)
 * @Space Complexity:   O(1)
 */
#include <vector>
#include <iostream>
using namespace std;

class Solution {
public:
  int majorityElement(vector<int>& nums) {
    int len = nums.size();
    int pivot, cnt = 0;
    for (int i = 0; i < len; i++) {
      if (0 == cnt) {
        pivot = nums[i];
        cnt = 1;
      } else if (nums[i] == pivot) {
        cnt++;
      } else if (nums[i] != pivot) {
        cnt--;
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