package Classical;

import java.util.ArrayList;
import java.util.List;

/**
 * 约瑟夫环
 *
 * @Data Structures:   List
 * @Algorithms:
 * @Time Complexity:   O(n)
 * @Space Complexity:  O(1)
 */
public class Joseph {

    private int LastRemaining(int len, int k, int start) {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < len; i++)
            list.add(i);

        int index = start;
        while (list.size() > 1) {
            index = (index + k - 1) % list.size();
            list.remove(index);
        }
        return list.get(0);
    }

    public static void main(String[] args) {
        //序号从0开始
        Joseph sol = new Joseph();
        System.out.println(sol.LastRemaining(10, 3, 1));
    }
}
