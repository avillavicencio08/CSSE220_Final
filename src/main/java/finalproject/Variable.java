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
        return new Constant(1);
    }

    @Override
    public double evaluate(double x) {
        return x;
    }

    @Override
    public Expression createChild(ArrayList<Object> args) {
        return new Variable((String) args.getFirst());
    }

    @Override
    public ArrayList<Object> getFields() {
        ArrayList<Object> fields = new ArrayList<>();
        fields.add(name);
        return fields;
    }


    public String getName() {
        return name;
    }
}
