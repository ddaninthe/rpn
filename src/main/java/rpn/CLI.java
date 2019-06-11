package rpn;

import rpn.consumer.*;
import rpn.message.*;
import rpn.bus.InMemoryBus;

import java.util.UUID;

class CLI {
    public static void main(String[] args) {
        String expression = String.join(" ", args).replace(',','.');

        InMemoryBus bus = new InMemoryBus();

        Calculator calculator = new Calculator(bus);
        bus.subscribe(ExpressionMessage.MESSAGE_TYPE, new TokenizerConsumer(bus, "\\s+"));
        bus.subscribe(TokenMessage.MESSAGE_TYPE, calculator);
        bus.subscribe(EndOfToken.MESSAGE_TYPE, calculator);
        bus.subscribe(ResultMessage.MESSAGE_TYPE, calculator);
        bus.subscribe(AdditionMessage.MESSAGE_TYPE, new AdditionConsumer(bus));
        bus.subscribe(SubtractionMessage.MESSAGE_TYPE, new SubtractionConsumer(bus));
        bus.subscribe(MultiplicationMessage.MESSAGE_TYPE, new MultiplicationConsumer(bus));
        bus.subscribe(DivisionMessage.MESSAGE_TYPE, new DivisionConsumer(bus));

        bus.subscribe(EndOfClient.MESSAGE_TYPE, message -> {
            StringBuilder result = new StringBuilder();
            for (double number : ((EndOfClient) message).getStack()) {
                result.append(number).append(" ");
            }
            System.out.println(message.expressionId() + " > " + result);
        });

        bus.subscribe(ExceptionMessage.MESSAGE_TYPE,
                message -> System.out.println("Error: " + ((ExceptionMessage) message).getException().getMessage()));

        String expressionId = UUID.randomUUID().toString();
        String expressionId2 = UUID.randomUUID().toString();
        System.out.println("\"" + expression + "\" with id " + expressionId);
        System.out.println("\"" + expression + "\" with id " + expressionId2);


        Thread t = new Thread(() ->
                bus.publish(new ExpressionMessage(expressionId, expression)));
        Thread t2 = new Thread(() ->
                bus.publish(new ExpressionMessage(expressionId2, "3 5 * 2 - 2 1 + 3 - 5 + -")));

        t.start();
        t2.start();
    }
}
