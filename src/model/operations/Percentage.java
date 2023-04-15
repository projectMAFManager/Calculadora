package model.operations;

import model.*;
import model.exceptions.SyntaxErrorException;
import model.exceptions.SyntaxErrorMessage;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Iterator;

public class Percentage extends Operation {
    public Percentage() {
        super();
        this.symbol = Symbol.PERCENTAGE;
        this.operandsRequired = 1;
    }

    @Override
    public Num getResult() throws Exception{
        if(this.operands.size() != this.operandsRequired){
            throw new SyntaxErrorException(SyntaxErrorMessage.WRONG_NUM_OF_OPERANDS.getMessage());
        }

        Iterator<Num> iterator = this.operands.iterator();
        Num operandA = iterator.next();
        operandA.setNumber(operandA.getNumber().divide(BigDecimal.valueOf(100), RoundingMode.HALF_DOWN));
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
        return result;
    }
}
