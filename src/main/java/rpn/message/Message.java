package rpn.message;

public interface Message {
    String MESSAGE_TYPE_PREFIX = "rpn.#.";

    String messageType();
    String expressionId();
}
