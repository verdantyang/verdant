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
    Decorator(21, "Decorator"),
    Adapter(22, "Adapter"),
    Bridge(23, "Bridge"),
    Facade(24, "Facade"),
    Proxy(25, "Proxy"),
    Composite(26, "Composite"),
    Flyweight(27, "Flyweight"),
    Strategy(31, "Strategy"),
    State(32, "State"),
    Observer(33, "Observer"),
    Command(34, "Command"),
    Template(35, "Template"),
    Iterator(36, "Iterator"),
    Mediator(37, "Iterator"),
    Visitor(38, "Visitor"),
    Memento(39, "Memento"),
    ChainOfResponsibility(40, "ChainOfResponsibility"),
    Interpreter(41, "Interpreter");

    DesignPatternEnum(Integer code, String name) {
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
