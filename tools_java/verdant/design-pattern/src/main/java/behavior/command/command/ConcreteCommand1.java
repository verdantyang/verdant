package behavior.command.command;

import behavior.command.receiver.Receiver;

/**
 * Author: verdant
 * Create: 2016/3/30
 * Desc:   实例化命令1
 */
public class ConcreteCommand1 implements Command{
    Receiver receiver;

    public ConcreteCommand1(Receiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public void execute() {
        receiver.operate1();
    }

    @Override
    public void undo() {

    }
}
