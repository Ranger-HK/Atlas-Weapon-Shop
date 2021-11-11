package controller;

import db.DbConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Model;
import view.tm.ModelTM;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ModelServiceController {

    public boolean saveModel(Model model) throws SQLException, ClassNotFoundException {
        Connection connection = DbConnection.getInstance().getConnection();
        String query = "INSERT INTO Model VALUES(?,?)";
        PreparedStatement statement = connection.prepareStatement(query);

        statement.setObject(1,model.getModelId());
        statement.setObject(2,model.getModelName());

        return statement.executeUpdate() > 0;


    }

    public boolean deleteModel(String modelName) throws SQLException, ClassNotFoundException {
        if(DbConnection.getInstance().getConnection().prepareStatement("DELETE FROM Model WHERE ModelId='"+modelName+"'").executeUpdate() > 0) {
            return true;

        }else{
            return false;
        }
    }

    public ObservableList<ModelTM> getModelList() throws SQLException, ClassNotFoundException {
        ObservableList<ModelTM> models = FXCollections.observableArrayList();
        Connection connection = DbConnection.getInstance().getConnection();
        String query = "SELECT * FROM Model";
        PreparedStatement statement = connection.prepareStatement(query);
        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()){
            models.add(new ModelTM(
                    resultSet.getString(1),
                    resultSet.getString(2)
            ));

        }
        return models;
    }

    public List<String> getAllModel() throws ClassNotFoundException, SQLException {
        ResultSet rst = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Model").executeQuery();
        List<String> ids= new ArrayList<>();
        while(rst.next()){
            ids.add(
                    rst.getString(1)
            );
        }
        return ids;
    }
}
