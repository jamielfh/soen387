
package shop.exceptions;

public class UserDoesNotMatchOrderException extends Exception {

    public UserDoesNotMatchOrderException() {
        super();
    }

    public UserDoesNotMatchOrderException(String message) {
        super(message);
    }

}

