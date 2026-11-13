public class Variable implements Differentiable {
    public String name;

    public Variable(String name) {
        this.name = name;
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
        return input.getClass()==Variable.class&&((Variable) input).name.equals(this.name);
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
