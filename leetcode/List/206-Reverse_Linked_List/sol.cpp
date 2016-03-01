/**
 * @Time Complexity:    O(n)
 * @Space Complexity:   O(1)
 */
#include <stdio.h>
 
/**
 * Definition for singly-linked list.
 */
struct ListNode {
    int val;
    ListNode *next;
    ListNode(int x) : val(x), next(NULL) {}
};

class Solution {
public:
    ListNode* reverseList(ListNode* head) {
        ListNode* pcurrent;
        ListNode* pnext;
        ListNode* ptemp;

        pcurrent = head;
        pnext = pcurrent == NULL ? NULL : pcurrent -> next;

        if (pcurrent == NULL || pnext == NULL)
            return head;

        pcurrent -> next = NULL;
        while (pnext != NULL) {
            ptemp = pnext -> next;
            pnext -> next = pcurrent;
            pcurrent = pnext;
            pnext = ptemp;
        }
        return pcurrent;
    }
};