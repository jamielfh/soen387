package shop.models;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import shop.dao.CartDAO;
import shop.dao.OrderDAO;
import shop.dao.ProductDAO;
import shop.dao.UserDAO;
import shop.exceptions.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class StorefrontFacade {
    private ProductDAO productDAO = new ProductDAO();
    private CartDAO cartDAO = new CartDAO();
    private OrderDAO orderDAO = new OrderDAO();

    public StorefrontFacade() {}

    public void createUser(User user, String password) {
        if (UserDAO.passwordExists(password)) {
            return; // Error: password is not unique
        }

        if (UserDAO.idExists(user.getId())) {
            return; // Error: id is not unique
        }

        UserDAO.add(user, password);
    }

    // Create user with auto-generated id, return the new id
    public int createUser(Boolean is_staff, String password) {
        if (UserDAO.passwordExists(password)) {
            return -1; // Error: password is not unique
        }

        return UserDAO.add(is_staff, password);
    }

    public void createProduct(String sku, String name, String description, String vendor, String slug, BigDecimal price) throws ProductSkuExistsException, ProductSlugInvalidException, ProductSlugExistsException {
        // validate if sku is unique
        if (productDAO.getBySku(sku) != null) {
            throw new ProductSkuExistsException();
        }

        // validate if slug is unique
        if (productDAO.getBySlug(slug) != null) {
            throw new ProductSlugExistsException();
        }

        // validate if slug is valid
        if (!isValidSlug(slug)) {
            throw new ProductSlugInvalidException();
        }

        Product newProduct = new Product(sku, name, description, vendor, slug, price);
        productDAO.add(newProduct);
    }

    public void updateProduct(String oldSku, String sku, String name, String description, String vendor, String slug, BigDecimal price) throws ProductNotFoundException, ProductSlugInvalidException, ProductSlugExistsException, ProductSkuExistsException {

        // if no product found, throw ProductNotFoundException
        Product oldProduct = productDAO.getBySku(oldSku);
        if (oldProduct == null) {
            throw new ProductNotFoundException();
        }

        // validate if sku is unique
        if (!oldSku.equals(sku) && productDAO.getBySku(sku) != null) {
            throw new ProductSkuExistsException();
        }

        // validate if slug is unique
        if (!oldProduct.getSlug().equals(slug) && productDAO.getBySlug(slug) != null) {
            throw new ProductSlugExistsException();
        }

        // validate if slug is valid
        if (!isValidSlug(slug)) {
            throw new ProductSlugInvalidException();
        }

        productDAO.update(oldSku, sku, name, description, vendor, slug, price);
    }

    private boolean isValidSlug(String input) {
        // Check if the slug is not null and its length is within the limit
        // Use a regular expression to check if the slug contains only lowercase letters, numbers, or dashes
        return input != null && input.length() <= 100 && input.matches("^[a-z0-9-]*$") && !input.equals("clear-cart");
    }

    public Product getProduct(String sku) throws ProductNotFoundException {
        Product product = productDAO.getBySku(sku);

        // if no product found, throw ProductNotFoundException
        if (product == null) {
            throw new ProductNotFoundException();
        }

        return product;
    }

    public Product getProductBySlug(String slug) throws ProductNotFoundException {
        Product product = productDAO.getBySlug(slug);
        if (product != null) {
            return product;
        }
        throw new ProductNotFoundException();
    }

    /*public void newUser(String user) {
        this.carts.put(user, new Cart());
    }*/

    public List<CartProduct> getCart(User user) {
        Cart cart = cartDAO.getCart(user);
        if (cart != null) {
            return cart.getCartProducts();
        }
        return new ArrayList<>();
    }

    public Integer checkProductInCart(User user, String sku) {
        return cartDAO.checkProductInCart(user, sku);
    }

    public void addProductToCart(User user, String sku) {
        Integer qty = checkProductInCart(user, sku);
        if (qty != null) {
            setProductQuantityInCart(user, sku, qty + 1);
        } else {
            cartDAO.addProductToCart(user, sku);
        }
    }

    public void removeProductFromCart(User user, String sku) {
        cartDAO.removeProductFromCart(user, sku);
    }

    public void setProductQuantityInCart(User user, String sku, int quantity) {
        if (quantity > 0 && checkProductInCart(user, sku) == null) {
            addProductToCart(user, sku);
        } else if (quantity <= 0) {
            removeProductFromCart(user, sku);
            return;
        }

        cartDAO.setProductQuantityInCart(user, sku, quantity);
    }

    public void clearCart(User user) {
        cartDAO.clearCart(user);
    }

    public String downloadProductCatalog() {
        // Convert product data to JSON in pretty-print style
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(getAllProducts());
    }

    public List<Product> getAllProducts() {
        return productDAO.getAll();
    }

    public void createOrder(User user, String shippingAddress) {
        List<CartProduct> cartProducts = getCart(user);
        List<OrderProduct> orderProducts = cartProducts.stream()
                .map(x -> new OrderProduct(x.getProduct(), x.getQuantity()))
                .collect(Collectors.toList());

        Order newOrder = new Order(shippingAddress, user, orderProducts);
        orderDAO.createOrder(newOrder);
        clearCart(user);
    }

    public List<Order> getAllOrders() {
        return orderDAO.getAllOrders();
    }

    public List<Order> getOrders(User user) {
        return orderDAO.getOrders(user);
    }

    public Order getOrder(User user, int id) throws UserDoesNotMatchOrderException {
        Order order = orderDAO.getOrder(id);
        if (user != null && user.getId() != order.getUser().getId()) {
            throw new UserDoesNotMatchOrderException();
        }
        return order;
    }

    public void shipOrder(int id, String trackingNumber) {
        orderDAO.shipOrder(id, trackingNumber);
    }

}


