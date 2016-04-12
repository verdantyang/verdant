package behavior.interpreter.context;

import java.util.HashMap;
import java.util.Map;

/**
 * Author: verdant
 * Create: 2016/4/11
 * Desc:   解析内容
 */
public class Context {
    private final Map<String, Integer> valueMap = new HashMap<>();

    public void addValue(final String key, final int value) {
        valueMap.put(key, Integer.valueOf(value));
    }

    public int getValue(final String key) {
        return valueMap.get(key).intValue();
    }
}
