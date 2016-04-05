package behavior.mediator;

import behavior.mediator.colleague.Colleague;
import behavior.mediator.colleague.ConcreteColleagueA;
import behavior.mediator.colleague.ConcreteColleagueB;
import behavior.mediator.mediator.ConcreteMediator;
import behavior.mediator.mediator.Mediator;
import utils.DebugLog;
import utils.DebugLogFactory;
import utils.DesignPatternEnum;

/**
 * Created by Administrator on 2016/4/5.
 */
public class Client {
    private static final DebugLog logger = DebugLogFactory.getLogger(Client.class, DesignPatternEnum.Mediator);

    public static void main(String[] args) {
        Colleague colA = new ConcreteColleagueA();
        Colleague colB = new ConcreteColleagueB();

        Mediator med = new ConcreteMediator(colA, colB);

        colA.setNumber(1000, med);
        colB.setNumber(1000, med);
    }
}
