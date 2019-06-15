package rpn;

import org.junit.Before;
import org.junit.Test;
import rpn.consumer.*;
import rpn.message.*;
import rpn.bus.InMemoryBus;

import java.util.EmptyStackException;
import java.util.Stack;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class CLITest {
    private InMemoryBus bus;

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
    }

    @Test
    public void should_return_5_by_addition() {
        String expressionId = UUID.randomUUID().toString();
        bus.subscribe(EndOfClient.MESSAGE_TYPE, message ->
            assertThat(((EndOfClient) message).getStack().pop()).isEqualTo(5));
        bus.publish(new ExpressionMessage(expressionId, "4 1 +"));
    }

    @Test
    public void should_return_3_by_subtraction() {
        String expressionId = UUID.randomUUID().toString();
        bus.subscribe(EndOfClient.MESSAGE_TYPE, message ->
            assertThat(((EndOfClient) message).getStack().pop()).isEqualTo(2));
        bus.publish(new ExpressionMessage(expressionId, "4 1 + 3 -"));
    }

    @Test
    public void should_return_right_result_for_2_parallel_operation() {
        String id1 = UUID.randomUUID().toString();
        String id2 = UUID.randomUUID().toString();

        bus.subscribe(EndOfClient.MESSAGE_TYPE, message -> {
            if (id1.equals(message.expressionId())) {
                Stack<Double> stack = ((EndOfClient) message).getStack();
                assertThat(stack.pop()).isEqualTo(4);
                assertThat(stack.pop()).isEqualTo(18);
            } else if (id2.equals(message.expressionId())) {
                assertThat(((EndOfClient) message).getStack().pop()).isEqualTo(45);
            }
        });

        bus.publish(new ExpressionMessage(id1, "9 2 * 4"));
        bus.publish(new ExpressionMessage(id2, "100 2 / 10 - 5 +"));
    }

    @Test
    public void should_catch_arithmetic_exception() {
        String expressionId = UUID.randomUUID().toString();

        bus.subscribe(ExceptionMessage.MESSAGE_TYPE, message -> {
            Exception e = ((ExceptionMessage) message).getException();
            assertThat(e instanceof ArithmeticException).isTrue();
        });

        bus.publish(new ExpressionMessage(expressionId,"10 0 /"));
    }

    @Test
    public void should_throw_empty_stack_exception() {
        String expressionId = UUID.randomUUID().toString();

        bus.subscribe(ExceptionMessage.MESSAGE_TYPE, message -> {
            Exception e = ((ExceptionMessage) message).getException();
            assertThat(e instanceof EmptyStackException).isTrue();
        });

        bus.publish(new ExpressionMessage(expressionId, "43 *"));
    }
}