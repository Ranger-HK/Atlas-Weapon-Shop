package model;

public class OrderDetails {
    private String itemId;
    private String orderId;
    private int orderQty;

    public OrderDetails() {
    }

    public OrderDetails(String itemId, String orderId, int orderQty) {
        this.setItemId(itemId);
        this.setOrderId(orderId);
        this.setOrderQty(orderQty);
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public int getOrderQty() {
        return orderQty;
    }

    public void setOrderQty(int orderQty) {
        this.orderQty = orderQty;
    }

    @Override
    public String toString() {
        return "OrderDetails{" +
                "itemId='" + itemId + '\'' +
                ", orderId='" + orderId + '\'' +
                ", orderQty=" + orderQty +
                '}';
    }
}
