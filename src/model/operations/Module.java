package model.operations;

import model.*;
import model.exceptions.SyntaxErrorException;
import model.exceptions.SyntaxErrorMessage;

import java.util.Iterator;

public class Module extends Operation {
    public Module() {
        super();
        this.symbol = Symbol.MODULE;
        this.operandsRequired = 2;
    }

    @Override
    public Num getResult() throws Exception {
        if(this.operands.size() != this.operandsRequired){
            throw new SyntaxErrorException(SyntaxErrorMessage.WRONG_NUM_OF_OPERANDS.getMessage());
        }

        Iterator<Num> iterator = this.operands.iterator();
        Num operandA = iterator.next();
        Num operandB = iterator.next();
        operandA.setNumber(operandA.getNumber().remainder(operandB.getNumber()));
        return operandA;
    }

    @Override
    public int compare(Operation o){
        if (o instanceof Sum || o instanceof Subtract || o instanceof Multiplication || o instanceof Division){
            return 1;
        }else{
            return 0;
        }
    }

    @Override
    public String toString() {
        if(this.operands.isEmpty()){
            return symbol.getSymbolConsole();
        }
        String result = "";
        result = result.concat(operands.get(0).toString());
        result = result.concat(symbol.getSymbolConsole());
        result = result.concat(operands.get(0).toString());
        return result;
    }
}
