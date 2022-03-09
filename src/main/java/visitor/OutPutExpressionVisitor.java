package visitor;

import calculator.MyNumber;
import calculator.Notation;
import calculator.Operation;
import calculator.Expression;

import java.util.ArrayList;

public class OutPutExpressionVisitor extends Visitor{
    private String output = "";
    private final Notation notation;


    public OutPutExpressionVisitor(Notation notation){
        this.notation = notation;
    }
    public String getOutput(){
        return output;
    }
    @Override
    public void visit(MyNumber n) {
        output = n.getValue().toString();
    }

    @Override
    public void visit(Operation o) {
        ArrayList<String> outputtedArgs = new ArrayList<>();
        for(Expression expression : o.args) {
            expression.accept(this);
            outputtedArgs.add(output);
            output = "";
        }
        String temp = "";
        for (int counter = 1; counter < o.args.size(); counter++) {
            temp = outputExpr(o, outputtedArgs, counter);
        }
        output = temp;
    }


    private String outputExpr(Operation o, ArrayList<String> args, int index) {
        switch (notation) {
            case INFIX:
                return "( " + args.get(index - 1) + " " + o.getSymbol() + " " + args.get(index) + " )";
            case PREFIX:
                return o.getSymbol() + " (" + args.get(index - 1) + ", " + args.get(index) + ")";
            case POSTFIX:
                return "(" + args.get(index - 1) + ", " + args.get(index) + ") " + o.getSymbol();
            default: return "This case should never occur.";
        }
    }


}
