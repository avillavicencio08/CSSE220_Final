package finalproject;

import java.util.ArrayList;

public class Variable implements Expression {
    private String name;

    public Variable(String name) {
        this.name = name;
    }

    public double[] getDomain() {
        return new double[]{Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY};
    }

    public boolean isConstant() {
        return false;
    }

    public Expression calculateDerivative() {
        return new Constant(1);
    }

    public double evaluate(double x) {
        return x;
    }

    public Expression createChild(ArrayList<Object> args) {
        return new Variable((String)args.getFirst());
    }

    public ArrayList<Object> getFields() {
        ArrayList<Object> fields = new ArrayList();
        fields.add(this.name);
        return fields;
    }

    public String getName() {
        return this.name;
    }
}
/**package finalproject;

public class Variable implements Differentiable {
    public String name;

    public Variable(String name) {
        this.name = name;
        System.out.println("make "+this);
    }
    @Override
    public Differentiable derivative() {
        return Constant.create("1");
    }
    @Override
    public boolean isConstant() {
        return false;
    }
    @Override
    public boolean equals(Differentiable input) {
        return true;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public Double eval(Double x) {
        return x;
    }
}

**/
