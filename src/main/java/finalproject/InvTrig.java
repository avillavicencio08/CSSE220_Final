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
        if(isConstant()) return null;

        Expression pcr = null;

        switch (fn) {
            case SIN -> {
                Expression z = new Arithmetic(new Constant(-1), new Exponential(in, new Constant(2)), Operation.MULTIPLY);
                Expression u = new Arithmetic(new Constant(1), z, Operation.ADD);

                pcr = new Exponential(u, new Constant(-0.5));
            }
            case COS -> {
                Expression z = new Arithmetic(new Constant(-1), new Exponential(in, new Constant(2)), Operation.MULTIPLY);
                Expression u = new Arithmetic(new Constant(1), z, Operation.ADD);

                pcr = new Arithmetic(new Constant(-0.5), new Exponential(u, new Constant(-1)), Operation.MULTIPLY);
            }
            case TAN -> {
                Expression z = new Arithmetic(new Constant(1), new Exponential(in, new Constant(2)), Operation.ADD);

                pcr = new Exponential(z, new Constant(-1));
            }
            case SINH -> {
                Expression a = new Arithmetic(new Constant(1), new Exponential(in, new Constant(2)), Operation.ADD);
                Expression b = new Arithmetic(in, new Exponential(a, new Constant(.5)), Operation.ADD);

                pcr = new Logarithm(new Constant(Math.E), b);
            }
            case COSH -> {
                Expression a = new Arithmetic(new Constant(-1), new Exponential(in, new Constant(2)), Operation.ADD);
                Expression b = new Arithmetic(in, new Exponential(a, new Constant(.5)), Operation.ADD);

                pcr = new Logarithm(new Constant(Math.E), b);
            }
            case TANH -> {
                Expression a = new Arithmetic(new Constant(1), in, Operation.ADD);
                Expression b = new Arithmetic(new Constant(1), new Arithmetic(new Constant(-1), in, Operation.MULTIPLY), Operation.ADD);
                Expression c = new Logarithm(new Constant(Math.E), new Arithmetic(a, new Exponential(b, new Constant(-1)), Operation.MULTIPLY));

                pcr = new Arithmetic(new Constant(.5), c, Operation.MULTIPLY);
            }
        }

        return new Arithmetic(pcr, in.calculateDerivative(), Operation.MULTIPLY);
    }

    @Override
    public double evaluate(double x) {
        return 0;
    }
}
