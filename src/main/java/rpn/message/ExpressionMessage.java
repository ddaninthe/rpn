package rpn.message;

public class ExpressionMessage implements Message {
    public static final String MESSAGE_TYPE = MESSAGE_TYPE_PREFIX + "expression";

    private final String expression;
    private final String expressionId;

    public ExpressionMessage(String expressionId, String expression) {
        this.expression = expression;
        this.expressionId = expressionId;
    }

    @Override
    public String messageType() {
        return MESSAGE_TYPE;
    }

    public String getExpression() {
        return expression;
    }

    public String expressionId() {
        return expressionId;
    }
}
