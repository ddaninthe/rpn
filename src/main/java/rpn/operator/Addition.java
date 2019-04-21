package rpn.operator;

public class Addition implements IOperation {
    private static Addition instance = new Addition();

    public static Addition getInstance() {
        return instance;
    }

    @Override
    public float compute(float a, float b) {
        return a + b;
    }
}
