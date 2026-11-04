import java.beans.Expression;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class ParserMain {
    public HashMap<String, Expression> oneInputFuncs;

    public HashMap<String, Expression> twoInputFuncs;

    public HashMap<String, Expression> vars;

    public HashMap<Expression, Enums> enums;


    public ParserMain(){
        this.oneInputFuncs = new HashMap<>();

        this.twoInputFuncs = new HashMap<>();
        oneInputFuncs.put("SIN", trig);
        oneInputFuncs.put("COS", trig);
         oneInputFuncs.put("TAN", trig);
         oneInputFuncs.put("CSC", trig);
         oneInputFuncs.put("SEC", trig);
         oneInputFuncs.put("COT", trig);
         oneInputFuncs.put("SINH", trig);
         oneInputFuncs.put("COSH", trig);
         oneInputFuncs.put("TANH", trig);
         oneInputFuncs.put("CSCH", trig);
         oneInputFuncs.put("SECH", trig);
         oneInputFuncs.put("COTH", trig);
         oneInputFuncs.put("ARCSINH", invTrig);
         oneInputFuncs.put("ARCCOSH", invTrig);
         oneInputFuncs.put("ARCTANH", invTrig);
         oneInputFuncs.put("ARCCSCH", invTrig);
         oneInputFuncs.put("ARCSECH", invTrig);
         oneInputFuncs.put("ARCCOTH", invTrig);
         oneInputFuncs.put("ARCSIN", invTrig);
         oneInputFuncs.put("ARCCOS", invTrig);
         oneInputFuncs.put("ARCTAN", invTrig);
         oneInputFuncs.put("ARCCSC", invTrig);
         oneInputFuncs.put("ARCSEC", invTrig);
         oneInputFuncs.put("ARCCOT", invTrig);
         oneInputFuncs.put("LN", logarithm);




        twoInputFuncs.put("*", arithmetic);
        twoInputFuncs.put("^", exponential);
        twoInputFuncs.put("+", arithmetic);
         twoInputFuncs.put("-", arithmetic);
         twoInputFuncs.put("/", arithmetic);
         twoInputFuncs.put("log", logarithm);




        this.vars = new HashMap<>();
        vars.put("PI", constant);
        vars.put("E", constant);
        vars.put("g", constant);
        vars.put("GAMMA",constant);
        vars.put("x", variable);

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



    public static boolean isDouble(String s) {
        try {
            Double.parseDouble(s);
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
                if (isDouble(current)){
                        searching = false;
                        parts.add(current);
                    vars.put(current,Constant);
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
        Trig trig = new Trig(null,null);
        InvTrig invTrig = new InvTrig(null,null);
        Logarithm logarithm = new Logarithm(null,null);
        Exponential exponential = new Exponential(null,null);
        Arithmetic arithmetic = new Arithmetic(null,null,null);
        Variable variable = new Variable(null);
        Constant constant = new Constant(null);

        ArrayList<Object> equation = new ArrayList<>();
        String function;
        Scanner scanner = new Scanner(System.in);
        function = scanner.nextLine();

        equation = m.funcParse(function);
        printNested(equation);



    }
}




