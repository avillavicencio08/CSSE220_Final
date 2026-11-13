package finalproject;

import java.util.Map;
import java.util.function.BiFunction;

public class Operator implements Differentiable {
    public Differentiable input1;
    public String name;
    public Differentiable input2;

    static Map<String, BiFunction<Differentiable, Differentiable, Differentiable>> derivatives = Map.ofEntries(
            Map.entry("+", (x, y) -> Operator.create(x.derivative(), "+", y.derivative())),
            Map.entry("-", (x, y) -> Operator.create(x.derivative(), "-", y.derivative())),
            Map.entry("*", (x, y) -> Operator.create(Operator.create(x.derivative(), "*", y), "+", Operator.create(y.derivative(), "*", x))),
            Map.entry("/", (x, y) -> Operator.create(Operator.create(Operator.create(x.derivative(), "*", y), "-", Operator.create(x, "*", y.derivative())), "/", Operator.create(y, "^2", Constant.create("2")))),
            Map.entry("^", (x, y) -> Operator.create(Operator.create(x, "^", y), "*", Operator.create(Func.create("ln", x), "*", y).derivative())),
            Map.entry("log", (x, y) -> Operator.create(Func.create("ln", y), "/", Func.create("ln", x)).derivative())
    );
    public static Differentiable create(Differentiable input1, String name, Differentiable input2) {
        if (input1 instanceof Constant && input2 instanceof Constant) {
            if (((Constant) input1).isNumber && ((Constant) input2).isNumber) {
                return switch (name) {
                    case "+" -> Constant.create(String.valueOf(Double.parseDouble(((Constant) input1).name)+Double.parseDouble(((Constant) input2).name)));
                    case "-" -> Constant.create(String.valueOf(Double.parseDouble(((Constant) input1).name)-Double.parseDouble(((Constant) input2).name)));
                    case "*" -> Constant.create(String.valueOf(Double.parseDouble(((Constant) input1).name)*Double.parseDouble(((Constant) input2).name)));
                    case "/" -> Constant.create(String.valueOf(Double.parseDouble(((Constant) input1).name)/Double.parseDouble(((Constant) input2).name)));
                    case "^" -> Constant.create(String.valueOf(Math.pow(Double.parseDouble(((Constant) input1).name), Double.parseDouble(((Constant) input2).name))));
                    case "log" -> Constant.create(String.valueOf(Math.log(Double.parseDouble(((Constant) input2).name))/Math.log(Double.parseDouble(((Constant) input1).name))));
                    default -> throw new IllegalStateException("Unexpected value: " + name);
                };
            } else if ((name.equals("+")||name.equals("*"))&&((Constant) input2).isNumber) return Operator.create(input2, name, input1);
        }

        switch (name) {
            case "*":
                System.out.println("*  "+input1+name+input2);
                if (input1 instanceof Constant) {
                    switch (((Constant) input1).name) {
                        case "0" -> {return Constant.create("0");}
                        case "1" -> {return input2;}
                        case "-1" -> {return Func.create("-", input2);}
                    }
                }
                if (input2 instanceof Constant) {
                    switch (((Constant) input2).name) {
                        case "0" -> {return Constant.create("0");}
                        case "1" -> {return input1;}
                        case "-1" -> {return Func.create("-", input1);}
                    }
                }
                //a*a->a^2
                if (input1.equals(input2)) return Operator.create(input1, "^", Constant.create("2"));
                if (input1 instanceof Operator) {
                    if (input2 instanceof Operator) {
                        //(a^n)*(a^m)->a^(n+m)
                        if (((Operator) input1).name.equals("^") && ((Operator) input2).name.equals("^") && ((Operator) input1).input1.equals(((Operator) input2).input1))
                            return Operator.create(((Operator) input1).input1, "^", Operator.create(((Operator) input1).input2, "+", ((Operator) input2).input2));
                        //(a/b)*(c/d)->(a*c)/(b*d)
                        if (((Operator) input1).name.equals("/") && ((Operator) input2).name.equals("/"))
                            return Operator.create(Operator.create(((Operator) input1).input1, "*", ((Operator) input2).input1), "/", Operator.create(((Operator) input1).input2, "*", ((Operator) input2).input2));
                    }
                    //(a*b)*a or (b*a)*a ->b*(a^2)
                    if (((Operator) input1).name.equals("*")) {
                        if (input2.equals(((Operator) input1).input1)) return Operator.create(((Operator) input1).input2, "*", Operator.create(input2, "^", Constant.create("2")));
                        if (input2.equals(((Operator) input1).input2)) return Operator.create(((Operator) input1).input1, "*", Operator.create(input2, "^", Constant.create("2")));
                    }
                    //(a/b)*c->(a*c)/b
                    if (((Operator) input1).name.equals("/")) return Operator.create(Operator.create(((Operator) input1).input1, "*", input2), "/", ((Operator) input1).input2);
                }

                if (input2 instanceof Operator) {
                    //a*(b/c)->(a*b)/c
                    if (((Operator) input2).name.equals("/")) return Operator.create(Operator.create(((Operator) input2).input1, "*", input1), "/", ((Operator) input2).input2);
                    //a*(a*b) or a*(b*a)->b*(a^2)
                    if (input1.equals(((Operator) input2).input1)) return Operator.create(((Operator) input2).input2, "*", Operator.create(input1, "^", Constant.create("2")));
                    if (input1.equals(((Operator) input2).input2)) return Operator.create(((Operator) input2).input1, "*", Operator.create(input1, "^", Constant.create("2")));
                }
                //a*((a^n)*b)->b*(a^(n+1))
                //b*(a*(b^n))->a*(b^(n+1))
                //((a^n)*b)*a->b*(a^(n+1))
                //(a*(b^n))*b->a*(b^(n+1))
                break;
            case "+":
                System.out.println("+  "+input1+name+input2);
                if (input1 instanceof Constant&&((Constant) input1).name.equals("0")) return input2;
                if (input2 instanceof Constant&&((Constant) input2).name.equals("0")) return input1;
                if (input2 instanceof Func &&((Func) input2).name.equals("-")) return Operator.create(input1, "-", ((Func) input2).input);
                if (input1 instanceof Func &&((Func) input1).name.equals("-")) return Operator.create(input2, "-", ((Func) input1).input);
                if (input1.equals(input2)) return Operator.create(Constant.create("2"), "*", input1);
                if (input1 instanceof Operator&&input2 instanceof Operator&&((Operator) input1).name.equals("log")&&((Operator) input2).name.equals("log")&&((Operator) input1).input1.equals(((Operator) input2).input1))
                    return Operator.create(((Operator) input1).input1, "log", Operator.create(((Operator) input1).input2, "*", ((Operator) input2).input2));
                break;
            case "-":
                System.out.println("-  "+input1+name+input2);
                if (input1 instanceof Constant&&((Constant) input1).name.equals("0")) return Func.create("-", input2);
                if (input2 instanceof Constant&&((Constant) input2).name.equals("0")) return input1;
                if (input2 instanceof Func &&((Func) input2).name.equals("-")) return Operator.create(input1, "+", ((Func) input2).input);
                if (input1.equals(input2)) return Constant.create("0");
                if (input1 instanceof Operator&&input2 instanceof Operator&&((Operator) input1).name.equals("log")&&((Operator) input2).name.equals("log")&&((Operator) input1).input1.equals(((Operator) input2).input1))
                    return Operator.create(((Operator) input1).input1, "log", Operator.create(((Operator) input1).input2, "/", ((Operator) input2).input2));
                break;
            case "/":
                System.out.println("/  "+input1+name+input2);
                if (input2 instanceof Constant) {
                    if (((Constant) input2).name.equals("1")) return input1;
                    if (((Constant) input2).name.equals("0")) throw new ArithmeticException("Divide by 0");
                }
                if (input1 instanceof Constant && ((Constant) input1).name.equals("0")) return Constant.create("0");
                if (input1.equals(input2)) return Constant.create("1");
                if (input1 instanceof Operator) {
                    if (input2 instanceof Operator) {
                        //(a^n)/(a^m)->a(n-m)
                        if (((Operator) input1).name.equals("^") && ((Operator) input2).name.equals("^") && ((Operator) input1).input1.equals(((Operator) input2).input1))
                            return Operator.create(((Operator) input1).input1, "^", Operator.create(((Operator) input1).input2, "-", ((Operator) input2).input2));
                        //(a*b)/(a*c)->b/c
                        if (((Operator) input1).name.equals("*") && ((Operator) input2).name.equals("*")) {
                            if (((Operator) input1).input1.equals(((Operator) input2).input1))
                                return Operator.create(((Operator) input1).input2, "/", ((Operator) input2).input2);
                            if (((Operator) input1).input1.equals(((Operator) input2).input2))
                                return Operator.create(((Operator) input1).input2, "/", ((Operator) input2).input1);
                            if (((Operator) input1).input2.equals(((Operator) input2).input1))
                                return Operator.create(((Operator) input1).input1, "/", ((Operator) input2).input2);
                            if (((Operator) input1).input2.equals(((Operator) input2).input2))
                                return Operator.create(((Operator) input1).input1, "/", ((Operator) input2).input1);
                        }
                    }
                    switch (((Operator) input1).name) {

                        case "*":
                            //(a*b)/a or (b*a)/a->b
                            if (((Operator) input1).input1.equals(input2)) return ((Operator) input1).input2;
                            if (((Operator) input1).input2.equals(input2)) return ((Operator) input1).input1;
                        case "/":
                            //(a/b)/c->a/(b*c)
                            return Operator.create(((Operator) input1).input1, "/", Operator.create(((Operator) input1).input2, "*", input2));
                        case "^":
                            //(a^n)/a->a^(n-1)
                            if (((Operator) input1).input1.equals(input2))
                                return Operator.create(input2, "^", Operator.create(((Operator) input1).input2, "-", Constant.create("1")));
                    }
                }
                if (input2 instanceof Operator) {
                    switch (((Operator) input2).name) {
                        case "/":
                            //a/(b/c)->(a*c)/b
                            return Operator.create(Operator.create(input1, "*", ((Operator) input2).input2), "/", ((Operator) input2).input1);
                        case "*":
                            //a/(a*b) or a/(b*a)->1/b
                            if (input1.equals(((Operator) input2).input1)) return Operator.create(Constant.create("1"), "/", ((Operator) input2).input2);
                            if (input1.equals(((Operator) input2).input2)) return Operator.create(Constant.create("1"), "/", ((Operator) input2).input1);
                        case "^":
                            //a/(a^n)->a^(1-n)
                            if (input1.equals(((Operator) input2).input1)) return Operator.create(input1, "^", Operator.create(Constant.create("1"), "-", ((Operator) input2).input2));
                            //b/(a^(-(n))->b*(a^(n))
                            if (((Operator) input2).input2 instanceof Func &&((Func)(((Operator) input2).input2)).name.equals("-")) return Operator.create(input1, "*", Operator.create(((Operator) input2).input1, "^", ((Func)((Operator) input2).input2).input));
                    }
                }
                break;
            case "^":
                System.out.println("^  "+input1+name+input2);
                if (input2 instanceof Constant) {
                    if (((Constant) input2).name.equals("1")) return input1;
                    if (((Constant) input2).name.equals("0")&&!(input1 instanceof Constant&&((Constant) input1).name.equals("0"))) return Constant.create("1");
                }
                if (input1 instanceof Constant) {
                    if (((Constant) input1).name.equals("0")&&!(input2 instanceof Constant&&((Constant) input2).name.equals("0"))) return Constant.create("0");
                    if (((Constant) input1).name.equals("1")) return Constant.create("1");
                    if (((Constant) input1).name.equals("e")) return Func.create("exp", input2);
                }
                if (input1 instanceof Operator) {
                    //(a*/b)^c->(a^c)*/(b^c)
                    if (((Operator) input1).name.equals("*")||((Operator) input1).name.equals("/")) return Operator.create(Operator.create(((Operator) input1).input1, "^", input2), ((Operator) input1).name, Operator.create(((Operator) input1).input2, "^", input2));
                    //(a^b)^c->a^(b*c)
                    if (((Operator) input1).name.equals("^")) return Operator.create(((Operator) input1).input1, "^", Operator.create(((Operator) input1).input2, "*", input2));
                }
                if (input2 instanceof Operator) {
                    //a^(alogb)->b
                    if (((Operator) input2).name.equals("log")&&input1.equals(((Operator) input2).input1)) return ((Operator) input2).input2;
                    //a^(b*(alogc)) or a^((alogc)*b)->c^b
                    if (((Operator) input2).name.equals("*")) {
                        if (((Operator) input2).input1 instanceof Operator&&((Operator) ((Operator) input2).input1).name.equals("log")&&((Operator) ((Operator) input2).input1).input1.equals(input1)) return Operator.create(((Operator) ((Operator) input2).input1).input2, "^", ((Operator) input2).input2);
                        if (((Operator) input2).input2 instanceof Operator&&((Operator) ((Operator) input2).input2).name.equals("log")&&((Operator) ((Operator) input2).input2).input1.equals(input1)) return Operator.create(((Operator) ((Operator) input2).input2).input2, "^", ((Operator) input2).input1);
                    }
                }
                break;
            case "log":
                System.out.println("log  "+input1+name+input2);
                if (input1 instanceof Constant) {
                    if (((Constant) input1).name.equals("1")) throw new ArithmeticException("log with base 1 undefined");
                    if (((Constant) input1).name.equals("0")) throw new ArithmeticException("log with base 0 undefined");
                }
                if (input2 instanceof Constant) {
                    if (((Constant) input2).name.equals("0")) throw new ArithmeticException("log(0) undefined");
                    if (((Constant) input2).name.equals("1")) return Constant.create("0");
                }
                //alog(a^b)->b
                if (input2 instanceof Operator&&((Operator) input2).name.equals("^")&&((Operator) input2).input1.equals(input1)) return ((Operator) input2).input2;
                break;
        }

        return new Operator(input1, name, input2);
    }
    private Operator(Differentiable input1, String name, Differentiable input2) {
        //distributive property UNLIKELY
        //Combine like terms UNLIKELY
        this.input1 = input1;
        this.name = name;
        this.input2 = input2;
        System.out.println("make "+this);
    }
    @Override
    public Differentiable derivative() {
        System.out.println("derive "+this);
        if (isConstant()) {
            System.out.println("is 0");
            return Constant.create("0");
        }
        System.out.println("is "+derivatives.get(name).apply(input1, input2));
        return derivatives.get(name).apply(input1, input2);
    }
    @Override
    public boolean isConstant() {
        return input1.isConstant() && input2.isConstant();
    }
    @Override
    public boolean equals(Differentiable input) {
        if (input.getClass()==Operator.class&&this.name.equals(((Operator) input).name)) {
            if (this.input1.equals(((Operator) input).input1)&&this.input2.equals(((Operator) input).input2)) return true;
            return (this.name.equals("+") || this.name.equals("*")) && this.input1.equals(((Operator) input).input2) && this.input2.equals(((Operator) input).input1);
        }
        return false;
    }

    @Override
    public String toString() {
        return "("+input1+')'+name+'('+input2+')';
    }

    @Override
    public Double eval(Double x) {
        return switch (name) {
            case "+" -> input1.eval(x)+input2.eval(x);
            case "-" -> input1.eval(x)-input2.eval(x);
            case "*" -> input1.eval(x)*input2.eval(x);
            case "/" -> input1.eval(x)/input2.eval(x);
            case "^" -> Math.pow(input1.eval(x), input2.eval(x));
            case "log" -> Math.log(input2.eval(x))/Math.log(input1.eval(x));
            default -> throw new IllegalStateException("Unexpected value: " + name);
        };
    }
}
