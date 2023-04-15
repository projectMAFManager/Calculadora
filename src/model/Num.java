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

    public void setNumber(BigDecimal number) throws Exception {
        if(number == null){
            throw new Exception("[Num.setNumber ERROR] No se puede asignar un número null");
        }
        this.number = number;
    }

    @Override
    public String toString(){
        return number.toString();
    }
}
