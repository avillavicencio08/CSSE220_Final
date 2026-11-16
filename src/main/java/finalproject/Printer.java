package finalproject;

/** Turns an Expression tree back into readable infix text. */
public final class Printer {
    private Printer() {}

    public static String print(Expression e) { return p(e, 0); }

    static String num(double d) {
        if (d == Math.rint(d) && Math.abs(d) < 1e15) return String.valueOf((long) d);
        if (Math.abs(d) >= 1e-4) return String.valueOf(Math.round(d * 1e10) / 1e10);
        return String.valueOf(d);
    }

    // precedence: 1 = + -, 2 = * /, 3 = ^, 4 = atom / function call
    private static String p(Expression e, int ctx) {
        String s;
        int prec = 4;

        if (e instanceof Constant c) {
            double d = c.getDoubleValue();
            if (Math.abs(d - Math.E) < 1e-12) s = "e";
            else if (Math.abs(d - Math.PI) < 1e-12) s = "pi";
            else s = num(d);
            if (d < 0) prec = 1;
        } else if (e instanceof Variable v) {
            s = v.getName();
        } else if (e instanceof Arithmetic ar) {
            Expression a = ar.getA(), b = ar.getB();
            switch (ar.getOperation()) {
                case ADD -> {
                    prec = 1;
                    if (b instanceof Arithmetic m && m.getOperation() == Operation.MULTIPLY
                            && m.getA() instanceof Constant k && k.getDoubleValue() < 0) {
                        Expression pos = k.getDoubleValue() == -1 ? m.getB()
                                : new Arithmetic(new Constant(-k.getDoubleValue()), m.getB(), Operation.MULTIPLY);
                        s = p(a, 1) + " - " + p(pos, 2);
                    } else if (b instanceof Constant k && k.getDoubleValue() < 0) {
                        s = p(a, 1) + " - " + num(-k.getDoubleValue());
                    } else {
                        s = p(a, 1) + " + " + p(b, 1);
                    }
                }
                case MULTIPLY -> {
                    if (a instanceof Constant k && k.getDoubleValue() < 0) {
                        prec = 1;
                        Expression pos = k.getDoubleValue() == -1 ? b
                                : new Arithmetic(new Constant(-k.getDoubleValue()), b, Operation.MULTIPLY);
                        s = "-" + p(pos, 2);
                    } else {
                        prec = 2;
                        s = p(a, 2) + "*" + p(b, 2);
                    }
                }
                default -> { // DIVIDE
                    if (a instanceof Constant k && k.getDoubleValue() < 0) {
                        prec = 1;
                        s = "-" + p(new Arithmetic(new Constant(-k.getDoubleValue()), b, Operation.DIVIDE), 2);
                    } else {
                        prec = 2;
                        s = p(a, 2) + "/" + p(b, 3);
                    }
                }
            }
        } else if (e instanceof Exponential ex) {
            prec = 3;
            s = p(ex.getBase(), 4) + "^" + p(ex.getExponent(), 3);
        } else if (e instanceof Trig t) {
            s = t.getFn().name().toLowerCase() + "(" + p(t.getTheta(), 0) + ")";
        } else if (e instanceof InvTrig t) {
            s = "arc" + t.getFn().name().toLowerCase() + "(" + p(t.getInput(), 0) + ")";
        } else if (e instanceof Logarithm l) {
            if (l.getBase() instanceof Constant k && Math.abs(k.getDoubleValue() - Math.E) < 1e-12) {
                s = "ln(" + p(l.getInput(), 0) + ")";
            } else {
                s = "log(" + p(l.getBase(), 0) + ", " + p(l.getInput(), 0) + ")";
            }
        } else {
            s = String.valueOf(e);
        }
        return prec < ctx ? "(" + s + ")" : s;
    }
}
