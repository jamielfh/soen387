package shop.exceptions;

public class PasswordInvalidException extends Exception {

    public PasswordInvalidException() {
        super("The user password must be alphanumeric and contain at least 4 characters.");
    }
}
