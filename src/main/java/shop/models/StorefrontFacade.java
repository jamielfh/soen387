
package shop.models;

import shop.exceptions.*;

import java.util.*;

public class StorefrontFacade {
    private Map<String, Product> products;
    private Map<String, Cart> carts;

    public StorefrontFacade() {
        this.products = new HashMap<String, Product>();
        this.carts = new HashMap<String, Cart>();
    }

    public void createProduct(String sku, String name, String description, String vendor, String slug, double price) throws ProductSkuExistsException, ProductSlugInvalidException, ProductSlugExistsException {
        // validate if sku is unique
        Product product = this.products.get(sku);
        if (product != null) {
            throw new ProductSkuExistsException();
        }

        // validate if slug is unique
        try {
            Product slugProduct = getProductBySlug(slug);
            if (slugProduct != null) {
                throw new ProductSlugExistsException();
            }
        } catch (ProductNotFoundException ignored) {}

        // validate if slug is valid
        boolean isValidSlug = isValidSlug(slug);
        if (!isValidSlug) {
            throw new ProductSlugInvalidException();
        }

        Product newProduct = new Product(sku, name, description, vendor, slug, price);
        this.products.put(sku, newProduct);
    }

    public void updateProduct(String sku, String name, String description, String vendor, String slug, double price) throws ProductNotFoundException, ProductSlugInvalidException, ProductSlugExistsException {
        Product productToUpdate = this.products.get(sku);

        // if no product found, throw ProductNotFoundException
        if (productToUpdate == null) {
            throw new ProductNotFoundException();
        }

        // if product found, update product
        productToUpdate.setName(name);
        productToUpdate.setDescription(description);
        productToUpdate.setVendor(vendor);
        productToUpdate.setPrice(price);

        // validate if slug is unique
        try {
            Product slugProduct = getProductBySlug(slug);
            if (slugProduct != null) {
                throw new ProductSlugExistsException();
            }
        } catch (ProductNotFoundException ignored) {}

        // validate if slug is valid
        boolean isValidSlug = isValidSlug(slug);
        if (!isValidSlug) {
            throw new ProductSlugInvalidException();
        }
        productToUpdate.setSlug(slug);

        this.products.put(sku, productToUpdate);
    }

    private boolean isValidSlug(String input) {
        // Check if the slug is not null and its length is within the limit
        // Use a regular expression to check if the slug contains only lowercase letters, numbers, or dashes
        return input != null && input.length() <= 100 && input.matches("^[a-z0-9-]*$");
    }

    public Product getProduct(String sku) throws ProductNotFoundException {
        Product product = this.products.get(sku);

        // if no product found, throw ProductNotFoundException
        if (product == null) {
            throw new ProductNotFoundException();
        }

        return product;
    }

    public Product getProductBySlug(String slug) throws ProductNotFoundException {
        for (Product product : products.values()) {
            if (product.getSlug().equals(slug)) {
                return product;
            }
        }
        throw new ProductNotFoundException();
    }

    public void newUser(String user) {
        this.carts.put(user, new Cart());
    }

    public List<Product> getCart(String user) throws CartNotFoundException {
        Cart cart = this.carts.get(user);
        // if no cart found, throw CartNotFoundException
        if (cart == null) {
            throw new CartNotFoundException();
        }

        // if cart found, return list of products
        return cart.getItems();
    }

    public void addProductToCart(String user, String sku) throws ProductNotFoundException, ProductAlreadyInCartException, CartNotFoundException {
        Cart userCart = this.carts.get(user);
        // if no cart found, throw CartNotFoundException
        if (userCart == null) {
            throw new CartNotFoundException();
        }

        // if cart found, add product to cart
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
        // Return a string representing the catalog content
        return null;
    }

    public List<Product> getAllProducts() {
        return new ArrayList<>(products.values());
    }

    public void setProducts(List<Product> productList) {
        // Convert the list of products to a map
        for (Product product : productList) {
            this.products.put(product.getSku(), product);
        }
    }
}


