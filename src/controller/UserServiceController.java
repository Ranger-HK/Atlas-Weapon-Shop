package controller;

import db.DbConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.User;
import view.tm.UserTM;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserServiceController {

    public boolean saveUser(User user) throws SQLException, ClassNotFoundException {
        Connection connection = DbConnection.getInstance().getConnection();
        String query = "INSERT INTO `User` VALUES(?,?,md5(?),?,?)";
        PreparedStatement statement = connection.prepareStatement(query);

        statement.setObject(1,user.getUserName());
        statement.setObject(2,user.getName());
        statement.setObject(3,user.getUserPassword());
        statement.setObject(4,user.getRole());
        statement.setObject(5,user.getAddress());

        return statement.executeUpdate() > 0;
    }


    public String login(User login) throws SQLException, ClassNotFoundException {
        String username = login.getUserName();
        String password = login.getUserPassword();

        PreparedStatement statement = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM  user WHERE UserName = ? AND  UserPassword=md5(?)");
        statement.setObject(1,username);
        statement.setObject(2,password);
        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            String userRole = resultSet.getString(4);
            return userRole;
        }else {
            return "";
        }
    }

    public ObservableList<UserTM> getUserList() throws SQLException, ClassNotFoundException {
        ObservableList<UserTM> users = FXCollections.observableArrayList();
        Connection connection = DbConnection.getInstance().getConnection();
        String query = "SELECT * FROM `User`";
        PreparedStatement statement = connection.prepareStatement(query);
        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()){
            users.add(new UserTM(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5)

            ));
        }
        return users;
    }


    public boolean deleteUser(String userName) throws SQLException, ClassNotFoundException {
        if (DbConnection.getInstance().getConnection().prepareStatement("DELETE FROM `User` WHERE UserName='"+userName+"'").executeUpdate() > 0) {
            return true;
        }else{
            return false;
        }
    }


    public boolean updateUser(UserTM user) throws SQLException, ClassNotFoundException {
        PreparedStatement statement = DbConnection.getInstance().getConnection().prepareStatement("UPDATE `User` SET Name=?, UserPassword=?,Role=?,Address=? WHERE UserName=?");
        statement.setObject(1,user.getName());
        statement.setObject(2,user.getUserPassword());
        statement.setObject(3,user.getRole());
        statement.setObject(4,user.getAddress());
        statement.setObject(5,user.getUserName());
        return statement.executeUpdate() > 0;
    }

}
