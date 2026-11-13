package finalproject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ToExpression {
    ParserMain m;

    public ToExpression(ParserMain m) {
        this.m = m;
    }

    public Expression express(ArrayList<Object> list, HashMap<String, Expression> one, HashMap<String, Expression> two, HashMap<String, Expression> three) {
        if (list.size() == 2) {
            return !(two.get(list.get(0)) instanceof Trig) && !(two.get(list.get(0)) instanceof InvTrig) ? ((Expression)two.get(list.get(0))).createChild(new ArrayList(List.of(Math.E, this.express((ArrayList)list.get(1), one, two, three)))) : ((Expression)two.get(list.get(0))).createChild(new ArrayList(List.of(this.express((ArrayList)list.get(1), one, two, three), TrigFunction.valueOf((String)list.get(0)))));
        } else if (list.size() == 3) {
            return three.get(list.get(0)) instanceof Exponential ? ((Expression)three.get(list.get(0))).createChild(new ArrayList(List.of(this.express((ArrayList)list.get(0), one, two, three), this.express((ArrayList)list.get(2), one, two, three)))) : ((Expression)three.get(list.get(1))).createChild(new ArrayList(List.of(this.express((ArrayList)list.get(0), one, two, three), this.express((ArrayList)list.get(2), one, two, three), this.toEnum((String)list.get(1)))));
        } else if (list.size() == 1) {
            if (one.get(list.get(0)) instanceof Variable) {
                return ((Expression)one.get(list.get(0))).createChild(list);
            } else {
                return (Expression)(one.containsKey(String.valueOf(list.get(0))) ? ((Expression)one.get(String.valueOf(list.get(0)))).createChild(new ArrayList(List.of(this.getConstant(String.valueOf(list.get(0)))))) : new Constant(this.getConstant(String.valueOf(list.get(0)))));
            }
        } else {
            return null;
        }
    }

    public Operation toEnum(String OP) {
        if (OP.equals("+")) {
            return Operation.ADD;
        } else if (OP.equals("/")) {
            return Operation.DIVIDE;
        } else if (OP.equals("*")) {
            return Operation.MULTIPLY;
        } else {
            throw new IllegalArgumentException("minusing isnt real thats what the gov wants you to think");
        }
    }

    public double getConstant(String num) {
        ParserMain var10000 = this.m;
        if (ParserMain.isDouble(num)) {
            return Double.parseDouble(num);
        } else if (num.equals("E")) {
            return Math.E;
        } else if (num.equals("PI")) {
            return Math.PI;
        } else {
            return num.equals("g") ? 9.80665 : 0.5772156649015329;
        }
    }

    public static void printNested(ArrayList<?> list) {
        System.out.print("[");

        for(int i = 0; i < list.size(); ++i) {
            Object item = list.get(i);
            if (item instanceof String) {
                System.out.print(item);
            } else if (item instanceof ArrayList) {
                printNested((ArrayList)item);
            }

            if (i < list.size() - 1) {
                System.out.print(", ");
            }
        }

        System.out.print("]");
    }
}
