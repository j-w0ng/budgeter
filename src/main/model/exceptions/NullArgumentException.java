package main.model.exceptions;

public class NullArgumentException extends IllegalArgumentException {

    public NullArgumentException() {

    }

    public NullArgumentException(String string) {
        super(string);
    }
}
