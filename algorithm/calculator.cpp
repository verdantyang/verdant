#include <iostream>
#include <map>
#include <stdio.h>

using namespace std;

class Calculator {
public:
    static int BinaryAdd(int a, int b) {
        int carry, add;
        do {
            add = a ^ b;              //本位的加法结果
            carry = (a & b) << 1;     //进位值
            a = add;
            b = carry;
        } while (carry != 0); //循环直到某次运算没有进位，运算结束
        return add;
    }

    static int BinarySub(int a, int b) {
        return BinaryAdd(a, BinaryAdd(~b, 1));
    }

    static int BinaryMultiply(int a, int b) {
        bool neg = (b < 0);
        if (b < 0) b = -b;
        int product = 0;
        map<int, int> bit_map;
        for (int i = 0; i < 32; i++) {
            bit_map.insert(pair<int, int>(1 << i, i));
        }
        while (b > 0) {
            /* b & ~(b - 1)可以得到乘数b的二进制表示中最右侧1的位置 last_bit记录被乘数a需要位移的位数 */
            int last_bit = bit_map[b & ~(b - 1)]; //将得到的乘法结果全部相加即为最后结果
            product += (a << last_bit);
            b &= b - 1; //每次将b的二进制表示的最右侧1去掉用于下一次乘法
        }
        if (neg)
            product = -product;
        return product;
    }

    static int BinaryDivide(int a, int b) {
        bool neg = (a > 0) ^ (b > 0);
        if (a < 0) a = -a;
        if (b < 0) b = -b;
        if (a < b)
            return 0;
        int msb = 0; //msd记录除数需要左移的位数
        for (msb = 0; msb < 32; msb++) {
            if ((b << msb) >= a)
                break;
        }
        int quotient  = 0; //记录每次除法的商
        for (int i = msb; i >= 0; i--) {
            if ((b << i) > a)
                continue;
            quotient  |= (1 << i);
            a -= (b << i);
        }
        if (neg)
            quotient = -quotient;
        return quotient ;
    }
};


int main() {
    Calculator cal;
    int a = -33, b = 16;
    // cin >> a >> b;
    cout << "Plus: " << cal.BinaryAdd(a, b) << endl;
    cout << "Minus: " << cal.BinarySub(a, b) << endl;
    cout << "Multiply: " << cal.BinaryMultiply(a, b) << endl;
    cout << "Divide: " << cal.BinaryDivide(a, b) << endl;
}