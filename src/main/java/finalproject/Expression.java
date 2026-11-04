package finalproject;

public interface Expression {
    // TODO: better thought out domains.
    double[] getDomain();
    boolean isConstant();
    Expression calculateDerivative();
    double evaluate(double x);
}
