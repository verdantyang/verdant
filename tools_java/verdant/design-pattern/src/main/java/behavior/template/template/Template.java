package behavior.template.template;

/**
 * Author: verdant
 * Create: 2016/3/30
 * Desc:   抽象模板
 */
public abstract class Template {

    public void process() {
        produce1();
        produce2();
    }

    protected abstract void produce1();

    protected abstract void produce2();

}
