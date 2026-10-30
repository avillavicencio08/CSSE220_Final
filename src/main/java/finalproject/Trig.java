package finalproject;

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
            case SINH -> {
                return new Arithmetic(new Trig(theta, TrigFunction.COSH), theta.calculateDerivative(), Operation.MULTIPLY);
            }
            case COSH -> {
                return new Arithmetic(new Trig(theta, TrigFunction.SINH), theta.calculateDerivative(), Operation.MULTIPLY);
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
}
