package Math.P258_AdDigits;

public class Solution258 {
    public static int addDigits(int num) {
        int dig = 0;
        if (num / 10 == 0)
            return num;
        while (num / 10 > 0) {
            dig += num % 10;
            num = num / 10;
        }
        dig += num;
        return addDigits(dig);
    }

    public static void main(String[] args) {
        System.out.println(Solution258.addDigits(12));
    }
}
