package shop.exceptions;

public class PasscodeInvalidException extends Exception {

    public PasscodeInvalidException() {
        super("The passcode must be alphanumeric and contain at least 4 characters.");
    }
}
