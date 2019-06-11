package rpn.operator;

import rpn.message.AdditionMessage;
import rpn.message.DivisionMessage;
import rpn.message.MultiplicationMessage;
import rpn.operator.Operator;
import rpn.message.SubtractionMessage;

import java.util.HashMap;
import java.util.Map;

public class OperatorManager {
    private final Map<String, Operator> operatorMessages = new HashMap<>();

    public OperatorManager() {
        operatorMessages.put("+", new AdditionMessage());
        operatorMessages.put("-", new SubtractionMessage());
        operatorMessages.put("*", new MultiplicationMessage());
        operatorMessages.put("/", new DivisionMessage());
    }

    public Operator find(String key) {
        return operatorMessages.get(key);
    }
}
