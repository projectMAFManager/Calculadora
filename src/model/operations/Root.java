package model.operations;

import model.Num;
import model.Operation;
import model.Symbol;

import javax.print.DocFlavor;
import java.math.BigDecimal;
import java.util.Iterator;

public class Root extends Operation {
    public Root() {
        super();
        this.symbol = Symbol.ROOT;
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
        if(operandB.getNumber().scale() > 0){
            throw new Exception("El índice de una raíz no puede ser decimal");
        }

        BigDecimal calc = operandA.getNumber();
        calc.pow((1 / operandB.getNumber().intValue())); //a^(1/b)
        Num result = new Num();
        result.setNumber(calc);
        return result;
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
