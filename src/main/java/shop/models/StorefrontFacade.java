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
    private ProductDAO productDAO;
    private CartDAO cartDAO;
    private OrderDAO orderDAO;
    private UserDAO userDAO;

    public StorefrontFacade() {
        productDAO = new ProductDAO();
        cartDAO = new CartDAO();
        orderDAO = new OrderDAO();
        userDAO = new UserDAO();
    }

    public StorefrontFacade(ProductDAO productDAO, CartDAO cartDAO, OrderDAO orderDAO, UserDAO userDAO) {
        this.productDAO = productDAO;
        this.cartDAO = cartDAO;
        this.orderDAO = orderDAO;
        this.userDAO = userDAO;
    }

    public User getUserFromId(int id) throws UserDoesNotExistException {
        User user = userDAO.getUserFromId(id);
        if (user == null) {
            throw new UserDoesNotExistException();
        }
        return user;
    }

    public User getUserFromPasscode(String passcode) throws UserDoesNotExistException {
        User user = userDAO.getUserFromPasscode(passcode);
        if (user == null) {
            throw new UserDoesNotExistException();
        }
        return user;
    }

    // Sign up with automatically generated ID
    public int setPasscode(String passcode) throws PasscodeExistsException, PasscodeInvalidException {
        if (userDAO.passcodeExists(passcode)) {
            throw new PasscodeExistsException();
        }

        if (!isValidPasscode(passcode)) {
            throw new PasscodeInvalidException();
        }

        // The user staff status defaults to false on signup. It can be changed through other avenues afterward
        return userDAO.createUser(false, passcode);
    }

    public void changePasscode(User user, String passcode) throws PasscodeExistsException, PasscodeInvalidException {
        if (userDAO.passcodeExists(passcode)) {
            throw new PasscodeExistsException();
        }

        if (!isValidPasscode(passcode)) {
            throw new PasscodeInvalidException();
        }

        userDAO.changePasscode(user, passcode);
    }

    public void changePermission(User user, String role) {
        userDAO.changePermission(user, role.equals("staff"));
    }

    private boolean isValidPasscode(String input) {
        // Check if the passcode is not null and at least 4 characters
        // Use a regular expression to check if the passcode contains only alphanumeric characters
        return input != null && input.length() >= 4 && input.matches("^[a-zA-Z0-9]*$");
    }

    public List<User> getAllCustomers() {
        return userDAO.getAllCustomers();
    }

    public List<User> getAllStaff() {
        return userDAO.getAllStaff();
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

    public int createAnonymousOrder(List<CartProduct> cartProducts, String shippingAddress) {
        List<OrderProduct> orderProducts = cartProducts.stream()
                .map(x -> new OrderProduct(x.getProduct(), x.getQuantity()))
                .collect(Collectors.toList());

        Order newOrder = new Order(shippingAddress, null, orderProducts);
        return orderDAO.createOrder(newOrder);
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

    public void setOrderOwner(int orderId, int userId) throws OrderAlreadyClaimedException, OrderDoesNotExistException, UserDoesNotExistException {
        Order order = orderDAO.getOrder(orderId);
        User newUser = userDAO.getUserFromId(userId);

        if (newUser == null) {
            throw new UserDoesNotExistException();
        }

        if (order == null) {
            throw new OrderDoesNotExistException();
        }

        User previousUser = order.getUser();

        if (previousUser != null && previousUser.getPasscode() != null) {
            throw new OrderAlreadyClaimedException();
        }

        orderDAO.setOrderOwner(orderId, userId);
    }

    public void shipOrder(int id, String trackingNumber) {
        orderDAO.shipOrder(id, trackingNumber);
    }

    // Merge the given user's cart to the given other cart
    public void mergeCart(User user, List<CartProduct> otherCart) {

        for (CartProduct product : otherCart) {
            String sku = product.getProduct().getSku();
            int newQuantity = product.getQuantity();
            Integer oldQuantity = checkProductInCart(user, sku);

            if (oldQuantity == null) {
                cartDAO.addProductToCart(user, sku);
                if (newQuantity > 1) {
                    cartDAO.setProductQuantityInCart(user, sku, newQuantity);
                }
            } else if (oldQuantity < newQuantity) {
                cartDAO.setProductQuantityInCart(user, sku, newQuantity);
            }
        }
    }

}


