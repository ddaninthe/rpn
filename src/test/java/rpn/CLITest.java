package rpn;

import org.junit.Before;
import org.junit.Test;
import rpn.consumer.*;
import rpn.message.*;
import rpn.bus.InMemoryBus;

import java.util.Stack;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class CLITest {
    private InMemoryBus bus;
    private boolean received;

    private static boolean equals(Stack<Float> stack1, Stack<Float> stack2 ) {
        if (stack1.size() != stack2.size()) return false;

        for (int i = 0; i < stack1.size(); i++) {
            if (stack1.get(i).floatValue() != stack2.get(i).floatValue()) {
                return false;
            }
        }
        return true;
    }

    @Before
    public void setup() {
        bus = new InMemoryBus();

        bus.subscribe(ExpressionMessage.MESSAGE_TYPE, new TokenizerConsumer(bus, "\\s+"));
        Calculator calculator = new Calculator(bus);
        bus.subscribe(TokenMessage.MESSAGE_TYPE, calculator);
        bus.subscribe(EndOfToken.MESSAGE_TYPE, calculator);
        bus.subscribe(ResultMessage.MESSAGE_TYPE, calculator);
        bus.subscribe(AdditionMessage.MESSAGE_TYPE, new AdditionConsumer(bus));
        bus.subscribe(SubtractionMessage.MESSAGE_TYPE, new SubtractionConsumer(bus));
        bus.subscribe(MultiplicationMessage.MESSAGE_TYPE, new MultiplicationConsumer(bus));
        bus.subscribe(DivisionMessage.MESSAGE_TYPE, new DivisionConsumer(bus));

        received = false;
    }

    @Test
    public void should_return_5_by_addition() {
        String expressionId = UUID.randomUUID().toString();
        bus.subscribe(EndOfClient.MESSAGE_TYPE, message -> {
            assertThat(((EndOfClient) message).getStack().pop()).isEqualTo(5);
            received = true;
        });
        bus.publish(new ExpressionMessage(expressionId, "4 1 +"));

        while (!received);
    }

    @Test
    public void should_return_3_by_subtraction() {
        String expressionId = UUID.randomUUID().toString();
        bus.subscribe(EndOfClient.MESSAGE_TYPE, message -> {
            assertThat(((EndOfClient) message).getStack().pop()).isEqualTo(2);
            received = true;
        });
        bus.publish(new ExpressionMessage(expressionId, "4 1 + 3 -"));

        while (!received);
    }
}