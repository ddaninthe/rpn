package rpn.consumer;

import rpn.message.ExceptionMessage;
import rpn.message.MultiplicationMessage;
import rpn.message.ResultMessage;
import rpn.bus.Bus;
import rpn.message.Message;

import java.util.EmptyStackException;
import java.util.Stack;

public class MultiplicationConsumer implements Consumer {
    private final Bus bus;

    public MultiplicationConsumer(Bus bus) {
        this.bus = bus;
    }

    @Override
    public void receive(Message message) {
        Stack<Double> stack = ((MultiplicationMessage) message).getStack();

        if (stack.size() < 2) {
            bus.publish(new ExceptionMessage(message.expressionId(), new EmptyStackException()));
        }
        else {
            double b = stack.pop();
            double a = stack.pop();
            stack.push(a * b);

            bus.publish(new ResultMessage(message.expressionId(), stack));
        }
    }
}
