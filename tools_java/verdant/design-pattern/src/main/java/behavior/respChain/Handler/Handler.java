package behavior.respChain.Handler;

/**
 * Author: verdant
 * Create: 2016/4/11
 * Desc:   抽象处理器
 */
public abstract class Handler {


    /**
     * 持有后继的责任对象
     */
    protected Handler successor;
    /**
     * 处理请求的方法（可带参数）
     */
    public abstract void handleRequest();

    public Handler getSuccessor() {
        return successor;
    }

    public void setSuccessor(Handler successor) {
        this.successor = successor;
    }

}
