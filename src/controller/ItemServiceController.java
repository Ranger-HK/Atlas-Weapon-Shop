package controller;

import db.DbConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Item;
import view.tm.ItemTM;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemServiceController {

    public boolean saveItem(Item item) throws SQLException, ClassNotFoundException {
        Connection connection = DbConnection.getInstance().getConnection();
        String query = "INSERT INTO Item VALUES(?,?,?,?,?,?,?)";
        PreparedStatement statement = connection.prepareStatement(query);

        statement.setObject(1, item.getItemId());
        statement.setObject(2, item.getCategoryId());
        statement.setObject(3, item.getModelId());
        statement.setObject(4, item.getSupplierId());
        statement.setObject(5, item.getPrice());
        statement.setObject(6, item.getItemName());
        statement.setObject(7, item.getQtyOnHand());

        return statement.executeUpdate() > 0;

    }

    public ObservableList<ItemTM> getItemList() throws SQLException, ClassNotFoundException {
        ObservableList<ItemTM> items = FXCollections.observableArrayList();
        Connection connection = DbConnection.getInstance().getConnection();
        String query = "SELECT * FROM Item ";
        PreparedStatement statement = connection.prepareStatement(query);
        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            items.add(new ItemTM(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getDouble(5),
                    resultSet.getString(6),
                    resultSet.getInt(7)

            ));
        }
        return items;
    }


        public boolean updateItem(ItemTM item) throws SQLException, ClassNotFoundException {
            PreparedStatement statement = DbConnection.getInstance().getConnection().prepareStatement("UPDATE Item SET CategoryId=?,ModelId=?,SupplierId=?,Price=?,ItemName=?,QtyOnHand=? WHERE ItemId=? ");
            statement.setObject(1,item.getCategoryId());
            statement.setObject(2,item.getModelId());
            statement.setObject(3,item.getSupplierId());
            statement.setObject(4,item.getPrice());
            statement.setObject(5,item.getItemName());
            statement.setObject(6,item.getQtyOnHand());
            statement.setObject(7,item.getItemId());

            return statement.executeUpdate() > 0;
        }


        public boolean deleteItem(String itemName) throws SQLException, ClassNotFoundException {
          if (DbConnection.getInstance().getConnection().prepareStatement("DELETE FROM Item WHERE ItemId='"+itemName+"'").executeUpdate() >0){
              return true;
          }else {
              return false;
          }
        }

        public List<String> getAllItem() throws SQLException, ClassNotFoundException {
            ResultSet rst = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Item WHERE QtyOnHand > 0").executeQuery();
            List<String> ids= new ArrayList<>();
            while(rst.next()){
                ids.add(
                        rst.getString(1)
                );
            }
            return ids;
        }

        public Item passItem(String itemId) throws SQLException, ClassNotFoundException {
            ResultSet rst = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Item WHERE ItemId='" + itemId + "'").executeQuery();
            if(rst.next()){
                return new Item(
                        rst.getString(1),
                        rst.getString(2),
                        rst.getString(3),
                        rst.getString(4),
                        rst.getDouble(5),
                        rst.getString(6),
                        rst.getInt(7)

                );
            }else {
                return null;
            }
        }
}