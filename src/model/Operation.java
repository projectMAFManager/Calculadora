package model;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

public abstract class Operation implements Expressible {
    protected Symbol symbol;
    protected List<Num> operands;
    protected int operandsRequired;

    public Operation() {
        operands = new LinkedList<>();
    }

    public void pushOperand(Num operand){
        operands.add(operand);
    }

    public boolean removeOperand(Num operand){
        return operands.remove(operand);
    }

    public abstract Num getResult() throws Exception;

    public Symbol getSymbol() {
        return symbol;
    }

    public int getOperandsRequired() {
        return operandsRequired;
    }

    public abstract int compare(Operation op);
}
