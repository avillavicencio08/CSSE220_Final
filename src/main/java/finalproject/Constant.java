package finalproject;

import java.util.ArrayList;

public class Constant implements Expression {
    int value;
    double dvalue;
    boolean decimal;

    public Constant(int value) {
        this.value = value;
        this.dvalue = (double)value;
        decimal = false;
    }

    public Constant(double value) {
        this.value = (int)value;
        this.dvalue = value;
        decimal = true;
    }

    public double[] getDomain() {
        return new double[]{Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY};
    }

    public boolean isConstant() {
        return true;
    }

    public Expression calculateDerivative() {
        return null;
    }

    public double evaluate(double x) {
        if(decimal) return dvalue;
        else return value;
    }

    @Override
    public Expression createChild(ArrayList<Object> args) {
        return new Constant((Double) args.getFirst());
    }

    public boolean isDecimal() {
        return decimal;
    }

    public double getDoubleValue() {
        return dvalue;
    }

    public int getIntValue() {
        return value;
    }
}
