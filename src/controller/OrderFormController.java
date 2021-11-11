package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.Item;
import util.Validation;
import view.tm.CartTM;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;

public class OrderFormController {
    public Label lblDate;
    public Label lblTime;
    public JFXComboBox<String> cmbItemId;
    public JFXTextField txtCategoryId;
    public JFXTextField txtModelId;
    public JFXTextField txtPrice;
    public JFXTextField txtItemName;
    public JFXTextField txtQtyOnHand;
    public JFXTextField txtQuantity;
    public TableView tblOrder;
    public TableColumn colItemId;
    public TableColumn colCategoryId;
    public TableColumn colModelId;
    public TableColumn colPrice;
    public TableColumn colItemName;
    public TableColumn colQuantity;
    public TableColumn colTotal;
    public Label lblTotal;
    public AnchorPane contextOrder;
    public JFXButton btnAddToCart;


    static ObservableList<CartTM> list = FXCollections.observableArrayList();

    int cartSelectedRowRemove=-1;

    public static ObservableList getCart(){
        return list;
    }


    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap<>();

    Pattern Qty = Pattern.compile("^[0-9]{1,5}$");


    private void storeValidation(){
        map.put(txtQuantity,Qty);
    }


    public void loadCombo() throws SQLException, ClassNotFoundException {
        cmbItemId.getItems().setAll(new ItemServiceController().getAllItem());
    }

    public void setItemData(String itemId) throws SQLException, ClassNotFoundException {
            Item item = new ItemServiceController().passItem(itemId);
            if (item==null){

                new Alert(Alert.AlertType.WARNING,"Empty ResultSet");

            }else {
                txtCategoryId.setText(item.getCategoryId());
                txtModelId.setText(item.getModelId());
                txtPrice.setText(String.valueOf(item.getPrice()));
                txtItemName.setText(item.getItemName());
                txtQtyOnHand.setText(String.valueOf(item.getQtyOnHand()));
            }


        }



    public void addToCartOnAction(ActionEvent actionEvent) {
        String value = cmbItemId.getValue();
        String categoryId = txtCategoryId.getText();
        String modelId = txtModelId.getText();
        double price = Double.parseDouble(txtPrice.getText());
        String itemName = txtItemName.getText();
        int qtyOnHand = Integer.parseInt(txtQtyOnHand.getText());
        int quantity = Integer.parseInt(txtQuantity.getText());
        double total = price * quantity ;

        if(qtyOnHand< quantity) {
           new Alert(Alert.AlertType.WARNING, "Invalid QTY").show();


            return;
        }


        CartTM temp = new CartTM(
                value,
                categoryId,
                modelId,
                price,
                itemName,
                quantity,
                total
        );

        int rowNumber = isExists(temp);

        if (rowNumber==-1){
            list.add(temp);

        }else {
            CartTM tm = list.get(rowNumber);
            CartTM newTm = new CartTM(
                    tm.getItemId(),
                    tm.getCategoryId(),
                    tm.getModelId(),
                    tm.getPrice(),
                    tm.getItemName(),
                    tm.getQuantity() + quantity,
                    total + tm.getTotal()
            );

            if (qtyOnHand < tm.getQuantity() + quantity) {
                new Alert(Alert.AlertType.WARNING, "Invalid QTY").show();

                return;
            }

            list.remove(rowNumber);
            list.add(newTm);

        }

        tblOrder.setItems(list);
        calculateCost();
        clearField();
        btnAddToCart.setDisable(true);

    }


    private int isExists(CartTM tm){
        for (int i = 0; i < list.size(); i++){
            if (tm.getItemId().equals(list.get(i).getItemId())){
                return i;
            }
        }
        return -1;
    }

    void calculateCost(){
        double ttl = 0;
        for (CartTM tm:list
             ) {
            ttl+=tm.getTotal();

        }

        String formatTotal = new DecimalFormat("0.00").format(ttl);
        lblTotal.setText(formatTotal+" /=");

    }


    public void deleteOnAction(ActionEvent actionEvent) {
       if (cartSelectedRowRemove==-1){
           new AddNotifications().sceneNotifications("WARNING","Please Select a row",0,5);


       }else{

           new AddNotifications().sceneNotifications("CONFIRMATION","Delete Completed..",0,3 );


           list.remove(cartSelectedRowRemove);
           calculateCost();
           tblOrder.refresh();
       }
       clearField();



    }

    public void orderBtnOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/OrderDetailsForm.fxml");
        Parent load = FXMLLoader.load(resource);
        Scene scene=new Scene(load);
        Stage stage=new Stage();
        stage.setScene(scene);
        stage.setTitle("Order Details");
        stage.show();

    }

    public void addCustomerOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/AddCustomerForm.fxml");
        Parent load = FXMLLoader.load(resource);
        Scene scene=new Scene(load);
        Stage stage=new Stage();
        stage.setScene(scene);
        stage.setTitle("Add Customers");
        stage.show();
    }

    public void cancelOrderOnAction(ActionEvent actionEvent) {
        lblTotal.setText("");
        list.clear();
        clearField();
        tblOrder.refresh();


    }

    public void initialize(){
        storeValidation();
        btnAddToCart.setDisable(true);

        colItemId.setCellValueFactory(new PropertyValueFactory<>("itemId"));
        colCategoryId.setCellValueFactory(new PropertyValueFactory<>("categoryId"));
        colModelId.setCellValueFactory(new PropertyValueFactory<>("modelId"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colItemName.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        colQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));


        loadDateAndTime();
        try {
            loadCombo();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


        cmbItemId.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try {
                setItemData(newValue);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });

        tblOrder.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            cartSelectedRowRemove = (int) newValue;
        });
        list.clear();
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


    private void clearField(){
        txtCategoryId.clear();
        txtModelId.clear();
        txtPrice.clear();
        txtItemName.clear();
        txtQtyOnHand.clear();
        txtQuantity.clear();
        tblOrder.getSelectionModel().clearSelection();
        cmbItemId.setValue("");
        cmbItemId.getSelectionModel().clearSelection();
        cmbItemId.requestFocus();

    }

    public void moveToModelId(ActionEvent actionEvent) {
        txtModelId.requestFocus();
    }

    public void moveToPrice(ActionEvent actionEvent) {
        txtPrice.requestFocus();
    }

    public void moveToItemName(ActionEvent actionEvent) {
        txtItemName.requestFocus();
    }

    public void moveToQtyOnHand(ActionEvent actionEvent) {
        txtQtyOnHand.requestFocus();
    }

    public void moveToQuantity(ActionEvent actionEvent) {
        txtQuantity.requestFocus();

    }


    public void txtManageUserKeyRelease(KeyEvent keyEvent) {
        btnAddToCart.setDisable(true);
        Object response = Validation.validate(map,btnAddToCart);
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
