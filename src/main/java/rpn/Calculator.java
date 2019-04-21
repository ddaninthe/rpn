package rpn;

import rpn.operator.*;

import java.security.InvalidParameterException;
import java.util.Stack;

class Calculator {
    /**
     * Evaluate an arithmetic expression.
     *
     * @param expression  arithmetic expression to evaluate
     * @return  the results of the computation
     * @throws IllegalArgumentException  if there is an incorrect number of char of if a char is invalid.
     */
    static String evaluate(String expression) throws IllegalArgumentException {
        // Initialize
        final Stack<Float> stack = new Stack<>();
        final String[] parts = expression.split(CLI.DELIMITER);

        // Compute
        IOperation operation;
        for (String part : parts) {
            try {
                float number = Float.parseFloat(part);
                stack.push(number);
            } catch (NumberFormatException e) {
                // Not a number;
                operation = parseOperation(part);

                if (stack.size() < 2) {
                    throw new InvalidParameterException("Parameters count is invalid.");
                } else {
                    float b = stack.pop(); // First item is on top of the stack
                    float a = stack.pop();

                    stack.push(operation.compute(a, b));
                }
            }
        }

        // Build string result
        String result = "";
        for (float number : stack) {
            result += number + " ";
        }
        result = result.substring(0, result.length() - 1);

        return result;
    }

    /**
     * Parses a String to an IOperation.
     *
     * @param operation  an arithmetic operation
     * @return an {@link IOperation} of the correct type
     * @throws UnsupportedOperationException if the {@code operation} is invalid
     */
    private static IOperation parseOperation(String operation) throws IllegalArgumentException {
        switch (operation) {
            case "+":
                return Addition.getInstance();
            case "-":
                return Subtraction.getInstance();
            case "*":
                return Multiplication.getInstance();
            case "/":
                return Division.getInstance();
            default:
                throw new IllegalArgumentException("Illegal argument : " + operation);
        }
    }
}
