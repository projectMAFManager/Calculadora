package model.tests;

import model.Num;
import model.operations.Sum;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class SumTest {

    Sum suma = new Sum();
    Num number = new Num();
    @org.junit.jupiter.api.Test
    void pushOperand() {
        try{
            Num number = new Num();
            number.setNumber(BigDecimal.ONE);
            Num number2 = new Num();
            number2.setNumber(BigDecimal.TWO);
            suma.pushOperand(number);
            suma.pushOperand(number2);
            assertEquals(2, suma.getOperandsRequired());
        }catch (Exception e){
            e.printStackTrace();
            fail("Test pushOperand failed");
        }

    }

    @org.junit.jupiter.api.Test
    void removeOperand() {
    }

    @org.junit.jupiter.api.Test
    void getOperandsRequired() {
    }

    @org.junit.jupiter.api.Test
    void getResult() {
    }

    @org.junit.jupiter.api.Test
    void compare() {
    }
}