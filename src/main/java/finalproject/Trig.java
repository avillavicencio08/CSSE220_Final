package finalproject;

import java.util.ArrayList;

public class Trig implements Expression {
    Expression theta;
    TrigFunction fn;

    public Trig(Expression theta, TrigFunction fn) {
        this.theta = theta;
        this.fn = fn;
    }

    @Override
    public double[] getDomain() {
        return theta.getDomain();
    }

    @Override
    public boolean isConstant() {
        return theta.isConstant();
    }

    @Override
    public Expression calculateDerivative() {
        if(isConstant()) return null;

        switch(fn) {
            case SIN -> {
                return new Arithmetic(new Trig(theta, TrigFunction.COS), theta.calculateDerivative(), Operation.MULTIPLY);
            }
            case COS -> {
                Expression dt = new Arithmetic(new Constant(-1), theta.calculateDerivative(), Operation.MULTIPLY);
                return new Arithmetic(new Trig(theta, TrigFunction.SIN), dt, Operation.MULTIPLY);
            }
            case TAN -> {
                Expression c = new Trig(theta, TrigFunction.COS);

                /* added tan'(x) = sec^2 (x). */
                return new Arithmetic(theta.calculateDerivative(), new Exponential(c, new Constant(-2)), Operation.MULTIPLY);
            }
            case SINH -> {
                return new Arithmetic(new Trig(theta, TrigFunction.COSH), theta.calculateDerivative(), Operation.MULTIPLY);
            }
            case COSH -> {
                return new Arithmetic(new Trig(theta, TrigFunction.SINH), theta.calculateDerivative(), Operation.MULTIPLY);
            }
            case TANH -> {
                Expression c = new Trig(theta, TrigFunction.COSH);

                /* added tanh'(x) = sech^2 (x). */
                return new Arithmetic(theta.calculateDerivative(), new Exponential(c, new Constant(-2)), Operation.MULTIPLY);
            }
        }

        /* if the code gets this far ... i don't even know */
        return null;
    }

    @Override
    public double evaluate(double x) {
        double o = Double.NaN;

        switch (this.fn) {
            case SIN -> o = Math.sin(theta.evaluate(x));
            case COS -> o = Math.cos(theta.evaluate(x));
            case SINH -> o = Math.sinh(theta.evaluate(x));
            case COSH -> o = Math.cosh(theta.evaluate(x));
        }

        return o;
    }

    @Override
    public Expression createChild(ArrayList<Object> args) {
        return new Trig((Expression) args.get(0), (TrigFunction) args.get(1));
    }

    @Override
    public ArrayList<Object> getFields() {
        ArrayList<Object> fields = new ArrayList<>();
        fields.add(theta);
        fields.add(fn);
        return fields;
    }

    public TrigFunction getFn() {
        return fn;
    }

    public Expression getTheta() {
        return theta;
    }
}
