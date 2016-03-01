package utils;

/**
 * Author: verdant
 * Create: 2016/1/27
 * Func:   格式化输出
 */
public class DebugLog {
    public static <T> void print(String content, DesigiPatternEnum dp, Class<T> clazz) {
        String format = "{ %s }(%s): %s";
        String info = String.format(format, dp.getName(), clazz.getName(), content);
        System.out.println(info);
    }
}
