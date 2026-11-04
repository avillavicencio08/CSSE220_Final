package finalproject;

public class Arithmetic implements Expression {
    private Expression a;
    private Expression b;
    private Operation op;

    public Arithmetic(Expression a, Expression b, Operation op) {
        this.a = a;
        this.b = b;
        this.op = op;
    }

    @Override
    public double[] getDomain() {
        // TODO: calculate domains
        return new double[0];
    }

    @Override
    public boolean isConstant() {
        return (a.isConstant() || b.isConstant());
    }

    @Override
    public Expression calculateDerivative() {
        if(isConstant()) return null;

        if(op == Operation.ADD) {
            if(a.isConstant()) return a.calculateDerivative();
            else if(b.isConstant()) return b.calculateDerivative();

            return new Arithmetic(a.calculateDerivative(), b.calculateDerivative(), op);
        } else if(op == Operation.MULTIPLY) {
            Expression da;
            Expression db;

            // coefficient rule: [nf]' = n(f')
            if(a.isConstant()) return new Arithmetic(a, b.calculateDerivative(), Operation.MULTIPLY);
            else if(b.isConstant()) return new Arithmetic(b, a.calculateDerivative(), Operation.MULTIPLY);

            // product rule: [uv]' = uv' + vu'
            da = new Arithmetic(a, b.calculateDerivative(), Operation.MULTIPLY);
            db = new Arithmetic(b, a.calculateDerivative(), Operation.MULTIPLY);

            return new Arithmetic(da, db, Operation.ADD);
        } else if(op == Operation.DIVIDE) {
            // this one is incredibly annoying.
            Expression da;
            Expression db;
            Expression dc;

            if(a.isConstant()) {
                // da is constant. db isn't. convert to exponential then power rule.
                Expression d = new Exponential(b, new Constant(-1));

                return new Arithmetic(a, d.calculateDerivative(), Operation.MULTIPLY);
            } else if(b.isConstant()) {
                // classic. just coefficient rule.
                return new Arithmetic(a.calculateDerivative(), b, Operation.DIVIDE);
            } else {
                // quotient rule.
                da = new Arithmetic(a.calculateDerivative(), b, Operation.MULTIPLY);
                db = new Arithmetic(new Arithmetic(b.calculateDerivative(), a, Operation.MULTIPLY), new Constant(-1), Operation.MULTIPLY);
                dc = new Exponential(b, new Constant(2));

                return new Arithmetic(new Arithmetic(da, db, Operation.ADD), dc, Operation.DIVIDE);
            }
        }

        return null;
    }

    @Override
    public double evaluate(double x) {
        return 0;
    }

    public Expression getA() {
        return a;
    }

    public Expression getB() {
        return b;
    }

    public Operation getOperation() {
        return op;
    }
}
