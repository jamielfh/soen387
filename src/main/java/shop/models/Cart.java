
package shop.models;

import java.util.ArrayList;
import java.util.List;

public class Cart {
    private User user;
    private List<CartProduct> cartProducts;

    public Cart(User user) {
        this.user = user;
        this.cartProducts = new ArrayList<CartProduct>();
    }

    public Cart(User user, List<CartProduct> cartProducts) {
        this.user = user;
        this.cartProducts = cartProducts;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<CartProduct> getCartProducts() {
        return cartProducts;
    }

    public void setCartProducts(List<CartProduct> cartProducts) {
        this.cartProducts = cartProducts;
    }
}
