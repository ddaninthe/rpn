package rpn;

import org.junit.Test;

import java.util.EmptyStackException;
import java.util.Stack;

import static org.assertj.core.api.Assertions.assertThat;
import static rpn.Calculator.calculate;

public class CLITest {
    private static boolean equals(Stack<Float> stack1, Stack<Float> stack2 ) {
        if (stack1.size() != stack2.size()) return false;

        for (int i = 0; i < stack1.size(); i++) {
            if (stack1.get(i).floatValue() != stack2.get(i).floatValue()) {
                return false;
            }
        }
        return true;
    }

    @Test
    public void should_evaluate_single_digit_constant() {
        String[] tokens = Tokenizer.tokenize("5");
        Stack<Float> expectedStack = new Stack<>();
        expectedStack.push((float)5.0);
        Stack<Float> result = calculate(tokens);
        assertThat(equals(result ,expectedStack)).isTrue();
    }

    @Test
    public void should_evaluate_multiple_digits_constant() {
        String[] tokens = Tokenizer.tokenize("17,5");
        Stack<Float> expectedStack = new Stack<>();
        expectedStack.push((float) 17.5);
        Stack<Float> result = calculate(tokens);
        assertThat(equals(result ,expectedStack)).isTrue();
    }

    @Test
    public void should_evaluate_simple_addition() {
        String[] tokens = Tokenizer.tokenize("17 5 +");
        Stack<Float> expectedStack = new Stack<>();
        expectedStack.push((float)22.0);
        Stack<Float> result = calculate(tokens);
        assertThat(equals(result, expectedStack)).isTrue();
    }

    @Test
    public void should_evaluate_more_complex_addition() {
        String[] tokens = Tokenizer.tokenize("2 3 5 + +");
        Stack<Float> expectedStack = new Stack<>();
        expectedStack.push((float)10.0);
        Stack<Float> result = calculate(tokens);
        assertThat(equals(result ,expectedStack)).isTrue();
    }

    @Test
    public void should_evaluate_negative_number() {
        String[] tokens = Tokenizer.tokenize("-5 1 +");
        Stack<Float> expectedStack = new Stack<>();
        expectedStack.push((float)-4.0);
        Stack<Float> result = calculate(tokens);
        assertThat(equals(result ,expectedStack)).isTrue();
    }

    @Test
    public void should_evaluate_more_complex_addition_and_multiplication() {
        String[] tokens = Tokenizer.tokenize("3 5 8 * 7 + * ");
        Stack<Float> expectedStack = new Stack<>();
        expectedStack.push((float)141.0);
        Stack<Float> result = calculate(tokens);
        assertThat(equals(result ,expectedStack)).isTrue();
    }

    @Test
    public void should_evaluate_calcul_and_number() {
        String[] tokens = Tokenizer.tokenize("7 2 - 3 4 ");
        Stack<Float> expectedStack = new Stack<>();
        expectedStack.push((float)5.0);
        expectedStack.push((float)3.0);
        expectedStack.push((float)4.0);
        Stack<Float> result = calculate(tokens);
        assertThat(equals(result ,expectedStack)).isTrue();
    }

    @Test
    public void should_evaluate_calcul_float() {
        String[] tokens = Tokenizer.tokenize("1.2 1.3 +");
        Stack<Float> expectedStack = new Stack<>();
        expectedStack.push((float)2.5);
        Stack<Float> result = calculate(tokens);
        assertThat(equals(result ,expectedStack)).isTrue();
    }

    @Test
    public void should_evaluate_division() {
        String[] tokens = Tokenizer.tokenize("27 9 /");
        Stack<Float> expectedStack = new Stack<>();
        expectedStack.push((float)3.0);
        Stack<Float> result = calculate(tokens);
        assertThat(equals(result ,expectedStack)).isTrue();
    }

    @Test
    public void should_evaluate_to_few_character(){
        try {
            calculate(Tokenizer.tokenize("0 +"));
        } catch(Exception exception) {
            assertThat(exception instanceof EmptyStackException).isTrue();
        }
    }

    @Test
    public void should_evaluate_illegal_character(){
        Stack<Float> result = calculate(Tokenizer.tokenize("i"));
        assertThat(result).hasSize(0);
    }
}