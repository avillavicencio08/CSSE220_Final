package finalproject;

import java.util.ArrayList;

public class Exponential implements Expression {
    Expression base;
    Expression exponent;

    public Exponential(Expression base, Expression exponent) {
        this.base = base;
        this.exponent = exponent;
    }

    @Override
    public double[] getDomain() {
        // TODO: domain
        return new double[0];
    }

    @Override
    public boolean isConstant() {
        return (base.isConstant() && exponent.isConstant());
    }

    @Override
    public Expression calculateDerivative() {
        if (isConstant()) return new Constant(0); // FIX: was null

        if (exponent.isConstant() && !base.isConstant()) {
            // power rule + chain rule: [u^n]' = n u^(n-1) u'
            Expression c = new Exponential(base, new Arithmetic(exponent, new Constant(-1), Operation.ADD));
            Expression d = new Arithmetic(exponent, base.calculateDerivative(), Operation.MULTIPLY);

            return new Arithmetic(c, d, Operation.MULTIPLY);
        } else if (!exponent.isConstant() && base.isConstant()) {
            // exponential rule + chain rule: [a^u]' = a^u ln(a) u'
            // (no special case for e; the simplifier folds ln(e) to 1)
            Expression d = new Arithmetic(exponent.calculateDerivative(), new Logarithm(new Constant(Math.E), base), Operation.MULTIPLY);

            return new Arithmetic(this, d, Operation.MULTIPLY);
        } else {
            // both are functions of x: logarithmic differentiation, f' = f [v ln u]'
            Expression ln = new Arithmetic(exponent, new Logarithm(new Constant(Math.E), base), Operation.MULTIPLY);

            return new Arithmetic(this, ln.calculateDerivative(), Operation.MULTIPLY);
        }
    }

    @Override
    public double evaluate(double x) {
        return Math.pow(base.evaluate(x), exponent.evaluate(x));
    }

    @Override
    public Expression createChild(ArrayList<Object> args) {
        return new Exponential((Expression) args.get(0), (Expression) args.get(1));
    }

    @Override
    public ArrayList<Object> getFields() {
        ArrayList<Object> fields = new ArrayList<>();
        fields.add(base);
        fields.add(exponent);
        return fields;
    }

    public Expression getBase() { return base; }
    public Expression getExponent() { return exponent; }
}
