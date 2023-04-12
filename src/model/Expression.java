package model;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Expression implements Calculable {
    private List<Expressible> exp;
    private Num total;

    public Expression() {
        exp = new ArrayList<>();
        total.setNumber(BigDecimal.ZERO); // Total = 0
    }

    public void add(Expressible exp) throws Exception {
        //Preconditions
        if (exp instanceof Parenthesis){
            throw new Exception("No se admiten paréntesis en expresiones simples");
        }else if((exp instanceof Num) && expressibleExpected() == true){
            this.exp.add(exp);
        }else if((exp instanceof Operation) && expressibleExpected() == false){
            this.exp.add(exp);
        }else{
            throw new Exception("El tipo de dato expresable no es el esperado");
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
        if(numbers == totalOperandsNeeded){
            return false; //Expected Operation
        }else{
            return true; //Expected Num
        }
    }

    private Num calculateMajor(Operation operation, LinkedList<Num> numbers) throws Exception {
        if (operation == null || numbers.isEmpty()){
            throw new Exception("Para calcular la operación mayor, se necesita una operación y sus operandos");
        }
        numbers.stream().forEach(number -> operation.pushOperand(number));
        return operation.getResult();
    }

    private Num calculatePendingOp(Stack<Operation> sOperations, Stack<Num> sNumbers) throws Exception {
        if(sOperations.isEmpty() || sNumbers.isEmpty()){
            throw new Exception("Para calcular las operaciones pendientes, las pilas de operaciones y números no pueden estar vacías");
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
        Stream<Expressible> expressionStream = exp.stream().
                filter(expressible -> (expressible instanceof Operation));
        //Expressions list should have at least one operation.
        long numberOfOperations = exp.stream().count();
        if (numberOfOperations < 1){
            throw  new Exception("Para que la expresión sea calculable, hace falta como mínimo una operación");
        }
        //Expressions list should have whole the operands that the operations need.
        int operandsRequired = 0;
        for (Expressible operation : expressionStream.collect(Collectors.toList())){
            operandsRequired += ((Operation) operation).getOperandsRequired();
        }
        long totalOperands = exp.stream().
                filter(expressible -> (expressible instanceof Num)).
                count();
        if(operandsRequired != totalOperands){
            throw new Exception("Se necesitan todos los operandos requeridos para cada operación");
        }
    }
    @Override
    public String toString() {
        final String expression = new String();
        this.exp.forEach(expressible -> expression.concat(expressible.toString()));
        return expression;
    }

    @Override
    public Num calculate() throws Exception {
        try{
            constraints(); //Check if expression is ready to be calculated or it's incomplete.
        }catch (Exception e){
            throw new Exception("Expresión incompleta, no se puede calcular. ERROR: "+e.getMessage());
        }

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

        opA = operations.poll();
        if (operations.size() == 1){
            return calculateMajor(opA, numbers);
        }else{
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
        return new Num();
    }
}
