package model;

import java.util.LinkedList;
import java.util.List;

public abstract class Operation implements Expressible {
    protected Symbol symbol;
    protected List<Num> operands;
    protected int operandsRequired;

    public Operation() {
        operands = new LinkedList<>();
    }

    public void pushOperand(Num operand) throws Exception {
        if(operand == null){
            throw new Exception("[Operation.pushOperand ERROR] No se puede añadir un operando vacío");
        }else if(operand.getNumber() == null){
            throw new Exception("[Operation.pushOperand ERROR] No se puede añadir un operando vacío");
        }
        operands.add(operand);
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
