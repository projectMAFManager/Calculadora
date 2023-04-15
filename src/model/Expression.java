package model;

import model.exceptions.CalculateAnyOperationException;
import model.exceptions.SyntaxErrorException;
import model.exceptions.SyntaxErrorMessage;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Stream;

public class Expression {
    private final List<Expressible> exp;
    private Num total;

    public Expression(){
        exp = new ArrayList<>();
        total = new Num();
        try {
            total.setNumber(BigDecimal.ZERO); // Total = 0
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void add(Expressible exp) throws Exception{
        //Preconditions
        if(exp == null){
            throw new Exception("[Expression.add ERROR] No se puede añadir un expresable nulo");
        }else if (exp instanceof Parenthesis){
            throw new Exception("[Expression.add ERROR] No se admiten paréntesis en expresiones simples");
        }else if((exp instanceof Num) && expressibleExpected()){
            this.exp.add(exp);
        }else if((exp instanceof Operation) && !expressibleExpected()){
            this.exp.add(exp);
        }else{
            throw new SyntaxErrorException(SyntaxErrorMessage.UNEXPECTED_EXPRESSION.getMessage());
        }
    }

    //Returns true if expected Num and false if expected Operation
    private boolean expressibleExpected(){
        if(exp.isEmpty()){
            return true;
        }
        int operations = 0;
        int totalOperandsNeeded = 0;
        int numbers = 0;
        for(Expressible e : exp){
            if(e instanceof Num){
                numbers++;
            }else if(e instanceof Operation){
                operations++;
                totalOperandsNeeded += ((Operation) e).getOperandsRequired();
            }
        }
        if(operations > 2){
            totalOperandsNeeded --;
        }
        return numbers != totalOperandsNeeded;
    }

    private Num calculateMajor(Operation operation, LinkedList<Num> numbers) throws Exception {
        if (operation == null || numbers.isEmpty()){
            throw new Exception("[Expression.calculateMajor ERROR] Para calcular la operación mayor, se necesita una operación y sus operandos");
        }
        numbers.forEach(number -> {
            try {
                operation.pushOperand(number);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        return operation.getResult();
    }

    private Num calculatePendingOp(Stack<Operation> sOperations, Stack<Num> sNumbers) throws Exception {
        if(sOperations.isEmpty() || sNumbers.isEmpty()){
            throw new Exception("[Expression.calculatePendingOp ERROR] Para calcular las operaciones pendientes, las pilas de operaciones y números no pueden estar vacías");
        }
        Operation opA;
        while (!sOperations.isEmpty()){
            opA = sOperations.pop();
            opA.pushOperand(sNumbers.pop());
            opA.pushOperand(this.total);
            total = opA.getResult();
        }
        return total;
    }

    private void constraints() throws Exception {
        //Expressions list should have at least one operation.
        long numberOfOperations = exp.stream().
                filter(expressible -> (expressible instanceof Operation)).
                count();
        long totalOperands = exp.stream().
                filter(expressible -> (expressible instanceof Num)).
                count();
        if (numberOfOperations < 1){
            if(totalOperands == 1){
                throw new CalculateAnyOperationException();
            }
            throw new Exception("[Expression.constraint ERROR] Para que la expresión sea calculable, hace falta como mínimo una operación y sus operandos");
        }
        //Expressions list should have whole the operands that the operations need.
        int operandsRequired = 0;
        Stream<Expressible> expressionStream = exp.stream().
                filter(expressible -> (expressible instanceof Operation));
        for (Expressible operation : expressionStream.toList())
            operandsRequired += ((Operation) operation).getOperandsRequired();

        if(operandsRequired != totalOperands){
            throw new Exception("[Expression.constraint ERROR] Se necesitan todos los operandos requeridos para cada operación");
        }
    }
    @Override
    public String toString() {
        return this.exp.toString();
    }

    public Num calculate() throws Exception {
        constraints();
        LinkedList<Operation> operations = new LinkedList<>();
        LinkedList<Num> numbers = new LinkedList<>();
        for (Expressible e : exp){
            if (e instanceof Operation){
                operations.add((Operation) e);
            }else{
                numbers.add((Num) e);
            }
        }
        Stack<Operation> pendingOperations = new Stack<>();
        Stack<Num> pendingNumbers = new Stack<>();
        Operation opA, opB;
        Num num = new Num();
        num.setNumber(BigDecimal.ZERO);

        if (operations.size() == 1){
            opA = operations.poll();
            return calculateMajor(opA, numbers);
        }else if(operations.size() > 1){
            opA = operations.poll();
            while ((operations.size() + 1) >= 2){
                opB = operations.poll();
                if(opA.compare(opB) > -1){ // First greater than or equal to the second
                    if(pendingOperations.isEmpty()){
                        num = calculateMajor(opA, numbers);
                    }else{
                        int index = operations.indexOf(opA);
                        for(int i = index; i <= (index + opA.getOperandsRequired()); i++){
                            opA.pushOperand(numbers.get(i));
                        }
                        total = opA.getResult();
                        num = calculatePendingOp(pendingOperations, pendingNumbers);
                    }
                    numbers.addFirst(num);
                }else{
                   pendingOperations.push(opA);
                   pendingNumbers.push(numbers.poll());
                }
                opA = opB;
            }
        }
        return num;
    }
}
