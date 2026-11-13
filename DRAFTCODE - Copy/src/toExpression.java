import java.util.ArrayList;
import java.util.HashMap;

public class toExpression {
    public toExpression(){

    }

    public Expression express(ArrayList<?> list, HashMap<String, Expression> one, HashMap<String, Expression> two, HashMap<String, Expression> three){
        if (list.size()==2){
            if ((two.get(list.get(0)))==trig || (two.get(list.get(0)))==invTrig) {
                return two.get(list.get(0)).createChild(express(two.get(list.get(1))), TrigFunction.valueOf(list.get(0)));
            }

            return two.get(list.get(0)).createChild(Math.E,express(two.get(list.get(1))));

        }
        if (list.size()==3){
            if ((two.get(list.get(0)))==Exponential) {
                return two.get(list.get(0)).createChild(express(two.get(list.get(0))), express(two.get(list.get(2))));
            }
            return two.get(list.get(0)).createChild(express(two.get(list.get(0))), Operation.valueOf(list.get(1)),express(two.get(list.get(2))));
        }
        if (list.size()==1){
            if (one.get(num)==variable){
                return one.get(list.get(0)).createChild(num);
            }
            return one.get(list.get(0)).createChild(getConstant(list.get(0)));
        }
        return null;


    }

    public double getConstant(String num, HashMap<String, Expression> one){
        if (m.isdouble(num)){
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
        else if (num.equals("GAMMA")){
            return 0.5772156649015329;
        }

    }



}
