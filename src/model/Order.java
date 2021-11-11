package model;

import java.util.ArrayList;

public class Order {
    private String orderId;
    private String customerId;
    private String orderDate;
    private ArrayList<OrderDetails>items;

    public Order() {
    }

    public Order(String orderId, String customerId, String orderDate, ArrayList<OrderDetails> items) {
        this.setOrderId(orderId);
        this.setCustomerId(customerId);
        this.setOrderDate(orderDate);
        this.setItems(items);
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public ArrayList<OrderDetails> getItems() {
        return items;
    }

    public void setItems(ArrayList<OrderDetails> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId='" + orderId + '\'' +
                ", customerId='" + customerId + '\'' +
                ", orderDate='" + orderDate + '\'' +
                ", items=" + items +
                '}';
    }
}
