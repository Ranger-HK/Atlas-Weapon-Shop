package controller;

import db.DbConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import view.tm.ItemTM;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AvailableItemServiceController {
    public ObservableList<ItemTM>getItemToDashboard() throws SQLException, ClassNotFoundException {
        ObservableList<ItemTM>itemList= FXCollections.observableArrayList();
        PreparedStatement statement = DbConnection.getInstance().getConnection().prepareStatement("SELECT ItemId,ItemName,QtyOnHand,Price,CategoryId,ModelId FROM Item WHERE QtyOnHand > 0 ");
        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()){
            itemList.add(new ItemTM(
                resultSet.getString(1),
                resultSet.getString(5),
                resultSet.getString(6),
                resultSet.getDouble(4),
                resultSet.getString(2),
                resultSet.getInt(3)

            ));
        }
        return itemList;
    }


    public static List<ItemTM> searchItem(String value) throws SQLException, ClassNotFoundException {
        Connection con = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = con.prepareStatement("SELECT * FROM Item WHERE (QtyOnHand > 0) AND CONCAT( ItemId,ItemName) LIKE '%" + value + "%'");
        ResultSet rs = pstm.executeQuery();

        List<ItemTM> item = new ArrayList<>();

        while (rs.next()) {
            item.add(new ItemTM(

                    rs.getString(1),
                    rs.getString(2),
                    rs.getString(3),
                    rs.getDouble(5),
                    rs.getString(6),
                    rs.getInt(7)

            ));
        }

        return item;

    }
    public String getCountOfCustomers() throws SQLException, ClassNotFoundException {
        String count=null;
        ResultSet rst = DbConnection.getInstance().getConnection().prepareStatement("SELECT count(CustomerId) FROM Customer").executeQuery();
        while (rst.next()){
            count = rst.getString(1);
        }
        return count;
    }

    public String getCountOfItems() throws SQLException, ClassNotFoundException {
        String count=null;
        ResultSet rst = DbConnection.getInstance().getConnection().prepareStatement("SELECT count(ItemId) FROM Item WHERE (QtyOnHand > 0)").executeQuery();
        while (rst.next()){
            count = rst.getString(1);
        }
        return count;
    }

    public String getCountOfSupplier() throws SQLException, ClassNotFoundException {
        String count=null;
        ResultSet rst = DbConnection.getInstance().getConnection().prepareStatement("SELECT count(supplierId) FROM Supplier").executeQuery();
        while (rst.next()){
            count = rst.getString(1);
        }
        return count;
    }

}

