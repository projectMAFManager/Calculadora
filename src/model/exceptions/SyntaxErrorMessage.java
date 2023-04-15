package model.exceptions;

public enum SyntaxErrorMessage {
    WRONG_NUM_OF_OPERANDS("Número de operadandos incorrecto para esta operación"),
    WRONG_PARENTHESIS("Los paréntesis no están cerrados correctamente"),
    UNEXPECTED_EXPRESSION("Número u operación inesperados"),
    CANNOT_CLOSE_NOT_OPENED_PARENTHESIS("No puedes cerrar un paréntesis que no está abierto");

    private final String message;

    SyntaxErrorMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
