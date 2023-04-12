package model.operations;

import model.Num;
import model.Operation;
import model.Symbol;

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
            throw new Exception("Número de operadores inválido");
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
}
