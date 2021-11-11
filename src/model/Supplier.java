package model;

public class Supplier {
    private String supplierId;
    private String supplierName;
    private int supplierPayment;
    private int itemQty;
    private double itemPrice;
    private String supplyItem;

    public Supplier() {
    }

    public Supplier(String supplierId, String supplierName, int supplierPayment, int itemQty, double itemPrice, String supplyItem) {
        this.setSupplierId(supplierId);
        this.setSupplierName(supplierName);
        this.setSupplierPayment(supplierPayment);
        this.setItemQty(itemQty);
        this.setItemPrice(itemPrice);
        this.setSupplyItem(supplyItem);
    }

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public int getSupplierPayment() {
        return supplierPayment;
    }

    public void setSupplierPayment(int supplierPayment) {
        this.supplierPayment = supplierPayment;
    }

    public int getItemQty() {
        return itemQty;
    }

    public void setItemQty(int itemQty) {
        this.itemQty = itemQty;
    }

    public double getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(double itemPrice) {
        this.itemPrice = itemPrice;
    }

    public String getSupplyItem() {
        return supplyItem;
    }

    public void setSupplyItem(String supplyItem) {
        this.supplyItem = supplyItem;
    }

    @Override
    public String toString() {
        return "Supplier{" +
                "supplierId='" + supplierId + '\'' +
                ", supplierName='" + supplierName + '\'' +
                ", supplierPayment=" + supplierPayment +
                ", itemQty=" + itemQty +
                ", itemPrice=" + itemPrice +
                ", supplyItem='" + supplyItem + '\'' +
                '}';
    }
}
