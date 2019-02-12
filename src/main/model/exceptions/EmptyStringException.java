package main.model.exceptions;

public class EmptyStringException extends IllegalArgumentException {
        public EmptyStringException() {

        }

        public EmptyStringException(String string) {
            super(string);
        }
}
