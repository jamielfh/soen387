package shop.exceptions;

public class CartNotFoundException extends Exception {

    public CartNotFoundException() {
        super();
    }

    public CartNotFoundException(String message) {
        super(message);
    }

}

