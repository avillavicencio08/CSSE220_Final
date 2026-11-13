package finalproject;

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
