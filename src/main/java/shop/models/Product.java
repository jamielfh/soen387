
package shop.models;

import java.math.BigDecimal;

public class Product {
    private String sku;
    private String name;
    private String description;
    private String vendor;
    private String slug;
    private String imageURL;
    private BigDecimal price;

    public Product() {
    }

    public Product(String sku, String name, String description, String vendor, String slug, BigDecimal price) {
        this.sku = sku;
        this.name = name;
        this.description = description;
        this.vendor = vendor;
        this.slug = slug;
        this.price = price;

        // Default image (no-image image)
        this.imageURL = "https://demofree.sirv.com/nope-not-here.jpg";
    }

    public Product(String sku, String name, String description, String vendor, String slug, BigDecimal price, String imageURL) {
        this.sku = sku;
        this.name = name;
        this.description = description;
        this.vendor = vendor;
        this.slug = slug;
        this.price = price;
        this.imageURL = imageURL;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

}

