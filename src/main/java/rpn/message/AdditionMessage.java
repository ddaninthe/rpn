package rpn.message;

import rpn.message.Message;
import rpn.operator.Operator;

import java.util.Stack;

public class AdditionMessage implements Message, Operator {
    public static final String MESSAGE_TYPE = "operators.addition";

    private Stack<Double> stack;
    private String expressionId;

    public AdditionMessage() { }

    @Override
    public String messageType() {
        return MESSAGE_TYPE;
    }

    @Override
    public String expressionId() {
        return expressionId;
    }

    @Override
    public Stack<Double> getStack() {
        return stack;
    }

    @Override
    public void setStack(Stack<Double> stack) {
        this.stack = stack;
    }

    @Override
    public void setExpressionId(String expressionId) {
        this.expressionId = expressionId;
    }
}
