package rpn.message;

import rpn.message.Message;

import java.util.Stack;

public class EndOfClient implements Message {
    public static final String MESSAGE_TYPE = "eoc";

    private final Stack<Double> stack;
    private final String expressionId;

    public EndOfClient(String expressionId, Stack<Double> stack) {
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
