package rpn;

public class CLI {
    static final String DELIMITER = " ";

    public static final void main(String[] args) {
        String expression = String.join(DELIMITER, args);

        System.out.println("About to evaluate '" + expression + "'");

        String result = Calculator.evaluate(expression);

        System.out.println("> " + result);
    }
}
