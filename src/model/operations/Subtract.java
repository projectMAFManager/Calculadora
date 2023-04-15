package model.operations;

import model.*;
import model.exceptions.SyntaxErrorException;
import model.exceptions.SyntaxErrorMessage;

import java.math.BigDecimal;
import java.util.Iterator;

public class Subtract extends Operation {
    public Subtract() {
        super();
        this.symbol = Symbol.SUBTRACT;
        this.operandsRequired = 2;
    }

    @Override
    public Num getResult() throws Exception {
        //Preconditions
        if(this.operands.size() != this.operandsRequired){
            throw new SyntaxErrorException(SyntaxErrorMessage.WRONG_NUM_OF_OPERANDS.getMessage());
        }

        Iterator<Num> iterator = this.operands.iterator();
        Num operandA = iterator.next();
        Num operandB = iterator.next();
        BigDecimal calc = operandA.getNumber();
        calc = calc.subtract(operandB.getNumber());
        Num result = new Num();
        result.setNumber(calc);
        return result;
    }

    @Override
    public int compare(Operation o){
        if (o instanceof Sum || o instanceof Subtract){
            return 0;
        }else{
            return -1;
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
