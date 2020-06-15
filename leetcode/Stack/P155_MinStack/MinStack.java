package Stack.P155_MinStack;

import java.util.Stack;

/**
 * @Data Structures:
 * @Algorithms used:  BinarySearch
 * @Time Complexity:   O(logn)
 * @Space Complexity:  O(1)
 */
public class MinStack {
    private int min;
    private Stack<Integer> stack;

    /**
     * initialize your data structure here.
     */
    public MinStack() {
        this.stack = new Stack<>();
        this.min = Integer.MAX_VALUE;
    }

    public void push(int x) {
        if (x <= min) {
            stack.push(min);
            min = x;
        }
        stack.push(x);
    }

    public void pop() {
        if (stack.pop() == min)
            min = stack.pop();
    }

    public int top() {
        return stack.peek();
    }

    public int getMin() {
        return min;
    }
}

