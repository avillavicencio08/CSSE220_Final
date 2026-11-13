package finalproject;

/** Cleans up the (very verbose) trees that calculateDerivative produces. */
public final class Simplifier {
    private Simplifier() {}

    private static boolean isNum(Expression e, double v) {
        return e instanceof Constant c && c.getDoubleValue() == v;
    }

    public static Expression simplify(Expression e) {
        if (e instanceof Constant || e instanceof Variable) return e;

        if (e.isConstant()) { // fold constant subtrees
            double v = e.evaluate(0);
            if (!Double.isNaN(v) && !Double.isInfinite(v)) return new Constant(v);
        }

        if (e instanceof Arithmetic ar) {
            Expression a = simplify(ar.getA());
            Expression b = simplify(ar.getB());
            Operation op = ar.getOperation();

            if (op == Operation.ADD) {
                if (isNum(a, 0)) return b;
                if (isNum(b, 0)) return a;
                return new Arithmetic(a, b, op);
            }
            if (op == Operation.MULTIPLY) {
                if (isNum(a, 0) || isNum(b, 0)) return new Constant(0);
                if (isNum(a, 1)) return b;
                if (isNum(b, 1)) return a;
                if (b instanceof Constant && !(a instanceof Constant)) { Expression t = a; a = b; b = t; }
                // c1 * (c2 * f) -> (c1*c2) * f
                if (a instanceof Constant ca && b instanceof Arithmetic bm
                        && bm.getOperation() == Operation.MULTIPLY && bm.getA() instanceof Constant cb) {
                    return simplify(new Arithmetic(new Constant(ca.getDoubleValue() * cb.getDoubleValue()), bm.getB(), op));
                }
                // f * (1/g) -> f/g
                if (b instanceof Arithmetic bd && bd.getOperation() == Operation.DIVIDE && isNum(bd.getA(), 1)) {
                    return new Arithmetic(a, bd.getB(), Operation.DIVIDE);
                }
                if (a instanceof Arithmetic ad && ad.getOperation() == Operation.DIVIDE && isNum(ad.getA(), 1)) {
                    return new Arithmetic(b, ad.getB(), Operation.DIVIDE);
                }
                return new Arithmetic(a, b, op);
            }
            // DIVIDE
            if (isNum(b, 1)) return a;
            if (isNum(a, 0)) return new Constant(0);
            if (Printer.print(a).equals(Printer.print(b))) return new Constant(1); // u/u
            return new Arithmetic(a, b, op);
        }
        if (e instanceof Exponential ex) {
            Expression base = simplify(ex.getBase());
            Expression exp = simplify(ex.getExponent());
            if (isNum(exp, 1)) return base;
            if (isNum(exp, 0)) return new Constant(1);
            // (u^m)^n -> u^(m*n) when n is an integer (always valid)
            if (base instanceof Exponential in && in.getExponent() instanceof Constant m
                    && exp instanceof Constant n && n.getDoubleValue() == Math.rint(n.getDoubleValue())) {
                return simplify(new Exponential(in.getBase(), new Constant(m.getDoubleValue() * n.getDoubleValue())));
            }
            if (isNum(exp, -1)) return new Arithmetic(new Constant(1), base, Operation.DIVIDE);
            return new Exponential(base, exp);
        }
        if (e instanceof Trig t) return new Trig(simplify(t.getTheta()), t.getFn());
        if (e instanceof InvTrig t) return new InvTrig(simplify(t.getInput()), t.getFn());
        if (e instanceof Logarithm l) return new Logarithm(simplify(l.getBase()), simplify(l.getInput()));
        return e;
    }
}
