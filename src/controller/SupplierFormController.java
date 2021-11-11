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
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import model.Supplier;
import util.Validation;
import view.tm.SupplierTM;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;

public class SupplierFormController {

    public AnchorPane contextSupplierForm;
    public JFXTextField txtSupplierId;
    public JFXTextField txtSupplierName;
    public JFXTextField txtSupplierPayment;
    public JFXTextField txtItemQty;
    public JFXTextField txtItemPrice;
    public JFXTextField txtSupplyItem;
    public TableView<SupplierTM> tblSupplier;
    public TableColumn colSupplierName;
    public TableColumn colSupplierPayment;
    public TableColumn colItemQty;
    public TableColumn colItemPrice;
    public TableColumn colSupplyItem;
    public Label lblDate;
    public Label lblTime;
    public JFXButton btnAddItem;
    public JFXButton btnUpdate;
    public JFXButton btnDelete;
    public JFXButton btnClear;
    public TableColumn colSupplierId;


    LinkedHashMap<TextField , Pattern> map = new LinkedHashMap<>();

    Pattern supplierId = Pattern.compile("^(S-|s-)[0-9]{4}$");
    Pattern supplierName = Pattern.compile("^[A-z0-9]{3,20}$");
    Pattern supplierPayment = Pattern.compile("^[0-9]{4,8}$");
    Pattern itemQty = Pattern.compile("^[0-9]{3,5}$");
    Pattern itemPrice = Pattern.compile("^[0-9]{3,8}$");
    Pattern supplyItem = Pattern.compile("^[A-z0-9]{2,20}$");


    private void storeValidation(){
        map.put(txtSupplierId,supplierId);
        map.put(txtSupplierName,supplierName);
        map.put(txtSupplierPayment,supplierPayment);
        map.put(txtItemQty,itemQty);
        map.put(txtItemPrice,itemPrice);
        map.put(txtSupplyItem,supplyItem);

    }


    public void initialize(){
        storeValidation();
        btnAddItem.setDisable(true);
        loadDateAndTime();
        try {
            setSupplierToTable();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
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


    public void setSupplierToTable() throws SQLException, ClassNotFoundException {
        ObservableList<SupplierTM> supplier = new SupplierServiceController().getSupplierList();
        colSupplierId.setCellValueFactory(new PropertyValueFactory<>("supplierId"));
        colSupplierName.setCellValueFactory(new PropertyValueFactory<>("supplierName"));
        colSupplierPayment.setCellValueFactory(new PropertyValueFactory<>("supplierPayment"));
        colItemQty.setCellValueFactory(new PropertyValueFactory<>("itemQty"));
        colItemPrice.setCellValueFactory(new PropertyValueFactory<>("itemPrice"));
        colSupplyItem.setCellValueFactory(new PropertyValueFactory<>("supplyItem"));


        tblSupplier.setItems(supplier);
    }


    public void btnAddOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        Supplier supplier = new Supplier(
                txtSupplierId.getText(),
                txtSupplierName.getText(),
                Integer.parseInt(txtSupplierPayment.getText()),
                Integer.parseInt(txtItemQty.getText()),
                Double.parseDouble(txtItemPrice.getText()),
                txtSupplyItem.getText()

        );

        if (new SupplierServiceController().saveSupplier(supplier)){
            new AddNotifications().sceneNotifications("CONFIRMATION","Supplier Save Completed..",0,1);
            setSupplierToTable();
            btnAddItem.setDisable(true);

        }else {
            new AddNotifications().sceneNotifications("WARNING","Try Again..",0,5);

        }
        clearField();


    }


    public void btnUpdateOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        SupplierTM supplier = new SupplierTM(
                txtSupplierId.getText(),
                txtSupplierName.getText(),
                Integer.parseInt(txtSupplierPayment.getText()),
                Integer.parseInt(txtItemQty.getText()),
                Double.parseDouble(txtItemPrice.getText()),
                txtSupplyItem.getText()
        );

        if (new SupplierServiceController().updateSupplier(supplier)){

            new AddNotifications().sceneNotifications("CONFIRMATION","Supplier Update Completed..",0,1);
            setSupplierToTable();
            btnUpdate.setDisable(true);

        }else{
            new AddNotifications().sceneNotifications("WARNING","Try Again..",0,5);
        }
        clearField();

    }


    public void btnDeleteOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        SupplierTM supplier = tblSupplier.getSelectionModel().getSelectedItem();
        String supplierName = supplier.getSupplierId();

        if (new SupplierServiceController().deleteSupplier(supplierName)){
            new AddNotifications().sceneNotifications("CONFIRMATION","Supplier Delete Completed..",0,3);
            setSupplierToTable();
            btnDelete.setDisable(true);

        }else {
            new AddNotifications().sceneNotifications("WARNING","Try Again..",0,5);

        }
        clearField();
    }



    private void clearField(){
        txtSupplierId.clear();
        txtSupplierName.clear();
        txtSupplierPayment.clear();
        txtItemQty.clear();
        txtItemPrice.clear();
        txtSupplyItem.clear();
        txtSupplierId.requestFocus();

    }


    public void btnClearOnAction(ActionEvent actionEvent) {
        clearField();

    }

    public void moveToSupplierName(ActionEvent actionEvent) {
        txtSupplierName.requestFocus();

    }

    public void moveToSupplierPayment(ActionEvent actionEvent) {
        txtSupplierPayment.requestFocus();

    }

    public void moveToItemQty(ActionEvent actionEvent) {
        txtItemQty.requestFocus();

    }

    public void moveToItemPrice(ActionEvent actionEvent) {
        txtItemPrice.requestFocus();

    }

    public void moveToSupplyItem(ActionEvent actionEvent) {
        txtSupplyItem.requestFocus();

    }

    public void MouseOnAction(MouseEvent mouseEvent) {
        try{
        SupplierTM supplier = null;
        supplier = tblSupplier.getSelectionModel().getSelectedItem();
        txtSupplierId.setText(supplier.getSupplierId());
        txtSupplierName.setText(supplier.getSupplierName());
        txtSupplierPayment.setText(""+supplier.getSupplierPayment());
        txtItemQty.setText(""+supplier.getItemQty());
        txtItemPrice.setText(""+supplier.getItemPrice());
        txtSupplyItem.setText(supplier.getSupplyItem());
        btnUpdate.setDisable(false);
        btnDelete.setDisable(false);
    } catch (Exception e) {

        }
        }

        public void txtSupplierKeyRelease(KeyEvent keyEvent) {
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
