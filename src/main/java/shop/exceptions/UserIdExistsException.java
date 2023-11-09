package shop.exceptions;

public class UserIdExistsException extends Exception{

    public UserIdExistsException() {
        super("The user ID already exists. It must be unique.");
    }
}
