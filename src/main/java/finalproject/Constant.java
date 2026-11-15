package finalproject;

public class Constant implements Differentiable{
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
