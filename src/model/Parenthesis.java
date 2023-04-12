package model;

public class Parenthesis implements Expressible{
    public boolean isOpener;

    public Parenthesis(boolean isOpener) {
        this.isOpener = isOpener;
    }

    public boolean isOpener() {
        return isOpener;
    }
}
