package shop.models;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import shop.dao.ProductDAO;
import shop.dao.UserDAO;
import shop.exceptions.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StorefrontFacade {
    private Map<String, Cart> carts;
    private ProductDAO productDAO = new ProductDAO();

    public StorefrontFacade() {
        this.carts = new HashMap<String, Cart>();
    }

    public void createProduct(String sku, String name, String description, String vendor, String slug, double price) throws ProductSkuExistsException, ProductSlugInvalidException, ProductSlugExistsException {
        // validate if sku is unique
        if (productDAO.getBySKU(sku) != null) {
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

    public void updateProduct(String oldSku, String sku, String name, String description, String vendor, String slug, double price) throws ProductNotFoundException, ProductSlugInvalidException, ProductSlugExistsException, ProductSkuExistsException {

        // if no product found, throw ProductNotFoundException
        Product oldProduct = productDAO.getBySKU(oldSku);
        if (oldProduct == null) {
            throw new ProductNotFoundException();
        }

        // validate if sku is unique
        if (!oldSku.equals(sku) && productDAO.getBySKU(sku) != null) {
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
        return input != null && input.length() <= 100 && input.matches("^[a-z0-9-]*$");
    }

    public Product getProduct(String sku) throws ProductNotFoundException {
        Product product = productDAO.getBySKU(sku);

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

    public void newUser(String user) {
        this.carts.put(user, new Cart());
    }

    public List<Product> getCart(String user) {
        Cart cart = this.carts.get(user);
        // if no cart found, create a new cart
        if (cart == null) {
            newUser(user);
            cart = this.carts.get(user);
        }

        // return list of products
        return cart.getItems();
    }

    public void addProductToCart(String user, String sku) throws ProductNotFoundException, ProductAlreadyInCartException {
        Cart userCart = this.carts.get(user);
        // if no cart found, create a new cart
        if (userCart == null) {
            newUser(user);
            userCart = this.carts.get(user);
        }

        // add product to cart
        Product productToAdd = getProduct(sku);
        List<Product> currentItems = userCart.getItems();
        if (currentItems.contains(productToAdd)) {
            throw new ProductAlreadyInCartException();
        } else {
            currentItems.add(productToAdd);
        }

        userCart.setItems(currentItems);
        this.carts.put(user, userCart);
    }

    public void removeProductFromCart(String user, String sku) throws ProductNotFoundException, CartNotFoundException, ProductNotFoundInCartException {
        Cart userCart = this.carts.get(user);
        // if no cart found, throw CartNotFoundException
        if (userCart == null) {
            throw new CartNotFoundException();
        }

        // if cart found, find product in cart
        Product productToRemove = getProduct(sku);
        List<Product> currentItems = userCart.getItems();

        if (currentItems.contains(productToRemove)) {
            currentItems.remove(productToRemove);
        } else {
            throw new ProductNotFoundInCartException();
        }

        userCart.setItems(currentItems);
        this.carts.put(user, userCart);
    }

    public String downloadProductCatalog() {
        // Convert product data to JSON in pretty-print style
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(getAllProducts());
    }

    public List<Product> getAllProducts() {
        return productDAO.getAll();
    }

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

}


