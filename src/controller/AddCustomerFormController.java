package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Customer;
import util.Validation;
import java.io.IOException;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;

public class AddCustomerFormController {
    public AnchorPane contextAddCustomers;
    public JFXTextField txtCustomerId;
    public JFXTextField txtCustomerName;
    public JFXTextField txtTelephoneNumber;
    public JFXTextField txtCustomerAddress;
    public JFXTextField txtCustomerLicenseNumber;
    public JFXTextField txtCustomerNic;
    public JFXButton btnAddCustomer;



    LinkedHashMap<TextField , Pattern> map = new LinkedHashMap<>();

    Pattern customerId = Pattern.compile("^(CU-|cu-)[0-9]{4}$");
    Pattern name = Pattern.compile("^[A-z0-9]{3,20}$");
    Pattern nic = Pattern.compile("^[0-9]{9}[v]|[0-9]{12}$");
    Pattern teleNumber = Pattern.compile("^[0-9]{10}$");
    Pattern address = Pattern.compile("^[A-z 0-9 \\/\\,]{2,50}[A-z 0-9]{1,50}$");
    Pattern licenseNumber = Pattern.compile("^(L-|l-)[0-9]{4}$");

    private void storeValidation(){
        map.put(txtCustomerId,customerId);
        map.put(txtCustomerName,name);
        map.put(txtCustomerNic,nic);
        map.put(txtTelephoneNumber,teleNumber);
        map.put(txtCustomerAddress,address);
        map.put(txtCustomerLicenseNumber,licenseNumber);
    }


    public void initialize(){
        storeValidation();
        btnAddCustomer.setDisable(true);

    }

    public void addCustomerOnAction(ActionEvent actionEvent) throws IOException, SQLException, ClassNotFoundException {
        Customer customer = new Customer(
                txtCustomerId.getText(),
                txtCustomerName.getText(),
                txtCustomerNic.getText(),
                Integer.parseInt(txtTelephoneNumber.getText()),
                txtCustomerAddress.getText(),
                txtCustomerLicenseNumber.getText()
        );
        if (new CustomerServiceController().saveCustomer(customer)){
            new AddNotifications().sceneNotifications("Saved","Customer Saved..",0,1);
            btnAddCustomer.setDisable(true);

        }else {
            new AddNotifications().sceneNotifications("Warning","Try Again..",0,5);
        }
        Stage stage = (Stage) btnAddCustomer.getScene().getWindow();
        stage.close();
        clearField();

    }


    private void clearField(){
        txtCustomerId.clear();
        txtCustomerName.clear();
        txtCustomerNic.clear();
        txtTelephoneNumber.clear();
        txtCustomerAddress.clear();
        txtCustomerLicenseNumber.clear();
        txtCustomerId.requestFocus();
    }


    public void moveToCustomerName(ActionEvent actionEvent) {
        txtCustomerName.requestFocus();
    }

    public void moveToCustomerNic(ActionEvent actionEvent) {
        txtCustomerNic.requestFocus();
    }

    public void moveToAddress(ActionEvent actionEvent) {
        txtCustomerAddress.requestFocus();
    }

    public void moveToCustomerLicenseNumber(ActionEvent actionEvent) {
        txtCustomerLicenseNumber.requestFocus();
    }

    public void moveToTelephoneNumber(ActionEvent actionEvent) {
        txtTelephoneNumber.requestFocus();
    }


    public void txtCustomerKeyRelease(KeyEvent keyEvent) {
        btnAddCustomer.setDisable(true);
        Object response = Validation.validate(map,btnAddCustomer);
        if (keyEvent.getCode()==KeyCode.ENTER){
            if (response instanceof TextField){
                TextField error = (TextField) response;
                error.requestFocus();
            }else if (response instanceof Boolean){
                new AddNotifications().sceneNotifications("CONFIRMATION"," Success ",0,1);
            }
        }

    }

}
