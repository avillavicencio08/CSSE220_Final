package finalproject;

public class Evaluate {

    public Evaluate(){
    }

    public double evaluate(Expression f,double x){
        if (f instanceof Trig) {
                System.out.println( ((TrigFunction)f.getFields().get(1)).name());
                if (f.getFields().get(1)==TrigFunction.SIN){
                    return Math.sin(evaluate((Expression) f.getFields().get(0),x));
                }
                if (f.getFields().get(1)==TrigFunction.COS){
                    return Math.cos(evaluate((Expression) f.getFields().get(0),x));

                }
                if (f.getFields().get(1)==TrigFunction.TAN){
                    return Math.tan(evaluate((Expression) f.getFields().get(0),x));
                }
                if (f.getFields().get(1)==TrigFunction.HSIN){
                    return Math.sinh(evaluate((Expression) f.getFields().get(0),x));
                }
                if (f.getFields().get(1)==TrigFunction.HCOS){
                    return Math.cosh(evaluate((Expression) f.getFields().get(0),x));
                }
                if (f.getFields().get(1)==TrigFunction.HTAN){
                    return Math.tanh(evaluate((Expression) f.getFields().get(0),x));
                }
            }
        if (f instanceof Logarithm) {
                return Math.log(evaluate((Expression) f.getFields().get(0), x));
            }
        if (f instanceof Variable) {
            return x;
        }
        if (f instanceof Constant) {
            return (double) f.getFields().get(0);
        }
        if (f instanceof Arithmetic) {
            if (f.getFields().get(2)==Operation.ADD){
                return (((double) evaluate((Expression)f.getFields().get(0),x))+((double) evaluate((Expression)f.getFields().get(1),x)));
            }
            if (f.getFields().get(2)==Operation.DIVIDE){
                return ((double)evaluate((Expression)f.getFields().get(0),x))/((double)evaluate((Expression)f.getFields().get(1),x));
            }
            if (f.getFields().get(2)==Operation.MULTIPLY){
                return ((double)evaluate((Expression) f.getFields().get(0),x))*((double)evaluate((Expression) f.getFields().get(1),x));
            }
        }



        return 100000.1;
    }




}
