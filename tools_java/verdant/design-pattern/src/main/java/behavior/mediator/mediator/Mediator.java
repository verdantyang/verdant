package behavior.mediator.mediator;

import behavior.mediator.colleague.Colleague;
import utils.DebugLog;
import utils.DebugLogFactory;
import utils.DesignPatternEnum;

/**
 * Author: verdant
 * Create: 2016/4/5
 * Desc:   抽象中介者
 */
public abstract class Mediator {
    private static final DebugLog logger = DebugLogFactory.getLogger(Mediator.class, DesignPatternEnum.Mediator);

    protected Colleague A;
    protected Colleague B;

    public Mediator(Colleague a, Colleague b) {
        A = a;
        B = b;
    }

    public void inform(Colleague colA, Colleague colB) {
        logger.log(DebugLog.getClassName(colA.getClass()) + " affect " + DebugLog.getClassName(colB.getClass()));
    }

    public abstract void AaffectB();

    public abstract void BaffectA();
}
