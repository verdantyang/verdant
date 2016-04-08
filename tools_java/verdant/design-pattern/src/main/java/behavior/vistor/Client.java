package behavior.vistor;

import behavior.vistor.element.ConcreteElementA;
import behavior.vistor.element.ConcreteElementB;
import behavior.vistor.element.Element;
import behavior.vistor.vistor.ConcreteVisitor;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Author: verdant
 * Create: 2016/4/7
 * Func:   访问者模式
 */
public class Client {
    public static void main(String[] args) {
        List<Element> list = getList();
        for (Element elem : list) {
            elem.accept(new ConcreteVisitor());
        }
    }

    public static List<Element> getList() {
        List<Element> list = new ArrayList<>();
        Random rand = new Random();
        for (int i = 0; i < 5; i++) {
            int a = rand.nextInt(100);
            if (a > 50) {
                list.add(new ConcreteElementA());
            } else {
                list.add(new ConcreteElementB());
            }
        }
        return list;
    }
}
