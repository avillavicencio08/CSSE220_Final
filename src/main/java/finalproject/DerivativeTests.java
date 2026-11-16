package finalproject;

/** Dependency-free tests. Run: java -cp out finalproject.DerivativeTests */
public class DerivativeTests {
    static int passed = 0, failed = 0;

    static void check(boolean ok, String msg) {
        if (ok) passed++;
        else { failed++; System.out.println("FAIL: " + msg); }
    }

    /** Exact text expectation through the same call the UI makes. */
    static void eq(String input, String expected) {
        String got = Differentiator.differentiate(input);
        check(got.equals(expected), "d/dx [" + input + "] expected [" + expected + "] got [" + got + "]");
    }

    /** Compare against a central finite difference at several points, and check print->parse round trip. */
    static void numeric(String input) {
        Expression e = ExpressionParser.parse(input);
        Expression d = Differentiator.derivative(input);
        int checked = 0;
        for (double x : new double[]{-1.7, -0.4, 0.3, 0.6, 1.9}) {
            double h = 1e-6;
            double f1 = e.evaluate(x + h), f0 = e.evaluate(x - h);
            double an = d.evaluate(x);
            if (Double.isNaN(f1) || Double.isNaN(f0) || Double.isNaN(an) || Double.isInfinite(an)) continue; // outside domain
            double num = (f1 - f0) / (2 * h);
            check(Math.abs(num - an) <= 1e-4 * (1 + Math.abs(an)),
                  "numeric [" + input + "] at x=" + x + ": finite diff " + num + " vs derivative " + an
                  + " (printed: " + Printer.print(d) + ")");
            checked++;
        }
        check(checked > 0, "numeric [" + input + "] had no valid sample points");
        String printed = Printer.print(d);
        double a = ExpressionParser.parse(printed).evaluate(0.3), b = d.evaluate(0.3);
        check((Double.isNaN(a) && Double.isNaN(b)) || Math.abs(a - b) < 1e-8, "round trip [" + input + "] -> [" + printed + "]");
    }

    static void error(String input) {
        String got = Differentiator.differentiate(input);
        check(got.startsWith("Error:"), "expected error for [" + input + "] got [" + got + "]");
    }

    public static void main(String[] args) {
        // ---- the basics the UI must get right ----
        eq("x", "1");
        eq("5", "0");
        eq("2x", "2");
        eq("x^2", "2*x");
        eq("x^3", "3*x^2");
        eq("x^2 + sin(x)", "2*x + cos(x)");
        eq("3x^2+2x-5", "6*x + 2");
        eq("sin(x)", "cos(x)");
        eq("cos(x)", "-sin(x)");
        eq("tan(x)", "cos(x)^(-2)");
        eq("2sin(x)", "2*cos(x)");
        eq("sin(x^2)", "cos(x^2)*2*x");
        eq("ln(x)", "1/x");
        eq("e^x", "e^x");
        eq("sqrt(x)", "0.5*x^(-0.5)");

        // ---- different ways people type the same thing ----
        for (String s : new String[]{"sin(x)", "SIN(x)", "SIN(X)", "Sin(x)", " sin ( x ) ", "sin x", "sinx", "sin((x))", "(sin(x))"}) eq(s, "cos(x)");
        for (String s : new String[]{"x^2", "X^2", " x ^ 2 ", "(x)^(2)", "x^2.0", "(x^2)", "x**2".replace("**", "^")}) eq(s, "2*x");

        // ---- second derivatives (derivative output must be re-differentiable) ----
        eq("x^3", "3*x^2");
        check(Differentiator.differentiate("3*x^2").equals("6*x"), "second derivative of x^3");
        check(Differentiator.differentiate("cos(x)").equals("-sin(x)"), "d/dx cos(x)");
        check(Differentiator.differentiate("-sin(x)").equals("-cos(x)"), "d/dx -sin(x)");

        // ---- numeric cross-check on a wide range ----
        String[] exprs = {
            "x", "x^2", "x^3 - 4x + 1", "(x^2+1)^3", "1/(x^2+1)", "x/(1+x^2)", "5/x^2", "x^2*sin(x)", "(x+1)(x-1)",
            "sin(x)", "cos(x)", "tan(x)", "sec(x)", "csc(x)", "cot(x)", "sin(x)^2", "sin(cos(x))", "sin(2x)", "cos(3x+1)",
            "sin(x)*cos(x)", "sinh(x)", "cosh(x)", "tanh(x)", "sech(x)", "csch(x)", "coth(x)",
            "arcsin(x)", "arccos(x)", "arctan(x)", "arcsinh(x)", "arccosh(x+2)", "arctanh(x)",
            "arcsec(x+1)", "arccsc(x+1)", "arccot(x)", "arcsech(x)", "arccsch(x)", "arccoth(x+2)",
            "ln(x)", "ln(x^2+1)", "log(x)", "log(2,x)", "log(x,2)", "e^x", "e^(x^2)", "2^x", "2^-x", "x^x", "x^(x+1)",
            "sqrt(x)", "sqrt(x^2+1)", "exp(2x)", "-x^2", "pi*x", "-sin(x)", "x^-1", "x^2^0.5", "7"
        };
        for (String s : exprs) numeric(s);

        // ---- bad input must report an error, never crash ----
        for (String s : new String[]{"", "2+", "sin(", "foo(x)", "3 $ 4", "(x", "x)", "y^2", "log(1,2,3)"}) error(s);

        System.out.println(passed + " passed, " + failed + " failed");
        if (failed > 0) System.exit(1);
    }
}
