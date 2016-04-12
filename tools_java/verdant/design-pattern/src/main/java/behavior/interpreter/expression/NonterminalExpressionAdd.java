package behavior.interpreter.expression;

import behavior.interpreter.context.Context;
import utils.DebugLog;
import utils.DebugLogFactory;
import utils.DesignPatternEnum;

/**
 * Author: verdant
 * Create: 2016/4/11
 * Desc:   非终结符表达式（加）
 */
public class NonterminalExpressionAdd extends Expression{
    private static final DebugLog logger = DebugLogFactory.getLogger(NonterminalExpressionAdd.class, DesignPatternEnum.Interpreter);

    private Expression left;
    private Expression right;

    public NonterminalExpressionAdd(final Expression left, final Expression right)
    {
        this.left = left;
        this.right = right;
    }

    @Override
    public Integer interpreter(final Context context)
    {
        logger.log(DebugLog.getClassName(this.getClass()));
        return this.left.interpreter(context) + this.right.interpreter(context);
    }
}
