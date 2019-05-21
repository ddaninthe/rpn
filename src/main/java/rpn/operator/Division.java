package rpn.operator;

import java.util.Stack;

public class Division implements IOperation {
    @Override
    public void compute(Stack<Float> stack) {
        float b = stack.pop();
        float a = stack.pop();

        if (b != 0) {
            stack.push(a / b);
        } else {
            stack.push(a);
            stack.push(b);
        }
    }
}
