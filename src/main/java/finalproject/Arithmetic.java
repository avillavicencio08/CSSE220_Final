package finalproject;

import java.util.ArrayList;

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
        return (a.isConstant() && b.isConstant());
    }

    @Override
    public Expression calculateDerivative() {
        if (isConstant()) return new Constant(0); // FIX: was null

        if (op == Operation.ADD) {
            if (a.isConstant()) return b.calculateDerivative();
            else if (b.isConstant()) return a.calculateDerivative();

            return new Arithmetic(a.calculateDerivative(), b.calculateDerivative(), op);
        } else if (op == Operation.MULTIPLY) {
            // coefficient rule: [nf]' = n(f')
            if (a.isConstant()) return new Arithmetic(a, b.calculateDerivative(), Operation.MULTIPLY);
            else if (b.isConstant()) return new Arithmetic(b, a.calculateDerivative(), Operation.MULTIPLY);

            // product rule: [uv]' = uv' + vu'
            Expression da = new Arithmetic(a, b.calculateDerivative(), Operation.MULTIPLY);
            Expression db = new Arithmetic(b, a.calculateDerivative(), Operation.MULTIPLY);

            return new Arithmetic(da, db, Operation.ADD);
        } else if (op == Operation.DIVIDE) {
            if (a.isConstant()) {
                // constant numerator: rewrite as a * b^-1, then power rule.
                Expression d = new Exponential(b, new Constant(-1));

                return new Arithmetic(a, d.calculateDerivative(), Operation.MULTIPLY);
            } else if (b.isConstant()) {
                // constant denominator: coefficient rule.
                return new Arithmetic(a.calculateDerivative(), b, Operation.DIVIDE);
            } else {
                // quotient rule: (a'b - b'a) / b^2
                Expression da = new Arithmetic(a.calculateDerivative(), b, Operation.MULTIPLY);
                Expression db = new Arithmetic(new Arithmetic(b.calculateDerivative(), a, Operation.MULTIPLY), new Constant(-1), Operation.MULTIPLY);
                Expression dc = new Exponential(b, new Constant(2));

                return new Arithmetic(new Arithmetic(da, db, Operation.ADD), dc, Operation.DIVIDE);
            }
        }

        return null;
    }

    @Override
    public double evaluate(double x) {
        double r = Double.NaN;

        switch (op) {
            case ADD -> r = a.evaluate(x) + b.evaluate(x);
            case MULTIPLY -> r = a.evaluate(x) * b.evaluate(x);
            case DIVIDE -> r = a.evaluate(x) / b.evaluate(x);
        }

        return r;
    }

    @Override
    public Expression createChild(ArrayList<Object> args) {
        return new Arithmetic((Expression) args.get(0), (Expression) args.get(1), (Operation) args.get(2));
    }

    @Override
    public ArrayList<Object> getFields() {
        ArrayList<Object> fields = new ArrayList<>();
        fields.add(a);
        fields.add(b);
        fields.add(op);
        return fields;
    }

    public Expression getA() { return a; }
    public Expression getB() { return b; }
    public Operation getOperation() { return op; }
}
