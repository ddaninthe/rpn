package rpn;

class Tokenizer {
    static String[] tokenize(String expression) {
        expression = expression.replace(',','.');
        return expression.split("\\s");
    }
}