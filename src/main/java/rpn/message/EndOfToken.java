package rpn.message;

import rpn.message.Message;

public class EndOfToken implements Message {
    public static final String MESSAGE_TYPE = "eot";

    private final String expressionId;

    public EndOfToken(String expressionId) {
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
}
