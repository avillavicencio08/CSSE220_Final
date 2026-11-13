package finalproject;

import java.util.ArrayList;

public class InvTrig implements Expression {
    Expression in;
    TrigFunction fn;

    public InvTrig(Expression in, TrigFunction fn) {
        this.in = in;
        this.fn = fn;
    }

    @Override
    public double[] getDomain() {
        return new double[0];
    }

    @Override
    public boolean isConstant() {
        return in.isConstant();
    }

    @Override
    public Expression calculateDerivative() {
        if (isConstant()) return Ops.c(0);

        Expression u2 = Ops.pow(in, 2);
        Expression oneMinusU2 = Ops.add(Ops.c(1), Ops.neg(u2));
        Expression onePlusU2 = Ops.add(Ops.c(1), u2);
        Expression u2MinusOne = Ops.add(u2, Ops.c(-1));
        Expression absInv = Ops.pow(u2, -0.5); // 1/|u|

        Expression outer = switch (fn) {
            case SIN  -> Ops.pow(oneMinusU2, -0.5);
            case COS  -> Ops.neg(Ops.pow(oneMinusU2, -0.5));
            case TAN  -> Ops.pow(onePlusU2, -1);
            case CSC  -> Ops.neg(Ops.mul(absInv, Ops.pow(u2MinusOne, -0.5)));
            case SEC  -> Ops.mul(absInv, Ops.pow(u2MinusOne, -0.5));
            case COT  -> Ops.neg(Ops.pow(onePlusU2, -1));
            case SINH -> Ops.pow(onePlusU2, -0.5);
            case COSH -> Ops.pow(u2MinusOne, -0.5);
            case TANH, COTH -> Ops.pow(oneMinusU2, -1);
            case CSCH -> Ops.neg(Ops.mul(absInv, Ops.pow(onePlusU2, -0.5)));
            case SECH -> Ops.neg(Ops.mul(Ops.pow(in, -1), Ops.pow(oneMinusU2, -0.5)));
        };
        return Ops.mul(outer, in.calculateDerivative());
    }

    private static double asinh(double v) { return Math.log(v + Math.sqrt(v * v + 1)); }
    private static double acosh(double v) { return Math.log(v + Math.sqrt(v * v - 1)); }
    private static double atanh(double v) { return 0.5 * Math.log((1 + v) / (1 - v)); }

    @Override
    public double evaluate(double x) {
        double v = in.evaluate(x);
        return switch (fn) {
            case SIN -> Math.asin(v);
            case COS -> Math.acos(v);
            case TAN -> Math.atan(v);
            case CSC -> Math.asin(1 / v);
            case SEC -> Math.acos(1 / v);
            case COT -> Math.atan(1 / v);
            case SINH -> asinh(v);
            case COSH -> acosh(v);
            case TANH -> atanh(v);
            case CSCH -> asinh(1 / v);
            case SECH -> acosh(1 / v);
            case COTH -> atanh(1 / v);
        };
    }

    @Override
    public Expression createChild(ArrayList<Object> args) {
        return new InvTrig((Expression) args.get(0), (TrigFunction) args.get(1));
    }

    @Override
    public ArrayList<Object> getFields() {
        ArrayList<Object> fields = new ArrayList<>();
        fields.add(in);
        fields.add(fn);
        return fields;
    }

    public Expression getInput() { return in; }
    public TrigFunction getFn() { return fn; }
}
