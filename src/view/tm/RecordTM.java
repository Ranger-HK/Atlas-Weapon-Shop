package view.tm;

public class RecordTM {
    private String orderId;
    private String itemId;
    private String customerId;
    private int orderQty;
    private String date;
    private int total;

    public RecordTM() {
    }

    public RecordTM(String orderId, String itemId, String customerId, int orderQty, String date, int total) {
        this.setOrderId(orderId);
        this.setItemId(itemId);
        this.setCustomerId(customerId);
        this.setOrderQty(orderQty);
        this.setDate(date);
        this.setTotal(total);
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public int getOrderQty() {
        return orderQty;
    }

    public void setOrderQty(int orderQty) {
        this.orderQty = orderQty;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "RecordTM{" +
                "orderId='" + orderId + '\'' +
                ", itemId='" + itemId + '\'' +
                ", customerId='" + customerId + '\'' +
                ", orderQty=" + orderQty +
                ", date='" + date + '\'' +
                ", total=" + total +
                '}';
    }
}
