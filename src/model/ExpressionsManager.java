package model;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Stack;

public class ExpressionsManager {
    private Stack<ComposedExpression> parenthesis;
    private Expression exp;
    private LinkedList<Expressible> registerOfExpressions;

    public ExpressionsManager() {
        clear();
    }

    public void add(Expressible exp) {
        registerOfExpressions.addFirst(exp);
    }

    private void openParenthesis(){
        exp = new Expression();
        ComposedExpression base = new ComposedExpression(exp);
        parenthesis.push(base);
    }

    private void closeParenthesis() throws Exception {
        Num result = exp.calculate();
        ComposedExpression parenthesisClosed = parenthesis.pop();
        exp = parenthesisClosed.getExpression();
        exp.add(parenthesisClosed.calculate());
    }

    public Num getTotal() throws Exception {
        registerOfExpressions.add(new Parenthesis(false)); //Close main parenthesis
        int openerParenthesis = 0 ;
        int closerParenthesis = 0;
        for (Expressible e : registerOfExpressions.stream().filter(expressible -> expressible instanceof Parenthesis).toList()){
            if(((Parenthesis) e).isOpener){
                openerParenthesis++;
            }else{
                closerParenthesis++;
            }
        }
        if (openerParenthesis != closerParenthesis){
            throw new Exception("[Syntax Error] Message: "+"Los paréntesis no están correctamente cerrados");
        }

        Expressible expressible;
        Iterator<Expressible> iterator = this.registerOfExpressions.iterator();
        while(!registerOfExpressions.isEmpty()){
            expressible = iterator.next();
            if(expressible instanceof Parenthesis){
                if (((Parenthesis) expressible).isOpener){
                    openParenthesis();
                }else{
                    closeParenthesis();
                }
            }else{
                try{
                    this.exp.add(expressible);
                }catch(Exception e){
                    throw new Exception("[Syntax Error] Message: "+e.getMessage());
                }
            }
        }
        return exp.calculate();
    }

    public void clear(){
        parenthesis = new Stack<>();
        exp = new Expression();
        ComposedExpression mainParenthesis = new ComposedExpression(this.exp);
        parenthesis.push(mainParenthesis);
        registerOfExpressions = new LinkedList<>();
        registerOfExpressions.add(new Parenthesis(true));
    }

    public LinkedList<Expressible> getCopyRegister() {
        return registerOfExpressions;
    }
}
