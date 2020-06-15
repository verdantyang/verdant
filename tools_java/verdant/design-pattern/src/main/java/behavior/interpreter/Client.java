package behavior.interpreter;

import behavior.interpreter.context.Context;
import behavior.interpreter.expression.NonterminalExpressionAdd;
import behavior.interpreter.expression.NonterminalExpressionSubtract;
import behavior.interpreter.expression.TerminalExpression;

/**
 * Author: verdant
 * Create: 2016/4/11
 * Func:   解释器模式
 */
public class Client {
    public static void main(String[] args) {

        Context ctx = new Context();
        ctx.addValue("a", 7);
        ctx.addValue("b", 8);
        ctx.addValue("c", 2);

        //a-b+c
        NonterminalExpressionSubtract subtractExpression = new NonterminalExpressionSubtract(
                new TerminalExpression(ctx.getValue("a")),
                new TerminalExpression(ctx.getValue("b")));

        NonterminalExpressionAdd addExpression = new NonterminalExpressionAdd(
                subtractExpression,
                new TerminalExpression(ctx.getValue("c")));

        addExpression.interpreter(ctx);
    }
}
