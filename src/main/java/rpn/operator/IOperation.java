package rpn.operator;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public interface IOperation {
    Map<String, IOperation> operators = new HashMap<String, IOperation>() {{
        put("+", new Addition());
        put("-", new Subtraction());
        put("*", new Multiplication());
        put("/", new Division());
    }};

    static IOperation find(String token) {
        return operators.get(token);
    }

    void compute(Stack<Float> stack);
}
