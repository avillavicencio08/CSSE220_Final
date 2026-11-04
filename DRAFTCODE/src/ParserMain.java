import java.beans.Expression;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class ParserMain {
    HashMap<String, Expression> oneInputFuncs;

    HashMap<String, Expression> twoInputFuncs;

    HashMap<String, Expression> vars;


    public ParserMain(){
        this.oneInputFuncs = new HashMap<>();

        this.twoInputFuncs = new HashMap<>();
        oneInputFuncs.put("sin", Trig);
        oneInputFuncs.put("cos", Trig);
         oneInputFuncs.put("tan", Trig);
         oneInputFuncs.put("csc", Trig);
         oneInputFuncs.put("sec", Trig);
         oneInputFuncs.put("cot", Trig);
         oneInputFuncs.put("sinh", Trig);
         oneInputFuncs.put("cosh", Trig);
         oneInputFuncs.put("tanh", Trig);
         oneInputFuncs.put("csch", Trig);
         oneInputFuncs.put("sech", Trig);
         oneInputFuncs.put("coth", Trig);
         oneInputFuncs.put("arcsinh", InvTrig);
         oneInputFuncs.put("arccosh", InvTrig);
         oneInputFuncs.put("arctanh", InvTrig);
         oneInputFuncs.put("arccsch", InvTrig);
         oneInputFuncs.put("arcsech", InvTrig);
         oneInputFuncs.put("arccoth", InvTrig);
         oneInputFuncs.put("arcsin", InvTrig);
         oneInputFuncs.put("arccos", InvTrig);
         oneInputFuncs.put("arctan", InvTrig);
         oneInputFuncs.put("arccsc", InvTrig);
         oneInputFuncs.put("arcsec", InvTrig);
         oneInputFuncs.put("arccot", InvTrig);
         oneInputFuncs.put("ln", Logarithm);




        twoInputFuncs.put("*", Arithmetic);
        twoInputFuncs.put("^", Exponential);
        twoInputFuncs.put("+", Arithmetic);
         twoInputFuncs.put("-", Arithmetic);
         twoInputFuncs.put("/", Arithmetic);
         twoInputFuncs.put("log", Logarithm);




        this.vars = new HashMap<>();
        vars.put("pi", Constant);
        vars.put("e", Constant);
        vars.put("g", Constant);
        vars.put("Y",Constant);
        vars.put("x", Variable);

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



    public static boolean isLong(String s) {
        try {
            Long.parseLong(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }









private ArrayList<Object> funcParse(String func){
        ArrayList<Object> parts = new ArrayList<>();
    String current="";
        boolean searching = true;
        int pDepth = 0;
        int charN =-1;
        int charSave;
        while (searching) {
            charN += 1;
            if (charN >= func.length() - 1) {
                searching = false;
            }
            System.out.println(func);

            if (func.charAt(charN) == '(') {
                pDepth += 1;
            } else if (func.charAt(charN) == ')') {
                if (isLong(current)){
                        searching = false;
                        parts.add(current);
                }

                pDepth -= 1;
            } else if (pDepth == 0) {
                current = current + func.charAt(charN);









                if (twoInputFuncs.containsKey(current)) {
                    searching = false;
                    if (current.equals("log")) {
                        charSave = charN + 2;
                        while (func.charAt(charN) != ',') {
                            charN += 1;
                        }



                        parts.add(funcParse("(ln(" + func.substring(charSave, charN) + "))/(ln(" + func.substring(charN + 1, func.length() - 1) + "))"));
                        //ln/ln
                    } else {
                        parts.add(funcParse(func.substring(1, charN - current.length())));
                        //(goes from the second character, after 1st open paren, to right before the close parenthesis before current symbol
                        parts.add(current);
                        parts.add(funcParse(func.substring(charN + 2, func.length() - 1)));
                        //(goes from the second character after current, after 1st open paren, to right before the close parenthesis at the end , second to last char

                    }
                }
                if (oneInputFuncs.containsKey(current)) {
                    searching = false;
                    parts.add(current);
                    System.out.println(func.substring(charN + 2, func.length() - 1));
                    parts.add(funcParse(func.substring(charN + 2, func.length() - 1)));
                    //(goes from the second character after current, after 1st open paren, to right before the close parenthesis at the end , second to last char

                }
                if (vars.containsKey(current)) {
                    searching = false;
                    parts.add(current);

                }
            }
        }

        return parts;
    }









    public static void main(String[] args){
        ParserMain m =new ParserMain();

        ArrayList<Object> equation = new ArrayList<>();
        String function;
        Scanner scanner = new Scanner(System.in);
        function = scanner.nextLine();

        equation = m.funcParse(function);
        printNested(equation);

    }
}




