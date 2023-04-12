package model;

import java.util.LinkedList;

public class ComposedExpression implements Calculable{
    private LinkedList<ComposedExpression> childs;
    private Expression exp;

    public ComposedExpression(Expression exp) {
        this.exp = exp;
        childs = new LinkedList<>();
    }

    public void addChild(ComposedExpression child) throws Exception {
        if (childs.contains(child)){
            throw new Exception("No puedes añadir un paréntesis que ya existe");
        }
        childs.push(child);
    }

    @Override
    public Num calculate() throws Exception{
        if(childs.isEmpty()){
            return this.exp.calculate();
        }
        while (!childs.isEmpty()){
            this.exp.add(childs.removeFirst().calculate());
        }
        return exp.calculate();
    }

    public Expression getExpression() {
        return exp;
    }
}
