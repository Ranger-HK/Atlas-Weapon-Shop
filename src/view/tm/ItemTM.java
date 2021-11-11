package view.tm;

public class ItemTM {
    private String itemId;
    private String categoryId;
    private String modelId;
    private String supplierId;
    private double price;
    private String itemName;
    private int qtyOnHand;

    public ItemTM(String itemId, String categoryId, String modelId, double price, String itemName, int qtyOnHand) {
        this.itemId = itemId;
        this.categoryId = categoryId;
        this.modelId = modelId;
        this.price = price;
        this.itemName = itemName;
        this.qtyOnHand = qtyOnHand;
    }


    public ItemTM(String itemId, String categoryId, String modelId, String supplierId, double price, String itemName, int qtyOnHand) {
        this.setItemId(itemId);
        this.setCategoryId(categoryId);
        this.setModelId(modelId);
        this.setSupplierId(supplierId);
        this.setPrice(price);
        this.setItemName(itemName);
        this.setQtyOnHand(qtyOnHand);
    }

    public ItemTM() {
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

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
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

    public int getQtyOnHand() {
        return qtyOnHand;
    }

    public void setQtyOnHand(int qtyOnHand) {
        this.qtyOnHand = qtyOnHand;
    }

    @Override
    public String toString() {
        return "ItemTM{" +
                "itemId='" + itemId + '\'' +
                ", categoryId='" + categoryId + '\'' +
                ", modelId='" + modelId + '\'' +
                ", supplierId='" + supplierId + '\'' +
                ", price=" + price +
                ", itemName='" + itemName + '\'' +
                ", qtyOnHand=" + qtyOnHand +
                '}';
    }
}
