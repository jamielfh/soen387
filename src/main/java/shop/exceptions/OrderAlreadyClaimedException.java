package shop.exceptions;

public class OrderAlreadyClaimedException extends Exception{

    public OrderAlreadyClaimedException() {
        super("This order has already been claimed by a user.");
    }
}
