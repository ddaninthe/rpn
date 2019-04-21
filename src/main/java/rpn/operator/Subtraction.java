package rpn.operator;

public class Subtraction implements IOperation {
    private static Subtraction instance = new Subtraction();

    public static Subtraction getInstance() {
        return instance;
    }

    @Override
    public float compute(float a, float b) {
        return a - b;
    }
}
