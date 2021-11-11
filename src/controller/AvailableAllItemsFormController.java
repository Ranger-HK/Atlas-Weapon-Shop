package controller;

import com.jfoenix.controls.JFXTextField;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.util.Duration;
import view.tm.ItemTM;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

public class AvailableAllItemsFormController {
    public Label lblDate;
    public Label lblTime;
    public TableView<ItemTM> tblAvailableItem;
    public TableColumn colPrice;
    public TableColumn colItemId;
    public TableColumn colItemName;
    public TableColumn colQytOnHand;
    public JFXTextField txtSearch;
    public TableColumn colModel;
    public TableColumn colCategory;
    public Label lblItem;
    public Label lblCustomer;
    public Label lblSupplier;



    public void initialize() throws SQLException, ClassNotFoundException {
        loadDateAndTime();
        setDataToTable();
        lblCustomer.setText(new AvailableItemServiceController().getCountOfCustomers());
        lblItem.setText(new AvailableItemServiceController().getCountOfItems());
        lblSupplier.setText(new AvailableItemServiceController().getCountOfSupplier());
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





    public void searchOn(KeyEvent keyEvent) throws SQLException, ClassNotFoundException {
        List<ItemTM> items = AvailableItemServiceController.searchItem(txtSearch.getText());
        ObservableList<ItemTM> tableData = FXCollections.observableArrayList();
        for (ItemTM item : items) {
            tableData.add(new ItemTM(
                  item.getItemId(),
                  item.getCategoryId(),
                  item.getModelId(),
                  item.getPrice(),
                  item.getItemName(),
                  item.getQtyOnHand()
            ));

        }
        tblAvailableItem.setItems(tableData);

    }


    public void setDataToTable() throws SQLException, ClassNotFoundException {
        ObservableList<ItemTM>items= new AvailableItemServiceController().getItemToDashboard();

        colItemId.setCellValueFactory(new PropertyValueFactory<>("itemId"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colItemName.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        colQytOnHand.setCellValueFactory(new PropertyValueFactory<>("qtyOnHand"));
        colCategory.setCellValueFactory(new PropertyValueFactory<>("categoryId"));
        colModel.setCellValueFactory(new PropertyValueFactory<>("modelId"));


        tblAvailableItem.setItems(items);
    }
}
