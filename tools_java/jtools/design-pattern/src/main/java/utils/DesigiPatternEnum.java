package utils;

/**
 * Author: verdant
 * Create: 2016/1/27
 * Func:   设计模式类型枚举
 */
public enum DesigiPatternEnum {
    Singleton(1, "Singleton"),
    Factory(2, "Factory"),
    AbstractFactory(3, "AbstractFactory"),
    Prototype(4, "Prototype"),
    Builder(5, "Builder");

    private DesigiPatternEnum(Integer code, String name) {
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
