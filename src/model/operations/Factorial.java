package model.operations;

import model.*;
import model.exceptions.SyntaxErrorException;
import model.exceptions.SyntaxErrorMessage;

import java.math.BigDecimal;
import java.util.Iterator;

public class Factorial extends Operation {

    public Factorial() {
        super();
        this.symbol = Symbol.FACTORIAL;
        this.operandsRequired = 1;
    }

    @Override
    public Num getResult() throws Exception {
        if(this.operands.size() != this.operandsRequired){
            throw new SyntaxErrorException(SyntaxErrorMessage.WRONG_NUM_OF_OPERANDS.getMessage());
        }
        Iterator<Num> iterator = this.operands.iterator();
        Num operandA = iterator.next();
        if(operandA.getNumber().scale() > 0) {
            throw new ArithmeticException("No se puede calcular el factorial de un número decimal");
        }else if(!(operandA.getNumber().compareTo(BigDecimal.ZERO) > 0)){ // operandA > 0
            throw new ArithmeticException("No se puede calcular el factorial de un número < 1");
        }

        operandA.setNumber(BigDecimal.valueOf(calculateFactorial(operandA.getNumber().intValue())));
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

    private int calculateFactorial(int number){
        if (number == 1){
            return 1;
        } else {
            return calculateFactorial(number - 1);
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
