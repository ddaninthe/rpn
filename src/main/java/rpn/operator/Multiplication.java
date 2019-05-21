package rpn.operator;

import java.util.Stack;

public class Multiplication implements IOperation {
    @Override
    public void compute(Stack<Float> stack) {
        float b = stack.pop();
        float a = stack.pop();
        stack.push(a * b);
    }
}
