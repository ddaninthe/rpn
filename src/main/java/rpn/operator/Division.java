package rpn.operator;

public class Division implements IOperation {
    private static Division instance = new Division();

    public static Division getInstance() {
        return instance;
    }

    @Override
    public float compute(float a, float b) {
        return a / b;
    }
}
