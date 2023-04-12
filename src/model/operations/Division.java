package model.operations;

import model.Num;
import model.Operation;
import model.Symbol;

import java.math.BigDecimal;
import java.util.Iterator;

public class Division extends Operation {
    public Division() {
        super();
        this.symbol = Symbol.DIVISION;
        this.operandsRequired = 2;
    }
    @Override
    public Num getResult() throws Exception {
        //Preconditions
        if(this.operands.size() != this.operandsRequired){
            throw new Exception("Número de operadores inválido");
        }

        Iterator<Num> iterator = this.operands.iterator();
        Num operandA = iterator.next();
        Num operandB = iterator.next();
        BigDecimal calc = operandA.getNumber();
        calc.divide(operandB.getNumber());
        Num result = new Num();
        result.setNumber(calc);
        return result;
    }

    @Override
    public int compare(Operation o){
        if (o instanceof Sum || o instanceof Subtract){
            return 1;
        }else if(o instanceof Division){
            return 0;
        }else{
            return -1;
        }
    }
}
