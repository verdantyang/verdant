package behavior.template.uml.tmpl;


import utils.DebugLog;
import utils.DebugLogFactory;
import utils.DesignPatternEnum;

/**
 * 实例化模板2
 *
 * @author verdant
 * @since 2016/03/30
 */
public class ConcreteTemplate2 extends Template {
    private static final DebugLog logger = DebugLogFactory.getLogger(ConcreteTemplate2.class, DesignPatternEnum.Template);

    public ConcreteTemplate2() {
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
