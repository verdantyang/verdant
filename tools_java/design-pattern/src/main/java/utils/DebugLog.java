package utils;

/**
 * Created by Administrator on 2016/1/27.
 */
public class DebugLog {
    public static <T> void print(String content, DesigiPatternEnum dp, Class<T> clazz) {
        String format = "{ %s }(%s): %s";
        String info = String.format(format, dp.getName(), clazz.getName(), content);
        System.out.println(info);
    }
}
