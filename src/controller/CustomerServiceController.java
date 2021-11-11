package controller;

import db.DbConnection;
import model.Customer;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerServiceController {

    public boolean saveCustomer(Customer customer) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("INSERT INTO Customer VALUES (?,?,?,?,?,?)");
        stm.setObject(1,customer.getCustomerId());
        stm.setObject(2,customer.getCustomerName());
        stm.setObject(3,customer.getCustomerNic());
        stm.setObject(4,customer.getTelephoneNumber());
        stm.setObject(5,customer.getCustomerAddress());
        stm.setObject(6,customer.getCustomerLicenseNumber());

        return stm.executeUpdate() > 0;


    }
    public List<String> getAllCustomer() throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Customer ").executeQuery();
        List<String> ids= new ArrayList<>();
        while(rst.next()){
            ids.add(
                    rst.getString(1)
            );
        }
        return ids;

    }

    public Customer passCustomer(String customerId) throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Customer WHERE CustomerId='" + customerId + "'").executeQuery();
        if (rst.next()){
            return new Customer(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getInt(4),
                    rst.getString(5),
                    rst.getString(6)

            );
        }else {
            return null;
        }
    }

}
