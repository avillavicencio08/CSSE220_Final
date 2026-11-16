package finalproject;

/** Single entry point used by the UI: text in, simplified derivative text out. */
public final class Differentiator {
    private Differentiator() {}

    public static Expression derivative(String input) {
        Expression e = ExpressionParser.parse(input);
        return Simplifier.simplify(e.calculateDerivative());
    }

    /** Never throws; bad input comes back as "Error: ...". */
    public static String differentiate(String input) {
        try {
            return Printer.print(derivative(input));
        } catch (RuntimeException ex) {
            return "Error: " + ex.getMessage();
        }
    }
}
