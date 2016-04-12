package behavior.interpreter.expression;

import behavior.interpreter.context.Context;

/**
 * Author: verdant
 * Create: 2016/4/11
 * Desc:   抽象表达式
 */
public abstract class Expression {
    public abstract Integer interpreter(Context ctx);
}
