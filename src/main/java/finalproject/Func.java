import java.awt.geom.Arc2D;
import java.util.Map;
import java.util.function.Function ;

public class Func implements Differentiable{
    public String name;
    public Differentiable input;

    static Map<String, Function<Differentiable, Differentiable>> derivatives = Map.ofEntries(
            Map.entry("sin", (x) -> Func.create("cos", x)),
            Map.entry("cos", (x) -> Func.create("-", Func.create("sin", x))),
            Map.entry("tan", (x) -> Operator.create(Func.create("cos", x), "^", Constant.create("-2"))),
            Map.entry("arcsin", (x) -> Operator.create(Operator.create(Constant.create("1"), "-", Operator.create(x, "^", Constant.create("2"))), "^", Constant.create("-0.5"))),
            Map.entry("arccos", (x) -> Func.create("neg", Operator.create(Operator.create(Constant.create("1"), "-", Operator.create(x, "^", Constant.create("2"))), "^", Constant.create("-0.5")))),
            Map.entry("arctan", (x) -> Operator.create(Constant.create("1"), "/", Operator.create(Constant.create("1"), "+", Operator.create(x, "^", Constant.create("2"))))),
            Map.entry("ln", (x) -> Operator.create(Constant.create("1"), "/", x)),
            Map.entry("-", (x) -> Constant.create("-1")),
            Map.entry("exp", (x) -> Func.create("exp", x))
    );
    public static Differentiable create(String name, Differentiable input) {
        if (input instanceof Func) {
            if (((Func) input).name.equals("-")) {
                if (name.equals("-")) return ((Func) input).input;
                if (name.equals("exp")&&((Func) input).input instanceof Func&&((Func) ((Func) input).input).name.equals("ln")) return Operator.create(Constant.create("1"), "/", ((Func) ((Func) input).input).input);
                if (name.equals("sin")&&((Func) input).input instanceof Func&& ((Func) ((Func) input).input).name.equals("arcsin")) return Func.create("-", ((Func) ((Func) input).input).input);
                if (name.equals("tan")&&((Func) input).input instanceof Func&& ((Func) ((Func) input).input).name.equals("arctan")) return Func.create("-", ((Func) ((Func) input).input).input);
                if (name.equals("cos")&&((Func) input).input instanceof Func&& ((Func) ((Func) input).input).name.equals("arccos")) return ((Func) ((Func) input).input).input;
            }
            if (name.equals("ln") && ((Func) input).name.equals("exp")) return ((Func) input).input;
            if (name.equals("exp") && ((Func) input).name.equals("ln")) return ((Func) input).input;
            if (name.equals("sin") && ((Func) input).name.equals("arcsin")) return ((Func) input).input;
            if (name.equals("cos") && ((Func) input).name.equals("arccos")) return ((Func) input).input;
            if (name.equals("tan") && ((Func) input).name.equals("arctan")) return ((Func) input).input;
        }
        if (input instanceof Operator) {
            if (name.equals("-")&&((Operator) input).name.equals("-")) return Operator.create(((Operator) input).input2, "-", ((Operator) input).input1);
            //exp & ln stuff
            if (name.equals("exp")&&((Operator) input).name.equals("*")) {
                if (((Operator) input).input1 instanceof Func&&((Func) ((Operator) input).input1).name.equals("ln")) return Operator.create(((Func) ((Operator) input).input1).input, "^", ((Operator) input).input2);
                if (((Operator) input).input2 instanceof Func&&((Func) ((Operator) input).input2).name.equals("ln")) return Operator.create(((Func) ((Operator) input).input2).input, "^", ((Operator) input).input1);
            }
        }
        //trig identities and such
        return new Func(name, input);
    }
    private Func(String name, Differentiable input) {
        this.name = name;
        this.input = input;
    }

    @Override
    public Differentiable derivative() {
        if (isConstant()) {
            return Constant.create("0");
        }
        return Operator.create(derivatives.get(this.name).apply(this.input), "*", this.input.derivative());
        }

    @Override
    public boolean isConstant() {
        return input.isConstant();
    }

    @Override
    public String toString() {
        return name+'('+input+')';
    }

    @Override
    public Double eval(Double x) {
        return switch (name) {
            case "sin" -> Math.sin(input.eval(x));
            case "cos" -> Math.cos(input.eval(x));
            case "tan" -> Math.tan(input.eval(x));
            case "arcsin" -> Math.asin(input.eval(x));
            case "arccos" -> Math.acos(input.eval(x));
            case "arctan" -> Math.atan(input.eval(x));
            case "ln" -> Math.log(input.eval(x));
            case "exp" -> Math.exp(input.eval(x));
            case "-" -> -input.eval(x);
            default -> throw new IllegalStateException("Unexpected value: " + name);
        };
    }

    @Override
    public boolean equals(Differentiable input) {
        if (input.getClass()==Func.class&&this.name.equals(((Func) input).name)) return this.input.equals(((Func) input).input);
        return false;
    }
}

