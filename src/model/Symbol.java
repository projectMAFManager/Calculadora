package model;

public enum Symbol {
    SUM("+"),
    SUBTRACT("-"),
    MULTIPLICATION("*"),
    DIVISION("/"),
    ROOT("root"),
    MODULE("mod"),
    PERCENTAGE("%"),
    POW("^"),
    FACTORIAL("!"),
    EQUAL("="),
    PARENTHESES_OPEN("("),
    PARENTHESES_CLOSE(")");

    private final String symbolConsole;

    Symbol(String symbolConsole) {
        this.symbolConsole = symbolConsole;
    }

    public String getSymbolConsole() {
        return symbolConsole;
    }
}
