package utils;

/**
 * Author: verdant
 * Create: 2016/1/27
 * Func:   设计模式类型枚举
 */
public enum DesignPatternEnum {
    Singleton(11, "Singleton"),
    Factory(12, "Factory"),
    AbstractFactory(13, "AbstractFactory"),
    Prototype(14, "Prototype"),
    Builder(15, "Builder"),
    Strategy(21, "Strategy"),
    State(22, "State"),
    Observer(23, "Observer"),
    Command(24, "Command"),
    Template(25, "Template");

    private DesignPatternEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    private Integer code;
    private String name;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
