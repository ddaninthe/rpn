package rpn;

import rpn.operator.IOperation;

import java.util.Stack;

class Calculator {

    static Stack<Float> calculate(String[] tokens) {
        // Initialize
        final Stack<Float> stack = new Stack<>();

        IOperation operation;
        for (String token : tokens) {
            operation = IOperation.find(token);

            if (operation != null) {
                operation.compute(stack);
            } else if (token.matches("^[+-]?\\d*(\\.\\d*)?$")) {
                stack.push(Float.parseFloat(token));
            }
        }
        return stack;
    }
}
