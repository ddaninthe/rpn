package rpn.message;

import rpn.message.Message;

import java.util.Stack;

public class ResultMessage implements Message {
    public static final String MESSAGE_TYPE = "operator.result";

    private final Stack<Double> stack;
    private final String expressionId;

    public ResultMessage(String expressionId, Stack<Double> stack) {
        this.expressionId = expressionId;
        this.stack = stack;
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
}
