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
import model.User;
import util.Validation;
import view.tm.UserTM;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;

public class ManageUserFormController {
    public JFXTextField txtUserName;
    public JFXTextField txtName;
    public JFXTextField txtUserPassword;
    public JFXTextField txtRole;
    public JFXTextField txtAddress;
    public TableView<UserTM> tblMangeUser;
    public TableColumn colUserName;
    public TableColumn colName;
    public TableColumn colUserPassword;
    public TableColumn colRole;
    public TableColumn colAddress;
    public Label lblDate;
    public Label lblTime;
    public JFXButton btnAdd;
    public JFXButton btnUpdate;
    public JFXButton btnDelete;



    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap<>();

    Pattern userName = Pattern.compile("^[A-z0-9]{3,20}$");
    Pattern name = Pattern.compile("^[A-z]{3,20}$");
    Pattern userPassword = Pattern.compile("^.*(?=.{8,})(?=..*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).*$");
    Pattern role = Pattern.compile("^[A-z]{3,20}$");
    Pattern address = Pattern.compile("^[A-z 0-9 \\/\\,]{2,50}[A-z 0-9]{1,50}$");


    private void storeValidation(){
        map.put(txtUserName,userName);
        map.put(txtName,name);
        map.put(txtUserPassword,userPassword);
        map.put(txtRole,role);
        map.put(txtAddress,address);

    }

    public void initialize() throws SQLException, ClassNotFoundException {
        storeValidation();
        btnAdd.setDisable(true);
        setUsersToTable();
        loadDateAndTime();
        btnDelete.setDisable(true);
        btnUpdate.setDisable(true);
    }


    public void  setUsersToTable() throws SQLException, ClassNotFoundException {
        ObservableList<UserTM> user = new UserServiceController().getUserList();
        colUserName.setCellValueFactory(new PropertyValueFactory<>("userName"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colUserPassword.setCellValueFactory(new PropertyValueFactory<>("userPassword"));
        colRole.setCellValueFactory(new PropertyValueFactory<>("role"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));

        tblMangeUser.setItems(user);
    }



    public void saveUserOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        User user = new User(
                txtUserName.getText(), txtUserPassword.getText(), txtName.getText(),
                txtAddress.getText(),
                txtRole.getText()


        );

        if (new UserServiceController().saveUser(user)){
            new AddNotifications().sceneNotifications("CONFIRMATION","User Save Completed..",0,1);
            setUsersToTable();
            btnAdd.setDisable(true);
    }else {
            new AddNotifications().sceneNotifications("WARNING","Try Again..",0,5);
        }
        clearField();
    }



    public void updateOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        UserTM user = new UserTM(
                txtUserName.getText(),
                txtName.getText(),
                txtUserPassword.getText(),
                txtRole.getText(),
                txtAddress.getText()
        );

        if (new UserServiceController().updateUser(user)) {
            setUsersToTable();
            new AddNotifications().sceneNotifications("CONFIRMATION","Update User Completed",0,1);
            btnUpdate.setDisable(true);

        }else{
            new AddNotifications().sceneNotifications("WARNING","Try Again..",0,5);

        }
        clearField();

    }




    public void deleteOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        UserTM user = tblMangeUser.getSelectionModel().getSelectedItem();
        String userName = user.getUserName();

        if (new UserServiceController().deleteUser(userName)) {
            new AddNotifications().sceneNotifications("CONFIRMATION","Delete User Completed",0,1);
            setUsersToTable();
            btnDelete.setDisable(true);


        }else {
            new AddNotifications().sceneNotifications("WARNING","Try Again..",0,5);


        }
        clearField();

    }

    private void clearField(){
        txtUserName.clear();
        txtName.clear();
        txtUserPassword.clear();
        txtRole.clear();
        txtAddress.clear();
        txtUserName.requestFocus();
    }


    public void MouseOnAction(MouseEvent mouseEvent) {
        try{
        UserTM user = null;
        user = tblMangeUser.getSelectionModel().getSelectedItem();
        txtUserName.setText(user.getUserName());
        txtName.setText(user.getName());
        txtUserPassword.setText(user.getUserPassword());
        txtRole.setText(user.getRole());
        txtAddress.setText(user.getAddress());
        btnUpdate.setDisable(false);
        btnDelete.setDisable(false);


    } catch (Exception e) {
        }
        }

        public void clearOnAction(ActionEvent actionEvent) {
        clearField();
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



    public void moveToName(ActionEvent actionEvent) {
        txtName.requestFocus();
    }

    public void moveToUserPassword(ActionEvent actionEvent) {
        txtUserPassword.requestFocus();

    }

    public void moveToRole(ActionEvent actionEvent) {
        txtRole.requestFocus();

    }

    public void moveToAddress(ActionEvent actionEvent) {
        txtAddress.requestFocus();

    }

    public void txtManageUserKeyRelease(KeyEvent keyEvent) {
        btnAdd.setDisable(true);
        Object response = Validation.validate(map,btnAdd);
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
