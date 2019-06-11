package rpn.operator;

import java.util.Stack;

public interface Operator {
    Stack<Double> getStack();
    void setStack(Stack<Double> stack);
    void setExpressionId(String expressionId);
}
