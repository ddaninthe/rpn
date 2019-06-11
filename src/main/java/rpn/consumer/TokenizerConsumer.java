package rpn.consumer;

import rpn.bus.Bus;
import rpn.message.EndOfToken;
import rpn.message.ExpressionMessage;
import rpn.message.Message;
import rpn.message.TokenMessage;

import java.util.stream.Stream;

public class TokenizerConsumer implements Consumer {
    private final String splitRegex;
    private final Bus bus;

    public TokenizerConsumer(Bus bus, String splitRegex) {
        this.bus = bus;
        this.splitRegex = splitRegex;
    }

    @Override
    public void receive(Message message) {
        ExpressionMessage eMsg = (ExpressionMessage) message;

        String expression = eMsg.getExpression();
        Stream.of(expression.split(splitRegex))
                .forEach(token -> bus.publish(new TokenMessage(eMsg.expressionId(), token)));

        bus.publish(new EndOfToken(eMsg.expressionId()));
    }
}