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
        return base.isConstant() && val.isConstant();
    }

    @Override
    public Expression calculateDerivative() {
        if (isConstant()) return Ops.c(0);

        // d/dx log_b(v) = v' / (v ln b). A variable base is rewritten as ln v / ln b by the parser.
        if (!base.isConstant()) {
            throw new ArithmeticException("log base must not depend on x.");
        }
        Expression u = Ops.mul(val, new Logarithm(Ops.c(Math.E), base));
        return Ops.mul(val.calculateDerivative(), Ops.pow(u, -1));
    }

    @Override
    public double evaluate(double x) {
        return Math.log(val.evaluate(x)) / Math.log(base.evaluate(x));
    }

    @Override
    public Expression createChild(ArrayList<Object> args) {
        return new Logarithm((Expression) args.get(0), (Expression) args.get(1));
    }

    @Override
    public ArrayList<Object> getFields() {
        ArrayList<Object> fields = new ArrayList<>();
        fields.add(base);
        fields.add(val);
        return fields;
    }

    public Expression getBase() { return base; }
    public Expression getInput() { return val; }
}
