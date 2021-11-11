package controller;

import db.DbConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Category;
import view.tm.CategoryTM;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoryServiceController {


    public boolean saveCategory(Category category) throws SQLException, ClassNotFoundException {
        Connection connection = DbConnection.getInstance().getConnection();
        String query = "INSERT INTO Category VALUES(?,?)";
        PreparedStatement statement = connection.prepareStatement(query);

        statement.setObject(1,category.getCategoryId());
        statement.setObject(2,category.getCategoryName());

        return statement.executeUpdate() > 0;


    }

    public boolean deleteCategory (String categoryName) throws SQLException, ClassNotFoundException {
        if(DbConnection.getInstance().getConnection().prepareStatement("DELETE FROM Category WHERE CategoryId='"+categoryName+"'").executeUpdate() > 0) {
            return true;

        }else{
            return false;
        }
    }

    public ObservableList<CategoryTM> getCategoryList() throws SQLException, ClassNotFoundException {
        ObservableList<CategoryTM> category = FXCollections.observableArrayList();
        Connection connection = DbConnection.getInstance().getConnection();
        String query = "SELECT * FROM Category";
        PreparedStatement statement = connection.prepareStatement(query);
        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()){
            category.add(new CategoryTM(
                    resultSet.getString(1),
                    resultSet.getString(2)
            ));

        }
        return category;
    }

    public List<String> getAllCategory() throws ClassNotFoundException, SQLException {
        ResultSet rst = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Category").executeQuery();
        List<String> ids= new ArrayList<>();
        while(rst.next()){
            ids.add(
                    rst.getString(1)
            );
        }
        return ids;
    }


}
