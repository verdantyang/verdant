package behavior.command;

import behavior.command.command.Command;
import behavior.command.command.ConcreteCommand1;
import behavior.command.command.ConcreteCommand2;
import behavior.command.invoker.Invoker;
import behavior.command.receiver.Receiver;

/**
 * Author: verdant
 * Create: 2016/3/30
 * Func:   命令模式
 */
public class Client {
    public static void main(String[] args) {
        Receiver receiver = new Receiver();
        Command command1 = new ConcreteCommand1(receiver);
        Command command2 = new ConcreteCommand2(receiver);
        Invoker invoker = new Invoker(command1, command2);
        invoker.executeCommand1();
        invoker.executeCommand2();
    }
}
