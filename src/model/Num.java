package model;

import java.math.BigDecimal;

public class Num implements Expressible {
    private BigDecimal number;

    public Num() {
        number = null;
    }

    public java.math.BigDecimal getNumber() {
        return number;
    }

    public void setNumber(BigDecimal number) {
        this.number = number;
    }

    @Override
    public String toString(){
        return this.number.toString();
    }
}
