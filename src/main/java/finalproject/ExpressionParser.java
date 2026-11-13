package finalproject;

import java.util.ArrayList;
import java.util.List;

/**
 * Recursive-descent parser: normal infix input such as "3x^2 + sin(2x)/x - ln(x)".
 * Supports + - * / ^, unary minus, implicit multiplication (2x, 3sin(x), 2(x+1)),
 * constants pi, e, g, gamma, the variable x, and the functions in TrigFunction
 * (sin, cos, ..., plus arc-versions), ln, log(x) [base 10], log(base, value), sqrt, exp.
 */
public final class ExpressionParser {
    private final String s;
    private int pos = 0;

    private ExpressionParser(String s) { this.s = s; }

    public static Expression parse(String text) {
        ExpressionParser p = new ExpressionParser(text);
        Expression e = p.expr();
        p.skipWs();
        if (p.pos < p.s.length()) throw p.error("Unexpected '" + p.s.charAt(p.pos) + "'");
        return e;
    }

    private IllegalArgumentException error(String msg) {
        return new IllegalArgumentException(msg + " (position " + pos + ")");
    }

    private void skipWs() {
        while (pos < s.length() && Character.isWhitespace(s.charAt(pos))) pos++;
    }

    private boolean eat(char c) {
        skipWs();
        if (pos < s.length() && s.charAt(pos) == c) { pos++; return true; }
        return false;
    }

    private boolean startsPrimary() {
        skipWs();
        if (pos >= s.length()) return false;
        char c = s.charAt(pos);
        return c == '(' || c == '.' || Character.isLetterOrDigit(c);
    }

    // expr := term (('+' | '-') term)*     (a - b is stored as a + (-1)*b)
    private Expression expr() {
        Expression left = term();
        while (true) {
            if (eat('+')) left = Ops.add(left, term());
            else if (eat('-')) left = Ops.add(left, Ops.neg(term()));
            else return left;
        }
    }

    // term := unary (('*' | '/' | implicit) unary)*
    private Expression term() {
        Expression left = unary();
        while (true) {
            if (eat('*')) left = Ops.mul(left, unary());
            else if (eat('/')) left = new Arithmetic(left, unary(), Operation.DIVIDE);
            else if (startsPrimary()) left = Ops.mul(left, power());
            else return left;
        }
    }

    // unary := '-' unary | '+' unary | power      (so -x^2 = -(x^2))
    private Expression unary() {
        if (eat('-')) return Ops.neg(unary());
        if (eat('+')) return unary();
        return power();
    }

    // power := primary ('^' unary)?     (right associative)
    private Expression power() {
        Expression base = primary();
        if (eat('^')) return new Exponential(base, unary());
        return base;
    }

    private static boolean isSymbol(String n) {
        return n.equals("x") || n.equals("pi") || n.equals("e") || n.equals("g") || n.equals("gamma");
    }

    private static boolean isFunction(String n) {
        if (n.equals("ln") || n.equals("log") || n.equals("sqrt") || n.equals("exp")) return true;
        String t = n.startsWith("arc") ? n.substring(3) : n;
        try {
            TrigFunction.valueOf(t.toUpperCase());
            return true;
        } catch (IllegalArgumentException ex) {
            return false;
        }
    }

    private Expression primary() {
        skipWs();
        if (pos >= s.length()) throw error("Unexpected end of input");
        char c = s.charAt(pos);

        if (c == '(') {
            pos++;
            Expression e = expr();
            if (!eat(')')) throw error("Missing ')'");
            return e;
        }
        if (Character.isDigit(c) || c == '.') {
            int st = pos;
            while (pos < s.length() && (Character.isDigit(s.charAt(pos)) || s.charAt(pos) == '.')) pos++;
            try {
                return new Constant(Double.parseDouble(s.substring(st, pos)));
            } catch (NumberFormatException ex) {
                throw error("Bad number '" + s.substring(st, pos) + "'");
            }
        }
        if (Character.isLetter(c)) {
            int st = pos;
            while (pos < s.length() && Character.isLetter(s.charAt(pos))) pos++;
            String name = s.substring(st, pos).toLowerCase();

            // "sinx", "xsin(x)", "pix": split a glued word into its longest known prefix
            if (!isSymbol(name) && !isFunction(name)) {
                String prefix = null;
                for (int i = name.length() - 1; i >= 1 && prefix == null; i--) {
                    String cand = name.substring(0, i);
                    if (isFunction(cand) || isSymbol(cand)) prefix = cand;
                }
                if (prefix == null) throw error("Unknown symbol '" + name + "'");
                pos = st + prefix.length();
                name = prefix;
            }

            if (isFunction(name)) {
                List<Expression> args = new ArrayList<>();
                if (pos < s.length() && s.charAt(pos) == '(') {
                    pos++;
                    do { args.add(expr()); } while (eat(','));
                    if (!eat(')')) throw error("Missing ')' after " + name);
                } else {
                    args.add(unary()); // "sin x", "sin x^2"
                }
                return function(name, args);
            }
            return switch (name) {
                case "x" -> new Variable("x");
                case "pi" -> new Constant(Math.PI);
                case "e" -> new Constant(Math.E);
                case "g" -> new Constant(9.80665);
                default -> new Constant(0.5772156649015329); // gamma
            };
        }
        throw error("Unexpected '" + c + "'");
    }

    private Expression function(String name, List<Expression> args) {
        Expression e = Ops.c(Math.E);

        if (name.equals("log")) {
            if (args.size() == 1) return new Logarithm(Ops.c(10), args.get(0));
            if (args.size() == 2) {
                Expression base = args.get(0), val = args.get(1);
                if (base.isConstant()) return new Logarithm(base, val);
                // variable base: ln(val) / ln(base)
                return new Arithmetic(new Logarithm(e, val), new Logarithm(e, base), Operation.DIVIDE);
            }
            throw error("log takes 1 or 2 arguments");
        }
        if (args.size() != 1) throw error(name + " takes exactly 1 argument");
        Expression a = args.get(0);

        switch (name) {
            case "ln":   return new Logarithm(e, a);
            case "sqrt": return new Exponential(a, Ops.c(0.5));
            case "exp":  return new Exponential(e, a);
            default: break;
        }
        try {
            return new Trig(a, TrigFunction.valueOf(name.toUpperCase()));
        } catch (IllegalArgumentException ignored) { }
        if (name.startsWith("arc")) {
            try {
                return new InvTrig(a, TrigFunction.valueOf(name.substring(3).toUpperCase()));
            } catch (IllegalArgumentException ignored) { }
        }
        throw error("Unknown function '" + name + "'");
    }
}
