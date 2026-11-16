package finalproject;
import java.util.ArrayList;
import java.util.Set;

public class Parser {
    public static Set<String> twoInputFuncs = Set.of("^", "*", "+", "-", "/", "log");
    public static Set<String> oneInputFuncs = Set.of("sin", "cos", "tan", "csc", "sec", "cot", "arcsin", "arccos", "arctan", "arccsc", "arcsec", "arccot", "ln");

    public static boolean isDouble(String s) {
        try {
            Double.parseDouble(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    public static ArrayList<Object> funcParse(String func){
        ArrayList<Object> parts = new ArrayList<>();
        if (func == null || func.isEmpty()) return parts;
        func = func.trim();
        while (func.startsWith("(") && func.endsWith(")")) {
            int depth = 0;
            boolean matchesOuter = true;
            for (int i = 0; i < func.length() - 1; i++) {
                if (func.charAt(i) == '(') depth++;
                else if (func.charAt(i) == ')') depth--;
                if (depth == 0) {
                    matchesOuter = false; // Closed prematurely before the end
                    break;
                }
            }
            if (matchesOuter) {
                func = func.substring(1, func.length() - 1).trim();
            } else {
                break;
            }
        }
        int pDepth = 0;
        int charN = 0;
        boolean searching = true;
        while (charN < func.length()) {
            char c = func.charAt(charN);
            if (c == '(') {
                pDepth++;
            } else if (c == ')') {
                pDepth--;
            } else if (pDepth == 0) {
                // Check if any binary operator starts at this position
                boolean foundOp = false;
                for (String op : twoInputFuncs) {
                    if (func.startsWith(op, charN)) {
                        searching = false;
                        String left = func.substring(0, charN);
                        String right = func.substring(charN + op.length());

                        parts.add(funcParse(left));
                        parts.add(op);
                        parts.add(funcParse(right));
                        foundOp = true;
                        break;
                    }
                }
                if (foundOp) {
                    break;
                }
            }
            charN++;
        }
        if (parts.isEmpty()) {
            int openParenIndex = func.indexOf('(');
            if (openParenIndex != -1 && func.endsWith(")")) {
                String funcName = func.substring(0, openParenIndex).trim();
                if (oneInputFuncs.contains(funcName)) {
                    String arg = func.substring(openParenIndex + 1, func.length() - 1);
                    parts.add(funcName);
                    parts.add(funcParse(arg));
                    return parts;
                }
            }
            parts.add(func);
        }
        return parts;
    }
}
