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
        // Todo: this shit
        return new double[0];
    }

    @Override
    public boolean isConstant() {
        return (base.isConstant() || exponent.isConstant());
    }

    @Override
    public Expression calculateDerivative() {
        // there are two main cases, e is constant and b isn't
        // and b is constant and e isnt.

        if(isConstant()) return null;

        // power rule: [x^n]' = nx^(n-1), chain rule
        if(exponent.isConstant() && !base.isConstant()) {
            Expression c = new Exponential(base, new Arithmetic(exponent, new Constant(-1), Operation.ADD));
            Expression d = new Arithmetic(exponent, base.calculateDerivative(), Operation.MULTIPLY);

            return new Arithmetic(c, d, Operation.MULTIPLY);
        } else if(!exponent.isConstant() && base.isConstant()) {
            // exponential rule: [a^x] = a^x ln a, chain rule
            // not adding a special case for e, that shit is annoying.
            // simplification should take care of it.

            Expression c = this;
            Expression d = new Arithmetic(exponent.calculateDerivative(), new Logarithm(new Constant(Math.E), base), Operation.MULTIPLY);

            return new Arithmetic(c,d,Operation.MULTIPLY);
        } else {
            // logarithmic differentiation.
            // used exclusively for the case that
            // a^b are both functions of x
            // f' = f [ln f]'

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

    public Expression getBase() {
        return base;
    }

    public Expression getExponent() {
        return exponent;
    }
}
