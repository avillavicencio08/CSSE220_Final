//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

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





/**public class Constant implements Differentiable{
    public String name;
    public boolean isNumber;

    public static Differentiable create(String name) {
        if (name.charAt(0)=='-') return Func.create("-", new Constant(name.substring(1)));
        return new Constant(name);
    }
    private Constant(String name) {
        try
        {
            double a;
            if ((a=Double.parseDouble(name))%1==0) {
                this.name = String.valueOf((int) a);
            } else {
                this.name = name;
            }
            this.isNumber = true;
        }
        catch(NumberFormatException e)
        {
            this.isNumber = false;
            this.name = name;
        }
    }

    @Override
    public Differentiable derivative() {
        return new Constant("0");
    }

    @Override
    public boolean isConstant() {
        return true;
    }
    @Override
    public boolean equals(Differentiable input) {
        return input.getClass()==Constant.class&&((Constant) input).name.equals(this.name);
    }
    @Override
    public String toString() {
        return name;
    }

    @Override
    public Double eval(Double x) {
        return switch (name) {
            case "e" -> 2.71;
            case "pi" -> 3.14;
            default -> Double.parseDouble(name);
        };
    }
}
**/