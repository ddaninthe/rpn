package rpn.message;

import rpn.message.Message;

import java.util.Stack;

public class OperatorMessage implements Message {
    private static final String MESSAGE_TYPE = "operator.addition";

    private Stack<Double> stack;
    private String expressionId;

    public OperatorMessage(String expressionId, Stack<Double> stack) {
        this.stack = stack;
        this.expressionId = expressionId;
    }

    @Override
    public String messageType() {
        return MESSAGE_TYPE;
    }

    @Override
    public String expressionId() {
        return expressionId;
    }

    public Stack<Double> getStack() {
        return stack;
    }

    public void setStack(Stack<Double> stack) {
        this.stack = stack;
    }

    public void setExpressionId(String expressionId) {
        this.expressionId = expressionId;
    }
}
