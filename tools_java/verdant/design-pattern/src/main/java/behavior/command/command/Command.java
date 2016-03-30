package behavior.command.command;

import behavior.command.receiver.Receiver;

/**
 * Author: verdant
 * Create: 2016/3/30
 * Desc:   抽象命令
 */
public interface Command {
    void execute();
    void undo();
}
