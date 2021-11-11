package view.tm;

public class CartTM {
    private String itemId;
    private String categoryId;
    private String modelId;
    private double price;
    private String itemName;
    private int quantity;
    private double total;

    public CartTM() {
    }

    public CartTM(String itemId, String categoryId, String modelId, double price, String itemName, int quantity, double total) {
        this.setItemId(itemId);
        this.setCategoryId(categoryId);
        this.setModelId(modelId);
        this.setPrice(price);
        this.setItemName(itemName);
        this.setQuantity(quantity);
        this.setTotal(total);
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getModelId() {
        return modelId;
    }

    public void setModelId(String modelId) {
        this.modelId = modelId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "OrderTM{" +
                "itemId='" + itemId + '\'' +
                ", categoryId='" + categoryId + '\'' +
                ", modelId='" + modelId + '\'' +
                ", price=" + price +
                ", itemName='" + itemName + '\'' +
                ", quantity=" + quantity +
                ", total=" + total +
                '}';
    }
}
