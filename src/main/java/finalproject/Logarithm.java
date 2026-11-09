package finalproject;

import java.util.ArrayList;

public class Logarithm implements Expression {
    Expression base;
    Expression val;

    public Logarithm(Expression base, Expression val) {
        this.base = base;
        this.val = val;
    }

    @Override
    public double[] getDomain() {
        return new double[0];
    }

    @Override
    public boolean isConstant() {
        return (base.isConstant() && val.isConstant());
    }

    @Override
    public Expression calculateDerivative() {
        // we gonna ignore the case where base is variable
        // cause what the actual fuck
        // if that happens someone before us didn't do their job

        if(isConstant()) return null;

        if(base.isConstant() && !val.isConstant()) {
            Expression u = new Arithmetic(val, new Logarithm(new Constant(Math.E), base), Operation.MULTIPLY);
            Expression v = val.calculateDerivative();

            return new Arithmetic(v,new Exponential(u, new Constant(-1)), Operation.MULTIPLY);
        } else {
            // what the fuck.
            // krill yourself
            throw new ArithmeticException("log base should not be variable.");
        }
    }



    @Override
    public double evaluate(double x) {
        return 0;
    }

    @Override
    public Expression createChild(ArrayList<Object> args) {
        return new Logarithm((Expression) args.get(0), (Expression) args.get(1));
    }

    public Expression getBase() {
        return base;
    }

    public Expression getInput() {
        return val;
    }
}
