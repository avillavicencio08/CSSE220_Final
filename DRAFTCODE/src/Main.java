import java.util.ArrayList;
import java.util.HashMap;
public static void main() {

    HashMap<String, Function> oneInputFuncs = new HashMap<String, Function>();
    oneInputFuncs.put("sin", Trig);
    oneInputFuncs.put("cos", Trig);
    oneInputFuncs.put("tan", Trig);
    oneInputFuncs.put("csc", Trig);
    oneInputFuncs.put("sec", Trig);
    oneInputFuncs.put("cot", Trig);
    oneInputFuncs.put("sinh", Trigh);
    oneInputFuncs.put("cosh", Trigh);
    oneInputFuncs.put("tanh", Trigh);
    oneInputFuncs.put("csch", Trigh);
    oneInputFuncs.put("sech", Trigh);
    oneInputFuncs.put("coth", Trigh);
    oneInputFuncs.put("arcsinh", InvTrigh);
    oneInputFuncs.put("arccosh", InvTrigh);
    oneInputFuncs.put("arctanh", InvTrigh);
    oneInputFuncs.put("arccsch", InvTrigh);
    oneInputFuncs.put("arcsech", InvTrigh);
    oneInputFuncs.put("arccoth", InvTrigh);
    oneInputFuncs.put("arcsin", InvTrig);
    oneInputFuncs.put("arccos", InvTrig);
    oneInputFuncs.put("arctan", InvTrig);
    oneInputFuncs.put("arccsc", InvTrig);
    oneInputFuncs.put("arcsec", InvTrig);
    oneInputFuncs.put("arccot", InvTrig);
    oneInputFuncs.put("ln", Logs);
    HashMap<String, Function> twoInputFuncs = new HashMap<String, Function>();
    twoInputFuncs.put("*", Operator);
    twoInputFuncs.put("^", Operator);
    twoInputFuncs.put("+", Operator);
    twoInputFuncs.put("-", Operator);
    twoInputFuncs.put("/", Operator);
    twoInputFuncs.put("log", Logs);
    HashMap<String, Function> vars = new HashMap<String, Function>();
    vars.put("pi", Constant);
    vars.put("e", Constant);
    vars.put("y", Constant);
    vars.put("x", Variable);







public ArrayList(String) funcParse(String function, boolean commaExpected){
        ArrayList<String> part = new ArrayList<>();
    String current;
        boolean searching = true;
        int pDepth = 0;
        int charN =0;
        while (searching) {
            if (function.charAt(charN) == '(') {
                pDepth += 1;
                charN+=1;
            } else if (function.charAt(charN) == ')') {
                pDepth += 1;
                charN+=1;
            }
            else if (pDepth==0){
                current=current+function.charAt(charN);




                if (twoInputFuncs.containsKey(current)){
                    if (current=="log"){
                        commaExpected=true;
                        //ln/ln
                    }
                    else{
                        parts.add(funcParse(function.substring(1,charN-current.length()), false));
                        parts.add(current);
                        parts.add(funcParse(function.substring(charN,function.length()-1, false));
                    }

                    if (oneInputFuncs.containsKey(current)){
                        else{
                            parts.add(current);
                            parts.add(funcParse(function.substring(charN,function.length()-1, false));
                        }
                    }



            }





            }
        }

        /**else if (function.charAt(i)=='(' && current !=null){
         equation.add("o"+ current);
         if (current=="log"){
         commaExpected=true;
         }
         current="";
         }
         else if (function.charAt(i)==')'){
         equation.add(current);
         current="";
         }
         else {
         current = current + (function.charAt(i));
         }
         **/
    }
}

public static void main(String[] args){
    ArrayList<String> equation = new ArrayList<>();
    String function;
    Scanner scanner = new Scanner(System.in);
    function = scanner.nextLine();
    funcParse(function);



}


