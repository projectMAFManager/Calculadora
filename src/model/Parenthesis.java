package model;

public class Parenthesis implements Expressible{
    private final boolean isOpener;
    private final Symbol symbol;

    public Parenthesis(boolean isOpener) {
        this.isOpener = isOpener;
        if(isOpener){
            symbol = Symbol.PARENTHESES_OPEN;
        }else{
            symbol = Symbol.PARENTHESES_CLOSE;
        }
    }

    public boolean isOpener() {
        return isOpener;
    }

    @Override
    public String toString(){
        return symbol.getSymbolConsole();
    }
}
