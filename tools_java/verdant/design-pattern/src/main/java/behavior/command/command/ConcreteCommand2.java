package behavior.command.command;

import behavior.command.receiver.Receiver;

/**
 * Author: verdant
 * Create: 2016/3/30
 * Desc:   实例化命令2
 */
public class ConcreteCommand2 implements Command {
    Receiver receiver;

    public ConcreteCommand2(Receiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public void execute() {
        receiver.operate2();
    }

    @Override
    public void undo() {

    }
}
