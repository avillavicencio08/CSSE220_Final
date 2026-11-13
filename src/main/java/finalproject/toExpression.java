package finalproject;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;

public class toExpression {
    ParserMain m;
    public toExpression(ParserMain m){
        this.m = m;
    }

    public Expression express(ArrayList<Object> list, HashMap<String, Expression> one, HashMap<String, Expression> two, HashMap<String, Expression> three){

        if (list.size()==2){
            if ((two.get(list.get(0))) instanceof Trig || (two.get(list.get(0))) instanceof InvTrig) {
                return ((Expression) two.get(list.get(0))).createChild(new ArrayList<Object>(List.of(express((ArrayList<Object>)(list.get(1)),one,two,three), TrigFunction.valueOf((String) list.get(0)))));
            }

            return two.get(list.get(0)).createChild(new ArrayList<Object>(List.of(Math.E,express((ArrayList<Object>)(list.get(1)),one,two,three))));

        }
        //Above are One input functions. An array list size 2 has one func and one input
        //mostly trig, log is the only special case



        if (list.size()==3){
            if ((three.get(list.get(1))) instanceof Exponential) {
                return three.get(list.get(1)).createChild(new ArrayList<Object>(List.of(express((ArrayList<Object>) (list.get(0)),one,two,three), express((ArrayList<Object>) (list.get(2)),one,two,three))));
            }
            return ((Expression) three.get(list.get(1))).createChild(new ArrayList<Object>(List.of(express((ArrayList<Object>) list.get(0),one,two,three), express((ArrayList<Object>) list.get(2),one,two,three), toEnum((String) list.get(1)))));
        }

        //Above are two input functions. An array list size 3 has one func and two input
        //exponents are a special case because idk syntax on T's end



        if (list.size()==1){
            if (one.get(list.get(0)) instanceof Variable){
                return ((Expression) one.get(list.get(0))).createChild(list);
            }
            if (one.containsKey(String.valueOf(list.get(0)))){
                return ((Expression) one.get(String.valueOf(list.get(0)))).createChild(new ArrayList<Object>(List.of(getConstant(String.valueOf(list.get(0))))));
            }
            return new Constant(getConstant(String.valueOf(list.get(0))));
        }
        return null;


    }

    public Operation toEnum(String OP){
        if (OP.equals("+")){
            return Operation.ADD;
        }
        if (OP.equals("/")){
            return Operation.DIVIDE;
        }
        if (OP.equals("*")){
            return Operation.MULTIPLY;
        }
        throw new IllegalArgumentException("minusing isnt real thats what the gov wants you to think");
    }

    public double getConstant(String num){
        if (m.isDouble(num)){
            return Double.parseDouble(num);
        }
        else if (num.equals("E")){
            return Math.E;
        }
        else if (num.equals("PI")){
            return Math.PI;
        }
        else if (num.equals("g")){
            return 9.80665;
        }
        //else if (num.equals("GAMMA")){
            return 0.5772156649015329;
        //}

    }

    public static void printNested(ArrayList<?> list) {
        System.out.print("[");

        for (int i = 0; i < list.size(); i++) {
            Object item = list.get(i);

            if (item instanceof String) {
                System.out.print(item);
            } else if (item instanceof ArrayList<?>) {
                printNested((ArrayList<?>) item);
            }

            if (i < list.size() - 1) {
                System.out.print(", ");
            }
        }
        System.out.print("]");
    }

}