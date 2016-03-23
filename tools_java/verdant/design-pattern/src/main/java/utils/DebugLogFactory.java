package utils;

/**
 * Author: verdant
 * Create: 2016/1/27
 * Func:   格式化输出
 */
public class DebugLogFactory {

    public static <T> DebugLog getLogger(Class<T> clazz, DesignPatternEnum designPatternEnum) {
        DebugLog debugLog = new DebugLog();
        debugLog.setName(clazz.getName());
        debugLog.setDesignPattern(designPatternEnum);
        return debugLog;
    }
}
