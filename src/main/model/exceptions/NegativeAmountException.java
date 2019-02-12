package main.model.exceptions;

public class NegativeAmountException extends IllegalArgumentException {

    public NegativeAmountException() {
    }
    public NegativeAmountException(String string) {
        super(string);
    }
}
