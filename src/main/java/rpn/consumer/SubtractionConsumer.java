package rpn.consumer;

import rpn.bus.Bus;
import rpn.message.AdditionMessage;
import rpn.message.Message;
import rpn.message.ResultMessage;
import rpn.message.SubtractionMessage;

import java.util.Stack;

public class SubtractionConsumer implements Consumer {
    private final Bus bus;

    public SubtractionConsumer(Bus bus) {
        this.bus = bus;
    }

    @Override
    public void receive(Message message) {
        Stack<Double> stack = ((SubtractionMessage) message).getStack();

        double b = stack.pop();
        double a = stack.pop();
        stack.push(a - b);

        bus.publish(new ResultMessage(message.expressionId(), stack));
    }
}
