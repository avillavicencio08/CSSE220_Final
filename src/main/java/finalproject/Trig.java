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
        if (isConstant()) return Ops.c(0);

        // derivative of the outer function, evaluated at theta
        Expression outer = switch (fn) {
            case SIN  -> new Trig(theta, TrigFunction.COS);
            case COS  -> Ops.neg(new Trig(theta, TrigFunction.SIN));
            case TAN  -> Ops.pow(new Trig(theta, TrigFunction.COS), -2);          // sec^2
            case CSC  -> Ops.neg(Ops.mul(new Trig(theta, TrigFunction.CSC), new Trig(theta, TrigFunction.COT)));
            case SEC  -> Ops.mul(new Trig(theta, TrigFunction.SEC), new Trig(theta, TrigFunction.TAN));
            case COT  -> Ops.neg(Ops.pow(new Trig(theta, TrigFunction.CSC), 2));
            case SINH -> new Trig(theta, TrigFunction.COSH);
            case COSH -> new Trig(theta, TrigFunction.SINH);
            case TANH -> Ops.pow(new Trig(theta, TrigFunction.COSH), -2);         // sech^2
            case CSCH -> Ops.neg(Ops.mul(new Trig(theta, TrigFunction.CSCH), new Trig(theta, TrigFunction.COTH)));
            case SECH -> Ops.neg(Ops.mul(new Trig(theta, TrigFunction.SECH), new Trig(theta, TrigFunction.TANH)));
            case COTH -> Ops.neg(Ops.pow(new Trig(theta, TrigFunction.CSCH), 2));
        };
        // chain rule
        return Ops.mul(outer, theta.calculateDerivative());
    }

    @Override
    public double evaluate(double x) {
        double t = theta.evaluate(x);
        return switch (fn) {
            case SIN -> Math.sin(t);
            case COS -> Math.cos(t);
            case TAN -> Math.tan(t);
            case CSC -> 1 / Math.sin(t);
            case SEC -> 1 / Math.cos(t);
            case COT -> 1 / Math.tan(t);
            case SINH -> Math.sinh(t);
            case COSH -> Math.cosh(t);
            case TANH -> Math.tanh(t);
            case CSCH -> 1 / Math.sinh(t);
            case SECH -> 1 / Math.cosh(t);
            case COTH -> 1 / Math.tanh(t);
        };
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

    public TrigFunction getFn() { return fn; }
    public Expression getTheta() { return theta; }
}
