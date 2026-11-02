package finalproject;

public interface Expression {
    double[] getDomain();
    boolean isConstant();
    Expression calculateDerivative();
    double evaluate(double x);
}
