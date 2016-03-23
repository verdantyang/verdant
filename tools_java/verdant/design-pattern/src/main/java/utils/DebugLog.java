package utils;

/**
 * Author: verdant
 * Create: 2016/3/23
 * Func:   格式化日志
 */
public class DebugLog {

    private static final String format = "{%s}(%s): %s";

    private String name;
    private DesignPatternEnum designPattern;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DesignPatternEnum getDesignPattern() {
        return designPattern;
    }

    public void setDesignPattern(DesignPatternEnum designPattern) {
        this.designPattern = designPattern;
    }

    public void log(String content) {
        String info = String.format(format, designPattern.getName(), this.name, content);
        System.out.println(info);
    }
}
