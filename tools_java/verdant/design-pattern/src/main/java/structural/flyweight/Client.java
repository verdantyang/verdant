package structural.flyweight;

import structural.flyweight.factory.FlyweightFactory;
import structural.flyweight.flyweight.Flyweight;

/**
 * Author: verdant
 * Create: 2016/4/18
 * Func:   享元模式
 */
public class Client {
    FlyweightFactory factory = new FlyweightFactory();
    Flyweight fly1;
    Flyweight fly2;
    Flyweight fly3;
    Flyweight fly4;
    Flyweight fly5;

    /**
     * Creates new instance of Flyweight
     */
    public Client() {
        fly1 = factory.getFlyWeight("Google");
        fly2 = factory.getFlyWeight("Baidu");
        fly3 = factory.getFlyWeight("Bing");
        fly4 = factory.getFlyWeight("Google");
        fly5 = factory.getFlyWeight("Google");
    }

    public void showFlyweight() {
        fly1.operation();
        fly2.operation();
        fly3.operation();
        fly4.operation();
        fly5.operation();
        int objSize = factory.getFlyweightSize();
    }

    public static void main(String[] args) {
        Client client = new Client();
        client.showFlyweight();
    }
}
