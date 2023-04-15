package model;

import model.exceptions.SyntaxErrorException;
import model.exceptions.SyntaxErrorMessage;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Stack;

public class ExpressionsManager {
    private Stack<Expression> parenthesis;
    private Expression exp;
    private LinkedList<Expressible> registerOfExpressions;

    public ExpressionsManager() {
        clear();
    }

    public void add(Expressible exp) {
        registerOfExpressions.addFirst(exp);
    }

    private void openParenthesis(){
        this.parenthesis.push(exp);
        exp = new Expression();
    }

    private void closeParenthesis() throws Exception {
        if(parenthesis.isEmpty()){
            throw new SyntaxErrorException(SyntaxErrorMessage.CANNOT_CLOSE_NOT_OPENED_PARENTHESIS.getMessage());
        }
        Num result = new Num();
        result.setNumber(exp.calculate().getNumber());
        exp = parenthesis.pop();
        exp.add(result);
    }

    public Num getTotal() throws Exception {
        long openerParenthesis = registerOfExpressions.stream().
                filter(expressible -> expressible instanceof Parenthesis).
                filter(paren -> ((Parenthesis) paren).isOpener()).count();
        long closerParenthesis = registerOfExpressions.stream().
                filter(expressible -> expressible instanceof Parenthesis).
                filter(paren -> !((Parenthesis) paren).isOpener()).count();

        if (openerParenthesis != closerParenthesis){
            throw new SyntaxErrorException(SyntaxErrorMessage.WRONG_PARENTHESIS.getMessage());
        }

        Expressible expressible;
        Iterator<Expressible> iterator = registerOfExpressions.iterator();
        while(!registerOfExpressions.isEmpty()){
            expressible = iterator.next();
            if(expressible instanceof Parenthesis){
                if (((Parenthesis) expressible).isOpener()){
                    openParenthesis();
                }else{
                    closeParenthesis();
                }
            }else{
                this.exp.add(expressible);
            }
        }
        return exp.calculate();
    }

    public void clear(){
        parenthesis = new Stack<>();
        exp = new Expression();
        registerOfExpressions = new LinkedList<>();
        registerOfExpressions.add(new Parenthesis(true));
    }

    public LinkedList<Expressible> getCopyRegister() {
        return registerOfExpressions;
    }

    @Override
    public String toString() {
        if(registerOfExpressions.isEmpty()){
            return "0";
        }
        String result = "";
        for(Expressible e : registerOfExpressions){
            result = result.concat(e.toString());
        }
        return result;
    }
}
