package structural.composite;

import structural.composite.composite.Composite;
import structural.composite.leaf.Leaf;

/**
 * Author: verdant
 * Create: 2016/4/18
 * Func:   组合模式
 */
public class Client {
    public static void main(String[] args) {
        Composite compositeRoot = new Composite("Node-root");
        Composite compositeLeft = new Composite("Node-left");
        Composite compositeRight = new Composite("Node-right");
        compositeRoot.add(compositeRight);
        compositeRoot.add(compositeLeft);

        Leaf leaf1 = new Leaf("Leaf-rightChild 1");
        Leaf leaf2 = new Leaf("Leaf-rightChild 2");
        compositeRight.add(leaf1);
        compositeRight.add(leaf2);

        // 遍历节点
        compositeRoot.eachChild();
    }
}
