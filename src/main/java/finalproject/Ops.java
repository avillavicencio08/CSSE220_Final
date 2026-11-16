package finalproject;

/** Tiny helpers for building expression trees. */
final class Ops {
    private Ops() {}
    static Expression c(double v) { return new Constant(v); }
    static Expression add(Expression a, Expression b) { return new Arithmetic(a, b, Operation.ADD); }
    static Expression mul(Expression a, Expression b) { return new Arithmetic(a, b, Operation.MULTIPLY); }
    static Expression neg(Expression a) { return mul(c(-1), a); }
    static Expression pow(Expression a, double n) { return new Exponential(a, c(n)); }
}
