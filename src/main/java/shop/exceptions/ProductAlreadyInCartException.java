
package shop.exceptions;

public class ProductAlreadyInCartException extends Exception {

    public ProductAlreadyInCartException() {
        super();
    }

    public ProductAlreadyInCartException(String message) {
        super(message);
    }

}


