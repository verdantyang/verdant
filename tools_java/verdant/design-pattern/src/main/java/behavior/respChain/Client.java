package behavior.respChain;

import behavior.respChain.Handler.ConcreteHandler1;
import behavior.respChain.Handler.ConcreteHandler2;
import behavior.respChain.Handler.Handler;

/**
 * Author: verdant
 * Create: 2016/4/11
 * Func:   责任链模式
 */
public class Client {
    public static void main(String[] args) {
        //组装责任链
        Handler handler1 = new ConcreteHandler1();
        Handler handler2 = new ConcreteHandler2();
        handler1.setSuccessor(handler2);
        //处理请求
        handler1.handleRequest();
    }
}
