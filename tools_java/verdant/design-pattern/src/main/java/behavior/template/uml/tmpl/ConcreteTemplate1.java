package behavior.template.uml.tmpl;


import utils.DebugLog;
import utils.DebugLogFactory;
import utils.DesignPatternEnum;

/**
 * 实例化模板1
 *
 * @author verdant
 * @since 2016/03/30
 */
public class ConcreteTemplate1 extends Template {
    private static final DebugLog logger = DebugLogFactory.getLogger(ConcreteTemplate1.class, DesignPatternEnum.Template);

    public ConcreteTemplate1() {
        super();
    }

    @Override
    protected void produce1() {
        logger.log("Execute -> " + Thread.currentThread().getStackTrace()[1].getMethodName());
    }

    @Override
    protected void produce2() {
        logger.log("Execute -> " + Thread.currentThread().getStackTrace()[1].getMethodName());
    }
}
