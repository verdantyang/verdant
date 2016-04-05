package behavior.mediator.colleague;

import behavior.mediator.mediator.Mediator;

/**
 * Author: verdant
 * Create: 2016/4/5
 * Desc:   抽象实体
 */
public abstract class Colleague {
    protected int number;

    public int getNumber() {
        return number;
    }

    public void setNumber(int number){
        this.number = number;
    }

    //Tips: 此处参数有一个关联对象
    public abstract void setNumber(int number, Mediator med);
}
