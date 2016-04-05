package behavior.command.invoker;

import behavior.command.command.Command;

/**
 * Author: verdant
 * Create: 2016/3/30
 * Desc:   命令发起者
 */
public class Invoker {
    private Command command1, command2;

    public Invoker(Command command1, Command command2) {
        this.command1 = command1;
        this.command2 = command2;
    }

    public void executeCommand1() {
        command1.execute();
    }

    public void executeCommand2() {
        command2.execute();
    }
}
