package rpn.consumer;

import rpn.message.DivisionMessage;
import rpn.message.ExceptionMessage;
import rpn.message.ResultMessage;
import rpn.bus.Bus;
import rpn.message.Message;

import java.util.EmptyStackException;
import java.util.Stack;

public class DivisionConsumer implements Consumer {
    private final Bus bus;

    public DivisionConsumer(Bus bus) {
        this.bus = bus;
    }

    @Override
    public void receive(Message message) {
        Stack<Double> stack = ((DivisionMessage) message).getStack();

        if (stack.size() < 2) {
            bus.publish(new ExceptionMessage(message.expressionId(), new EmptyStackException()));
        }
        else {
            double b = stack.pop();
            double a = stack.pop();

            if (Math.abs(b) > Math.pow(0.1, 6)) {
                stack.push(a / b);
                bus.publish(new ResultMessage(message.expressionId(), stack));
            } else {
                bus.publish(new ExceptionMessage(message.expressionId(), new ArithmeticException("Cannot divide by 0.")));
            }
        }
    }
}
