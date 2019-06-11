package rpn.message;

import rpn.message.Message;

public class ExceptionMessage implements Message {
    public static final String MESSAGE_TYPE = "error";

    private final String expressionId;
    private final Exception exception;

    public ExceptionMessage(String expressionId, Exception exception) {
        this.expressionId = expressionId;
        this.exception = exception;
    }

    @Override
    public String messageType() {
        return MESSAGE_TYPE;
    }

    @Override
    public String expressionId() {
        return expressionId;
    }

    public Exception getException() {
        return exception;
    }
}
