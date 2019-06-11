package rpn.message;

import rpn.message.Message;

public class TokenMessage implements Message {
    public static final String MESSAGE_TYPE = "token";

    private final String token;
    private final String expressionId;

    public TokenMessage(String expressionId, String token) {
        this.token = token;
        this.expressionId = expressionId;
    }

    @Override
    public String messageType() {
        return MESSAGE_TYPE;
    }

    public String getToken() {
        return token;
    }

    public String expressionId() {
        return expressionId;
    }
}
