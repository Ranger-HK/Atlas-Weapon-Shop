package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import model.Item;
import model.Supplier;
import util.Validation;
import view.tm.ItemTM;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;

public class AdminItemFormController {
    public AnchorPane contextItemForm;
    public JFXTextField txtItemId;
    public Label lblDate;
    public Label lblTime;
    public JFXTextField txtPrice;
    public JFXTextField txtCategoryId;
    public JFXTextField txtItemName;
    public JFXTextField txtQtyOnHand;
    public TableView<ItemTM> tblItemForm;
    public TableColumn colItemId;
    public TableColumn colCategoryId;
    public TableColumn colModelId;
    public TableColumn colSupplierId;
    public TableColumn colPrice;
    public TableColumn colItemName;
    public TableColumn colQtyOnHand;
    public JFXButton btnClearFiled;
    public JFXButton btnAddItem;
    public JFXButton btnDelete;
    public JFXButton btnUpdate;
    public JFXComboBox<String> cmbCategoryId;
    public JFXComboBox<String> cmbModeId;
    public JFXComboBox<String> cmbSupplierId;


    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap<>();

    Pattern itemId = Pattern.compile("^(I-|i-)[0-9]{3,4}$");
    Pattern Price = Pattern.compile("^[0-9]{3,6}$");


    private void storeValidation() {
        map.put(txtItemId, itemId);
        map.put(txtPrice, Price);
    }


    public void loadCombo() throws SQLException, ClassNotFoundException {
        cmbCategoryId.getItems().setAll(new CategoryServiceController().getAllCategory());
        cmbModeId.getItems().setAll(new ModelServiceController().getAllModel());
        cmbSupplierId.getItems().setAll(new SupplierServiceController().getAllSupplier());


    }

    public void setSupplierData(String supId) throws SQLException, ClassNotFoundException {
        Supplier supplier = new SupplierServiceController().passSupplier(supId);
        if (supplier == null) {

        } else {
            txtItemName.setText(supplier.getSupplyItem());
            txtQtyOnHand.setText(String.valueOf(supplier.getItemQty()));
        }

    }


    public void initialize() {
        storeValidation();
        btnAddItem.setDisable(true);
        loadDateAndTime();
        try {
            loadCombo();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


        cmbSupplierId.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try {
                setSupplierData(newValue);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });

        try {
            setItemToTable();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        btnAddItem.setDisable(true);
        btnDelete.setDisable(true);
        btnUpdate.setDisable(true);
    }


    private void loadDateAndTime() {
        Date date = new Date();
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MMMM-dd");
        lblDate.setText(f.format(date));

        Timeline time = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            LocalTime currentTime = LocalTime.now();
            lblTime.setText(
                    currentTime.getHour() + " : " + currentTime.getMinute() + " : " + currentTime.getSecond()
            );
        }),
                new KeyFrame(Duration.seconds(1))
        );
        time.setCycleCount(Animation.INDEFINITE);
        time.play();
    }

    public void setItemToTable() throws SQLException, ClassNotFoundException {
        ObservableList<ItemTM> item = new ItemServiceController().getItemList();
        colItemId.setCellValueFactory(new PropertyValueFactory<>("itemId"));
        colCategoryId.setCellValueFactory(new PropertyValueFactory<>("categoryId"));
        colModelId.setCellValueFactory(new PropertyValueFactory<>("modelId"));
        colSupplierId.setCellValueFactory(new PropertyValueFactory<>("supplierId"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colItemName.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        colQtyOnHand.setCellValueFactory(new PropertyValueFactory<>("qtyOnHand"));

        tblItemForm.setItems(item);
    }


    public void btnClearFiledOnAction(ActionEvent actionEvent) {
        clearField();
    }

    public void btnAddItemOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        Item item = new Item(
                txtItemId.getText(),
                cmbCategoryId.getValue(),
                cmbModeId.getValue(),
                cmbSupplierId.getValue(),
                Double.parseDouble(txtPrice.getText()),
                txtItemName.getText(),
                Integer.parseInt(txtQtyOnHand.getText())
        );

        if (new ItemServiceController().saveItem(item)) {

            new AddNotifications().sceneNotifications("CONFIRMATION", "Item Save Completed..", 0, 1);
            setItemToTable();
            btnAddItem.setDisable(true);

        } else {

            new AddNotifications().sceneNotifications("WARNING", "Try Again..", 0, 5);
        }
        clearField();
    }


    public void btnDeleteItemOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        ItemTM item = tblItemForm.getSelectionModel().getSelectedItem();
        String items = item.getItemId();

        if (new ItemServiceController().deleteItem(items)) {
            new AddNotifications().sceneNotifications("CONFIRMATION", "Item Delete Completed..", 0, 3);
            setItemToTable();
            btnDelete.setDisable(true);

        } else {
            new AddNotifications().sceneNotifications("WARNING", "Try Again..", 0, 5);
        }
        clearField();

    }


    public void btnUpdateOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        ItemTM item = new ItemTM(
                txtItemId.getText(),
                cmbCategoryId.getValue(),
                cmbModeId.getValue(),
                cmbSupplierId.getValue(),
                Double.parseDouble(txtPrice.getText()),
                txtItemName.getText(),
                Integer.parseInt(txtQtyOnHand.getText())
        );

        if (new ItemServiceController().updateItem(item)) {
            new AddNotifications().sceneNotifications("CONFIRMATION", "Item Update Completed..", 0, 1);
            setItemToTable();
            btnUpdate.setDisable(true);

        } else {
            new AddNotifications().sceneNotifications("WARNING", "Try Again..", 0, 5);

        }
        clearField();

    }


    private void clearField() {
        txtItemId.clear();
        txtItemName.clear();
        txtQtyOnHand.clear();
        txtPrice.clear();
        cmbCategoryId.setValue("");
        cmbModeId.setValue("");
        cmbSupplierId.setValue("");
        txtItemId.requestFocus();
        cmbCategoryId.getSelectionModel().clearSelection();
        cmbModeId.getSelectionModel().clearSelection();
        cmbSupplierId.getSelectionModel().clearSelection();
    }


    public void moveToItemName(ActionEvent actionEvent) {
        txtItemName.requestFocus();

    }

    public void moveToCategoryId(ActionEvent actionEvent) {
        txtCategoryId.requestFocus();

    }


    public void moveToQtyOnHand(ActionEvent actionEvent) {
        txtQtyOnHand.requestFocus();

    }

    public void mouseOnAction(MouseEvent mouseEvent) {
        try {
            ItemTM item = null;
            item = tblItemForm.getSelectionModel().getSelectedItem();
            txtItemId.setText(item.getItemId());
            cmbCategoryId.setValue(item.getCategoryId());
            cmbModeId.setValue(item.getModelId());
            cmbSupplierId.setValue(item.getSupplierId());
            txtPrice.setText("" + item.getPrice());
            txtItemName.setText(item.getItemName());
            txtQtyOnHand.setText("" + item.getQtyOnHand());
            btnUpdate.setDisable(false);
            btnDelete.setDisable(false);
        } catch (Exception e) {
        }
    }

    public void txtAddItemKeyRelease(KeyEvent keyEvent) {
        btnAddItem.setDisable(true);
        Object response = Validation.validate(map, btnAddItem);
        if (keyEvent.getCode() == KeyCode.ENTER) {
            if (response instanceof TextField) {
                TextField error = (TextField) response;
                error.requestFocus();
            } else if (response instanceof Boolean) {
                new AddNotifications().sceneNotifications("CONFIRMATION", " Success ", 0, 1);

            }
        }

    }

}
