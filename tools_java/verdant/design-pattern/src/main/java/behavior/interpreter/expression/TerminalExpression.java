package behavior.interpreter.expression;

import behavior.interpreter.context.Context;

/**
 * Author: verdant
 * Create: 2016/4/11
 * Desc:   终结符表达式
 */
public class TerminalExpression extends Expression{
    private final int value;

    public TerminalExpression(final int value)
    {
        this.value = value;
    }

    @Override
    public Integer interpreter(final Context context)
    {
        return this.value;
    }

}
