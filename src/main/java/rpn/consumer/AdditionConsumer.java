package rpn.consumer;

import rpn.message.AdditionMessage;
import rpn.message.ResultMessage;
import rpn.bus.Bus;
import rpn.message.Message;

import java.util.Stack;

public class AdditionConsumer implements Consumer {
    private final Bus bus;

    public AdditionConsumer(Bus bus) {
        this.bus = bus;
    }

    @Override
    public void receive(Message message) {
        Stack<Double> stack = ((AdditionMessage) message).getStack();

        double b = stack.pop();
        double a = stack.pop();
        stack.push(a + b);

        bus.publish(new ResultMessage(message.expressionId(), stack));
    }
}
