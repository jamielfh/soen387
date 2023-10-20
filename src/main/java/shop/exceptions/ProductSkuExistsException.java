package shop.exceptions;

public class ProductSkuExistsException extends Exception {

    public ProductSkuExistsException() {
        super();
    }

    public ProductSkuExistsException(String message) {
        super(message);
    }

}

