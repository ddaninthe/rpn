package rpn.consumer;

import rpn.bus.Bus;
import rpn.message.*;
import rpn.operator.Operator;
import rpn.operator.OperatorManager;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class Calculator implements Consumer {
    private static final int TIMEOUT = 500;

    private final Bus bus;
    private final Map<String, Stack<Double>> stacks;
    private final Map<String, Boolean> waitingMap;
    private final OperatorManager operatorManager;

    public Calculator(Bus bus) {
        this.bus = bus;
        stacks = new HashMap<>();
        waitingMap = new HashMap<>();
        operatorManager = new OperatorManager();
    }

    @Override
    public synchronized void receive(Message message) {
        String expressionId = message.expressionId();
        Stack<Double> stack;

        System.out.println(message.expressionId());
        if (stacks.containsKey(expressionId)) {
            stack = stacks.get(expressionId);
        } else {
            stack = new Stack<>();
            stacks.put(expressionId, stack);
            waitingMap.put(expressionId, false);
        }

        String messageType = message.messageType();
        if (TokenMessage.MESSAGE_TYPE.equals(messageType)) {
            TokenMessage tokenMessage = (TokenMessage) message;
            String token = tokenMessage.getToken();

            if (token.matches("^[+-]?\\d+(\\.\\d+)?$")) {
                stack.push(Double.parseDouble(token));
            }
            else {
                Operator operator = operatorManager.find(token);
                if (operator != null) {
                    operator.setExpressionId(expressionId);
                    operator.setStack(stack);

                    waitingMap.put(expressionId, true);
                    bus.publish((Message) operator);
                }
            }
        }
        else if (ResultMessage.MESSAGE_TYPE.equals(messageType)){
            ResultMessage resultMessage = (ResultMessage) message;
            stacks.put(expressionId, resultMessage.getStack());
            waitingMap.put(expressionId, false);
        }
        else if (EndOfToken.MESSAGE_TYPE.equals(messageType)) {
           try {
               while (waitingMap.get(expressionId)) {
                   wait(TIMEOUT);
               }
               EndOfClient eoc = new EndOfClient(expressionId, stacks.get(expressionId));
               bus.publish(eoc);
           } catch (InterruptedException e) {
               waitingMap.remove(expressionId);
               stacks.remove(expressionId);
               bus.publish(new ExceptionMessage(expressionId, e));
           }
        }
    }
}
