package shop.exceptions;

public class UserIdDoesNotExistException extends Exception{

    public UserIdDoesNotExistException() {
        super("The user ID does not exist.");
    }
}
