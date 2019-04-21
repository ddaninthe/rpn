package rpn.operator;

public class Multiplication implements IOperation {
    private static Multiplication instance = new Multiplication();

    public static Multiplication getInstance() {
        return instance;
    }

    @Override
    public float compute(float a, float b) {
        return a * b;
    }
}
