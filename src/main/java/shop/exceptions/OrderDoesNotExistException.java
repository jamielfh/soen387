package shop.exceptions;

public class OrderDoesNotExistException extends Exception{

    public OrderDoesNotExistException() {
        super("This order does not exist.");
    }
}
