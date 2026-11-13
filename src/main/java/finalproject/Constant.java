package finalproject;

import java.util.ArrayList;

public class Constant implements Expression {
    int value;
    double dvalue;
    boolean decimal;

    public Constant(int value) {
        this.value = value;
        this.dvalue = (double)value;
        this.decimal = false;
    }

    public Constant(double value) {
        this.value = (int)value;
        this.dvalue = value;
        this.decimal = true;
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
        return this.decimal ? this.dvalue : (double)this.value;
    }

    public Expression createChild(ArrayList<Object> args) {
        return new Constant((Double)args.getFirst());
    }

    public ArrayList<Object> getFields() {
        ArrayList<Object> fields = new ArrayList();
        fields.add(this.dvalue);
        return fields;
    }

    public boolean isDecimal() {
        return this.decimal;
    }

    public double getDoubleValue() {
        return this.dvalue;
    }

    public int getIntValue() {
        return this.value;
    }
}
