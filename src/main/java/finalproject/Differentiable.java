package finalproject;

public interface Differentiable {
    Differentiable derivative();
    boolean isConstant();
    String toString();
    Double eval(Double x);
    boolean equals(Differentiable input);
}
