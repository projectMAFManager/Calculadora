package model.exceptions;

public class SyntaxErrorException extends Exception{
    public SyntaxErrorException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
