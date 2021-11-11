package model;

public class Customer {
    private String customerId;
    private String customerName;
    private String customerNic;
    private int telephoneNumber;
    private String customerAddress;
    private String customerLicenseNumber;

    public Customer() {
    }

    public Customer(String customerId, String customerName, String customerNic, int telephoneNumber, String customerAddress, String customerLicenseNumber) {
        this.setCustomerId(customerId);
        this.setCustomerName(customerName);
        this.setCustomerNic(customerNic);
        this.setTelephoneNumber(telephoneNumber);
        this.setCustomerAddress(customerAddress);
        this.setCustomerLicenseNumber(customerLicenseNumber);
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerNic() {
        return customerNic;
    }

    public void setCustomerNic(String customerNic) {
        this.customerNic = customerNic;
    }

    public int getTelephoneNumber() {
        return telephoneNumber;
    }

    public void setTelephoneNumber(int telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    public String getCustomerLicenseNumber() {
        return customerLicenseNumber;
    }

    public void setCustomerLicenseNumber(String customerLicenseNumber) {
        this.customerLicenseNumber = customerLicenseNumber;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "customerId='" + customerId + '\'' +
                ", customerName='" + customerName + '\'' +
                ", customerNic='" + customerNic + '\'' +
                ", telephoneNumber=" + telephoneNumber +
                ", customerAddress='" + customerAddress + '\'' +
                ", customerLicenseNumber='" + customerLicenseNumber + '\'' +
                '}';
    }
}

