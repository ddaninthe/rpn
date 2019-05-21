package rpn;

import java.util.Stack;

public class CLI {
    public static final void main(String[] args) {
        String expression = String.join("\\s", args);

        System.out.println("About to calculate '" + expression + "'");

        String[] tokens = Tokenizer.tokenize(expression);
        Stack<Float> stack = Calculator.calculate(tokens);

        // Build string result
        String result = "";
        for (float number : stack) {
            result += number + " ";
        }
        result = result.substring(0, result.length() - 1);

        System.out.println("> " + result);
    }
}
