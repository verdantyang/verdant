package behavior.mediator.mediator;

import behavior.mediator.colleague.Colleague;

/**
 * Author: verdant
 * Create: 2016/4/5
 * Desc:   实例化中介者
 */
public class ConcreteMediator extends Mediator {
    public ConcreteMediator(Colleague a, Colleague b) {
        super(a, b);
    }

    public void AaffectB() {
        int number = A.getNumber();
        B.setNumber(number * 100);
        inform(A, B);
    }

    public void BaffectA() {
        int number = B.getNumber();
        A.setNumber(number / 100);
        inform(B, A);
    }
}
