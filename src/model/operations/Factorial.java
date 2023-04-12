package model.operations;

import model.Num;
import model.Operation;
import model.Symbol;

import java.math.BigDecimal;
import java.math.BigInteger;
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
            throw new Exception("Número de operadores inválido");
        }
        Iterator<Num> iterator = this.operands.iterator();
        Num operandA = iterator.next();
        if(operandA.getNumber().scale() > 0) {
            throw new Exception("No se puede calcular el factorial de un número decimal");
        }else if(operandA.getNumber().compareTo(BigDecimal.ZERO) == 1){ // operandA > 0
            throw new Exception("No se puede calcular el factorial de un número < 1");
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
}
