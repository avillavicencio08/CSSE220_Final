package finalproject;

import java.util.ArrayList;

public class Constant implements Expression {
    int value;
    double dvalue;
    boolean decimal;

    public Constant(int value) {
        this.value = value;
        this.dvalue = (double) value;
        decimal = false;
    }

    public Constant(double value) {
        this.value = (int) value;
        this.dvalue = value;
        decimal = true;
    }

    public double[] getDomain() {
        return new double[]{Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY};
    }

    public boolean isConstant() {
        return true;
    }

    // FIX: used to return null, which caused NullPointerExceptions further up the tree.
    public Expression calculateDerivative() {
        return new Constant(0);
    }

    public double evaluate(double x) {
        return dvalue;
    }

    @Override
    public Expression createChild(ArrayList<Object> args) {
        return new Constant((Double) args.getFirst());
    }

    @Override
    public ArrayList<Object> getFields() {
        ArrayList<Object> fields = new ArrayList<>();
        fields.add(this.dvalue);
        return fields;
    }

    public boolean isDecimal() { return decimal; }
    public double getDoubleValue() { return dvalue; }
    public int getIntValue() { return value; }
}
