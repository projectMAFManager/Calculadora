package model.operations;

import model.Num;
import model.Operation;
import model.Symbol;

import java.math.BigDecimal;
import java.util.Iterator;

public class Percentage extends Operation {
    public Percentage() {
        super();
        this.symbol = Symbol.PERCENTAGE;
        this.operandsRequired = 1;
    }

    @Override
    public Num getResult() throws Exception {
        if(this.operands.size() != this.operandsRequired){
            throw new Exception("Número de operadores inválido");
        }

        Iterator<Num> iterator = this.operands.iterator();
        Num operandA = iterator.next();
        operandA.setNumber(operandA.getNumber().divide(BigDecimal.valueOf(100)));
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
}