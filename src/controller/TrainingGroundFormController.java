package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;
import model.TrainingGround;
import util.Validation;
import view.tm.TrainingGroundTM;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;

public class TrainingGroundFormController {
    public Label lblDate;
    public Label lblTime;
    public TableView<TrainingGroundTM> tblTrainingGround;
    public TableColumn colUserId;
    public TableColumn colRowNumber;
    public TableColumn colRowUserName;
    public TableColumn colUsingGunName;
    public TableColumn colUsingGunType;
    public JFXTextField txtUserId;
    public JFXTextField txtRowNumber;
    public JFXTextField txtUsingGunType;
    public JFXTextField txtRowUserName;
    public JFXTextField txtUsingGunName;
    public JFXButton btnAddItem;
    public JFXButton btnUpdate;
    public JFXButton btnDelete;
    public JFXButton btnClear;



    LinkedHashMap<TextField , Pattern> map = new LinkedHashMap<>();

    Pattern userId = Pattern.compile("^(U-|u-)[0-9]{4}$");
    Pattern rowNumber = Pattern.compile("^[1-5]{1}$");
    Pattern rowUserName = Pattern.compile("^[A-z0-9]{3,20}$");
    Pattern usingGunName = Pattern.compile("^[A-z0-9].{2,15}$");
    Pattern usingGunType = Pattern.compile("^[A-z0-9].{2,15}$");

    private void storeValidation(){
        map.put(txtUserId,userId);
        map.put(txtRowNumber,rowNumber);
        map.put(txtRowUserName,rowUserName);
        map.put(txtUsingGunName,usingGunName);
        map.put(txtUsingGunType,usingGunType);
    }


    public void initialize(){
        loadDateAndTime();
        try {
            setTrainingGroundToTable();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        storeValidation();
        btnAddItem.setDisable(true);
        btnDelete.setDisable(true);
        btnUpdate.setDisable(true);
    }



    private void loadDateAndTime() {
        Date date = new Date();
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MMMM-dd");
        lblDate.setText(f.format(date));

        Timeline time = new Timeline(new KeyFrame(Duration.ZERO, e->{
            LocalTime currentTime = LocalTime.now();
            lblTime.setText(
                    currentTime.getHour()+ " : "+currentTime.getMinute()+ " : "+currentTime.getSecond()
            );
        }),
                new KeyFrame(Duration.seconds(1))
        );
        time.setCycleCount(Animation.INDEFINITE);
        time.play();
    }


    public void setTrainingGroundToTable() throws SQLException, ClassNotFoundException {
        ObservableList<TrainingGroundTM> trainingGround = new TrainingGroundServiceController().getTrainingGroundList();
        colUserId.setCellValueFactory(new PropertyValueFactory<>("userId"));
        colRowNumber.setCellValueFactory(new PropertyValueFactory<>("rowNumber"));
        colRowUserName.setCellValueFactory(new PropertyValueFactory<>("rowUserName"));
        colUsingGunName.setCellValueFactory(new PropertyValueFactory<>("usingGunName"));
        colUsingGunType.setCellValueFactory(new PropertyValueFactory<>("usingGunType"));

        tblTrainingGround.setItems(trainingGround);
    }



    public void btnAddOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        TrainingGround trainingGround = new TrainingGround(
                txtUserId.getText(),
                Integer.parseInt(txtRowNumber.getText()),
                txtRowUserName.getText(),
                txtUsingGunName.getText(),
                txtUsingGunType.getText()
        );

       if (new TrainingGroundServiceController().saveTrainingGround(trainingGround)){
           new AddNotifications().sceneNotifications("CONFIRMATION"," Save Completed..",0,1);
           setTrainingGroundToTable();
           btnAddItem.setDisable(true);

       }else {
           new AddNotifications().sceneNotifications("WARNING","Try Again..",0,5);

       }
       clearField();

    }


    public void btnUpdateOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        TrainingGroundTM trainingGround = new TrainingGroundTM(
                txtUserId.getText(),
                Integer.parseInt(txtRowNumber.getText()),
                txtRowUserName.getText(),
                txtUsingGunName.getText(),
                txtUsingGunType.getText()
        );

        if (new TrainingGroundServiceController().updateTrainingGround(trainingGround)){
            new AddNotifications().sceneNotifications("CONFIRMATION"," Update Completed..",0,1);
            setTrainingGroundToTable();
            btnUpdate.setDisable(true);

        }else {
            new AddNotifications().sceneNotifications("WARNING","Try Again..",0,5);


        }
        clearField();
    }


    public void btnDeleteOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        TrainingGroundTM trainingGround = tblTrainingGround.getSelectionModel().getSelectedItem();
        String training = trainingGround.getUserId();

        if (new TrainingGroundServiceController().deleteTrainingGround(training)){
            new AddNotifications().sceneNotifications("CONFIRMATION","Delete Completed..",0,3);
            setTrainingGroundToTable();
            btnDelete.setDisable(true);


        }else {
            new AddNotifications().sceneNotifications("WARNING","Try Again..",0,5);

        }
        clearField();
    }

    private void clearField(){
        txtUserId.clear();
        txtRowNumber.clear();
        txtRowUserName.clear();
        txtUsingGunName.clear();
        txtUsingGunType.clear();
        txtUserId.requestFocus();
    }

    public void btnClearOnAction(ActionEvent actionEvent) {
        clearField();
    }

    public void moveToRowNumber(ActionEvent actionEvent) {
        txtRowNumber.requestFocus();
    }

    public void moveToRowUserName(ActionEvent actionEvent) {
        txtRowUserName.requestFocus();
    }

    public void moveToUsingGunName(ActionEvent actionEvent) {
        txtUsingGunName.requestFocus();
    }

    public void moveToUsingGunType(ActionEvent actionEvent) {
        txtUsingGunType.requestFocus();

    }

    public void MouseOnAction(MouseEvent mouseEvent) {
        try{
        TrainingGroundTM trainingGround = null;
        trainingGround = tblTrainingGround.getSelectionModel().getSelectedItem();
        txtUserId.setText(trainingGround.getUserId());
        txtRowNumber.setText(""+trainingGround.getRowNumber());
        txtRowUserName.setText(trainingGround.getRowUserName());
        txtUsingGunName.setText(trainingGround.getUsingGunName());
        txtUsingGunType.setText(trainingGround.getUsingGunType());
        btnDelete.setDisable(false);
        btnUpdate.setDisable(false);
    } catch (Exception e) {
        }
        }

        public void txtTrainingGroundKeyRelease(KeyEvent keyEvent) {
        btnAddItem.setDisable(true);
        Object response = Validation.validate(map,btnAddItem);
        if (keyEvent.getCode()== KeyCode.ENTER){
            if (response instanceof TextField){
                TextField error = (TextField) response;
                error.requestFocus();
            }else if (response instanceof Boolean){
                new AddNotifications().sceneNotifications("CONFIRMATION"," Success ",0,1);

            }
        }
    }
}
