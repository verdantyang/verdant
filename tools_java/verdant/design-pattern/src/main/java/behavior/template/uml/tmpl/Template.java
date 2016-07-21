package behavior.template.uml.tmpl;

/**
 * 抽象模板
 *
 * @author verdant
 * @since 2016/03/30
 */
public abstract class Template {

    public void process() {
        produce1();
        produce2();
    }

    protected abstract void produce1();

    protected abstract void produce2();
}
