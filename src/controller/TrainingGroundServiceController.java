package controller;

import db.DbConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.TrainingGround;
import view.tm.TrainingGroundTM;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TrainingGroundServiceController {

    public boolean saveTrainingGround(TrainingGround trainingGround) throws SQLException, ClassNotFoundException {
        Connection connection = DbConnection.getInstance().getConnection();
        String query = "INSERT INTO TrainingGround VALUES(?,?,?,?,?)";
        PreparedStatement statement = connection.prepareStatement(query);

        statement.setObject(1,trainingGround.getUserId());
        statement.setObject(2,trainingGround.getRowNumber());
        statement.setObject(3,trainingGround.getRowUserName());
        statement.setObject(4,trainingGround.getUsingGunName());
        statement.setObject(5,trainingGround.getUsingGunType());

        return statement.executeUpdate() > 0;
    }


    public ObservableList<TrainingGroundTM> getTrainingGroundList() throws SQLException, ClassNotFoundException {
        ObservableList<TrainingGroundTM> training = FXCollections.observableArrayList();
        Connection connection = DbConnection.getInstance().getConnection();
        String query = "SELECT * FROM TrainingGround";
        PreparedStatement statement = connection.prepareStatement(query);
        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()){
            training.add(new TrainingGroundTM(
                    resultSet.getString(1),
                    resultSet.getInt(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5)

            ));

        }
        return training;
    }


    public boolean deleteTrainingGround(String training) throws SQLException, ClassNotFoundException {
        if (DbConnection.getInstance().getConnection().prepareStatement("DELETE FROM TrainingGround WHERE UserId='"+training+"'").executeUpdate() > 0){
            return true;
        }else {
            return false;
        }
    }


    public boolean updateTrainingGround(TrainingGroundTM trainingGround) throws SQLException, ClassNotFoundException {
        PreparedStatement statement = DbConnection.getInstance().getConnection().prepareStatement("UPDATE TrainingGround SET RowNumber=?,RowUserName=?,UsingGunName=?,UsingGunType=? WHERE UserId=?");
        statement.setObject(1,trainingGround.getRowNumber());
        statement.setObject(2,trainingGround.getRowUserName());
        statement.setObject(3,trainingGround.getUsingGunName());
        statement.setObject(4,trainingGround.getUsingGunType());
        statement.setObject(5,trainingGround.getUserId());

        return statement.executeUpdate() > 0;
    }
}
