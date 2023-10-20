package shop.exceptions;

public class ProductNotFoundInCartException extends Exception {

    public ProductNotFoundInCartException() {
        super();
    }

    public ProductNotFoundInCartException(String message) {
        super(message);
    }

}

