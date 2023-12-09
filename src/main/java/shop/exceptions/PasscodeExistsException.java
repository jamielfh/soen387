package shop.exceptions;

public class PasscodeExistsException extends Exception {

    public PasscodeExistsException() {
        super("The passcode already exists. The passcode must be unique.");
    }
}
