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
import view.tm.RecordTM;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

public class RecordFormController {
    public Label lblDate;
    public Label lblTime;
    public TableColumn colOrderId;
    public TableColumn colItemId;
    public TableColumn colCustomerId;
    public TableColumn colOrderQty;
    public TableColumn colOrderDate;
    public TableColumn colTotal;
    public TableView tblRecord;
    public JFXTextField txtSearch;


    public void initialize(){
        loadDateAndTime();
        try {
            setRecordToTable();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


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

    public void setRecordToTable() throws SQLException, ClassNotFoundException {
        ObservableList<RecordTM> record = new RecordServiceController().getRecordToTable();
        colOrderId.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        colItemId.setCellValueFactory(new PropertyValueFactory<>("itemId"));
        colCustomerId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        colOrderQty.setCellValueFactory(new PropertyValueFactory<>("orderQty"));
        colOrderDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));

        tblRecord.setItems(record);
    }


    public void searchOnAction(KeyEvent keyEvent) throws SQLException, ClassNotFoundException {
        List<RecordTM> items = RecordServiceController.searchRecord(txtSearch.getText());
        ObservableList<RecordTM> tableData = FXCollections.observableArrayList();
        for (RecordTM item : items) {
            tableData.add(new RecordTM(
                    item.getOrderId(),
                    item.getItemId(),
                    item.getCustomerId(),
                    item.getOrderQty(),
                    item.getDate(),
                    item.getTotal()

            ));

        }
        tblRecord.setItems(tableData);

    }
}



