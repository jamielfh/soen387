package shop.exceptions;

public class PasswordExistsException extends Exception {

    public PasswordExistsException() {
        super("The user password already exists. The password must be unique.");
    }
}
