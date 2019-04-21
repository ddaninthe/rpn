package rpn;

import org.junit.Test;

import java.security.InvalidParameterException;

import static org.assertj.core.api.Assertions.assertThat;
import static rpn.Calculator.evaluate;

public class CLITest {
    @Test
    public void should_evaluate_single_digit_constant() {
        assertThat(evaluate("5")).isEqualTo("5.0");
    }

    @Test
    public void should_evaluate_multiple_digits_constant() {
        assertThat(evaluate("17")).isEqualTo("17.0");
    }

    @Test
    public void should_evaluate_simple_addition() {
        assertThat(evaluate("17 5 +")).isEqualTo("22.0");
    }

    @Test
    public void should_evaluate_more_complex_addition() {
        assertThat(evaluate("2 3 5 + +")).isEqualTo("10.0");
    }

    @Test
    public void should_evaluate_negative_number() {
        assertThat(evaluate("-5 1 +")).isEqualTo("-4.0");
    }

    @Test
    public void should_evaluate_more_complex_addition_and_multiplication() {
        assertThat(evaluate("3 5 8 * 7 + * ")).isEqualTo("141.0");
    }

    @Test
    public void should_evaluate_calcul_and_number() {
        assertThat(evaluate("7 2 - 3 4 ")).isEqualTo("5.0 3.0 4.0");
    }

    @Test
    public void should_evaluate_calcul_float() {
        assertThat(evaluate("1.2 1.3 +")).isEqualTo("2.5");
    }

    @Test
    public void should_evaluate_division() {
        assertThat(evaluate("27 9 /")).isEqualTo("3.0");
    }

    @Test
    public void should_evaluate_to_few_character(){
        try {
            evaluate("0 +");
        }
        catch(InvalidParameterException exception) {
            assertThat(exception.getMessage()).isEqualTo("Parameters count is invalid.");
        }
    }

    @Test
    public void should_evaluate_illegal_character(){
        try {
            evaluate("i");
        }
        catch(IllegalArgumentException exception) {
            assertThat(exception.getMessage()).isEqualTo("Illegal argument : i");
        }
    }
}