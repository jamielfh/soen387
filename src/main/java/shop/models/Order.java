package shop.models;

import java.util.List;

public class Order {
    private int id;
    private String shippingAddress;
    private String trackingNumber;
    private User user;
    private List<OrderProduct> orderProducts;

    public Order() {
    }

    public Order(String shippingAddress, User user, List<OrderProduct> orderProducts) {
        this.shippingAddress = shippingAddress;
        this.user = user;
        this.orderProducts = orderProducts;
    }

    public Order(int id, String shippingAddress, String trackingNumber, User user, List<OrderProduct> orderProducts) {
        this.id = id;
        this.shippingAddress = shippingAddress;
        this.trackingNumber = trackingNumber;
        this.user = user;
        this.orderProducts = orderProducts;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public String getTrackingNumber() {
        return trackingNumber;
    }

    public void setTrackingNumber(String trackingNumber) {
        this.trackingNumber = trackingNumber;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<OrderProduct> getOrderProducts() {
        return orderProducts;
    }

    public void setOrderProducts(List<OrderProduct> orderProducts) {
        this.orderProducts = orderProducts;
    }
}
