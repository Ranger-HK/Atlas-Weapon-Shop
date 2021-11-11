package controller;

import db.DbConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import view.tm.RecordTM;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RecordServiceController {

    public ObservableList<RecordTM> getRecordToTable() throws SQLException, ClassNotFoundException {
        ObservableList<RecordTM> record = FXCollections.observableArrayList();
        Connection connection = DbConnection.getInstance().getConnection();
        String query = "SELECT o.customerId,o.orderDate,od.*,sum(od.orderqty*i.price) AS Total FROM `order` o,orderdetails od,item i WHERE o.orderId=od.orderId AND i.itemId=od.itemId group by od.orderId,i.itemId";
        PreparedStatement statement = connection.prepareStatement(query);
        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            record.add(new RecordTM(
                    resultSet.getString(4),
                    resultSet.getString(3),
                    resultSet.getString(1),
                    resultSet.getInt(5),
                    resultSet.getString(2),
                    resultSet.getInt(6)

            ));

        }
        return record;

    }

    public static List<RecordTM> searchRecord(String value) throws SQLException, ClassNotFoundException {
        List<RecordTM> record = new ArrayList<>();
        ResultSet rst = DbConnection.getInstance().getConnection().prepareStatement("SELECT o.customerId,o.orderDate,od.*,sum(od.orderqty*i.price) AS Total FROM `order` o,orderdetails od,item i WHERE (o.orderId=od.orderId AND i.itemId=od.itemId) AND CONCAT(od.orderId,i.itemId) LIKE'%"+value+"%' group by od.orderId,i.itemId;").executeQuery();
        while (rst.next()){
            record.add(new RecordTM(
                    rst.getString(4),
                    rst.getString(3),
                    rst.getString(1),
                    rst.getInt(5),
                    rst.getString(2),
                    rst.getInt(6)

            ));
        }
        return record;
    }


}