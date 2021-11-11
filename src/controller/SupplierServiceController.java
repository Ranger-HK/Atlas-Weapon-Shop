package controller;

import db.DbConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Supplier;
import view.tm.SupplierTM;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SupplierServiceController {

    public boolean saveSupplier(Supplier supplier) throws SQLException, ClassNotFoundException {
        Connection connection = DbConnection.getInstance().getConnection();
        String query = "INSERT INTO Supplier VALUES(?,?,?,?,?,?)";
        PreparedStatement statement = connection.prepareStatement(query);

        statement.setObject(1,supplier.getSupplierId());
        statement.setObject(2,supplier.getSupplierName());
        statement.setObject(3,supplier.getSupplierPayment());
        statement.setObject(4,supplier.getItemQty());
        statement.setObject(5,supplier.getItemPrice());
        statement.setObject(6,supplier.getSupplyItem());

        return statement.executeUpdate() > 0;
    }

    public ObservableList<SupplierTM>getSupplierList() throws SQLException, ClassNotFoundException {
        ObservableList<SupplierTM> suppliers = FXCollections.observableArrayList();
        Connection connection = DbConnection.getInstance().getConnection();
        String query = "SELECT * FROM Supplier";
        PreparedStatement statement = connection.prepareStatement(query);
        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()){
            suppliers.add(new SupplierTM(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getInt(3),
                    resultSet.getInt(4),
                    resultSet.getDouble(5),
                    resultSet.getString(6)

            ));

        }
        return suppliers;
    }

    public boolean deleteSupplier(String supplierName) throws SQLException, ClassNotFoundException {
        if (DbConnection.getInstance().getConnection().prepareStatement("DELETE FROM Supplier WHERE SupplierId='"+supplierName+"'").executeUpdate() > 0){
            return true;
        }else {
            return false;
        }
    }

    public boolean updateSupplier(SupplierTM supplier) throws SQLException, ClassNotFoundException {
        PreparedStatement statement = DbConnection.getInstance().getConnection().prepareStatement("UPDATE Supplier SET SupplierName=?, SupplierPayment=?,ItemQty=?,ItemPrice=?,SupplyItem=? WHERE SupplierId=?");
        statement.setObject(1,supplier.getSupplierName());
        statement.setObject(2,supplier.getSupplierPayment());
        statement.setObject(3,supplier.getItemQty());
        statement.setObject(4,supplier.getItemPrice());
        statement.setObject(5,supplier.getSupplyItem());
        statement.setObject(6,supplier.getSupplierId());

        return statement.executeUpdate() > 0;
    }


    public List<String> getAllSupplier() throws ClassNotFoundException, SQLException {
        ResultSet rst = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Supplier").executeQuery();
        List<String> ids= new ArrayList<>();
        while(rst.next()){
            ids.add(
                    rst.getString(1)
            );
        }
        return ids;
    }

    public Supplier passSupplier(String supplyId) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Supplier WHERE SupplierId ='" + supplyId + "'").executeQuery();
        if (resultSet.next()) {
            return new Supplier(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getInt(3),
                    resultSet.getInt(4),
                    resultSet.getDouble(5),
                    resultSet.getString(6)
            );
        } else {
            return null;
        }
    }

}
