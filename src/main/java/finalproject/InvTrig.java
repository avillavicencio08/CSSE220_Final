package finalproject;

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
        return null;
    }

    @Override
    public double evaluate(double x) {
        return 0;
    }
}
