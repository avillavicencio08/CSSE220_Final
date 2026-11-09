package finalproject;

import java.util.ArrayList;

public interface Expression {
    // TODO: better thought out domains.
    double[] getDomain();
    boolean isConstant();
    Expression calculateDerivative();
    double evaluate(double x);
    Expression createChild(ArrayList<Object> args);
}
