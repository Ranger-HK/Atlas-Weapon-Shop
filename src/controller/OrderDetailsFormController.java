package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.Customer;
import model.Order;
import model.OrderDetails;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanArrayDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import view.tm.CartTM;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class OrderDetailsFormController {
    public Label lblDate;
    public Label lblTime;
    public TableView<CartTM> tblOrderDetails;
    public TableColumn colItemId;
    public TableColumn colItemName;
    public TableColumn colQuantity;
    public TableColumn colTotal;
    public Label lblOrderId;
    public JFXTextField txtCustomerName;
    public JFXComboBox<String> cmbCustomerId;
    public JFXTextField txtCustomerLicenseNumber;
    public JFXTextField txtCustomerAddress;
    public JFXButton btnConformOrder;
    public JFXTextField lblTotalAmount;
    public AnchorPane contextOrderDetails;
    public JFXTextField txtAmountPaid;
    public JFXTextField txtRemainingBalance;



    static ObservableList<CartTM> parentCartObList = FXCollections.observableArrayList();

    double netPrice;

    public void initialize(){
        loadDateAndTime();

        colItemId.setCellValueFactory(new PropertyValueFactory<>("itemId"));
        colItemName.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        colQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));

        try {
            setOrderId();
            loadCombo();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        cmbCustomerId.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try {
                setCustomerData(newValue);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });
        Bindings.bindContentBidirectional(parentCartObList,OrderFormController.getCart());
        tblOrderDetails.setItems(parentCartObList);


        for (CartTM tm : tblOrderDetails.getItems()){
            netPrice+=tm.getTotal();
        }
        lblTotalAmount.setText(new DecimalFormat("0.00").format(netPrice));


    }


    public void loadCombo() throws SQLException, ClassNotFoundException {
        cmbCustomerId.getItems().setAll(new CustomerServiceController().getAllCustomer());
    }

    public void setCustomerData(String customerId) throws SQLException, ClassNotFoundException {
        Customer customer = new CustomerServiceController().passCustomer(customerId);
        txtCustomerName.setText(customer.getCustomerName());
        txtCustomerAddress.setText(customer.getCustomerAddress());
        txtCustomerLicenseNumber.setText(customer.getCustomerLicenseNumber());
    }



    public void conformOrderOnAction(ActionEvent actionEvent) throws SQLException {
        Stage stage = (Stage) btnConformOrder.getScene().getWindow();stage.close();
        ArrayList<OrderDetails>items = new ArrayList<>();
        for (CartTM tempTm: parentCartObList
             ) {
            items.add(
                    new OrderDetails(
                           tempTm.getItemId(),
                           lblOrderId.getText(),
                           tempTm.getQuantity()

                    ));
        }
        Order order = new Order(
                lblOrderId.getText(),
                cmbCustomerId.getValue(),
                lblDate.getText(),
                items
        );
        if (new OrderServiceController().placeOrder(order)) {
            setOrderId();
            try {
                JasperDesign design = JRXmlLoader.load(this.getClass().getResourceAsStream("../view/reports/OrderReception.jrxml"));
                JasperReport compileReport = JasperCompileManager.compileReport(design);
                double totalAmount = Double.parseDouble(lblTotalAmount.getText());
                double amountPaid = Double.parseDouble(txtAmountPaid.getText());
                double remainingBalance =Double.parseDouble(txtRemainingBalance.getText());
                HashMap map2 = new HashMap();
                map2.put("Total Amount",totalAmount);
                map2.put("Amount Paid",amountPaid);
                map2.put("Remaining Balance",remainingBalance);

                ObservableList<CartTM> items1 = tblOrderDetails.getItems();

                JasperPrint jasperPrint = JasperFillManager.fillReport(compileReport, map2, new JRBeanArrayDataSource(items1.toArray()));
                JasperViewer.viewReport(jasperPrint, false);
            } catch (JRException e) {
                e.printStackTrace();
            }

            new AddNotifications().sceneNotifications("CONFIRMATION", "Order Successful..", 0, 1);
        }

        else{
            new AddNotifications().sceneNotifications("Warning","Try Again..",0,5);

        }

    }

    public void amountPaidOnAction(ActionEvent actionEvent) {
        int AmountPaidText = Integer.parseInt(txtAmountPaid.getText());
        txtRemainingBalance.setText(new DecimalFormat("0.00").format(AmountPaidText-netPrice));
    }


    public void moveToCustomerAddress(ActionEvent actionEvent) {
        txtCustomerAddress.requestFocus();
    }

    public void moveToCustomerLicenseNumber(ActionEvent actionEvent) {
        txtCustomerLicenseNumber.requestFocus();
    }

    public void moveToAmountPaid(ActionEvent actionEvent) {
        txtAmountPaid.requestFocus();

    }
    private void setOrderId(){
        try {
            lblOrderId.setText(new OrderServiceController().getOrderID());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void loadDateAndTime() {
        Date date = new Date();
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
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


}
