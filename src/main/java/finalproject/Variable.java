package finalproject;

import java.util.ArrayList;

public class Variable implements Expression {
    private String name;

    public Variable(String name) {
        this.name = name;
    }

    @Override
    public double[] getDomain() {
        return new double[]{Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY};
    }

    @Override
    public boolean isConstant() {
        return false;
    }

    @Override
    public Expression calculateDerivative() {
        return null;
    }

    @Override
    public double evaluate(double x) {
        return x;
    }

    @Override
    public Expression createChild(ArrayList<Object> args) {
        return new Variable((String) args.getFirst());
    }


    public String getName() {
        return name;
    }
}
