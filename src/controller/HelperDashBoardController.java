package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;

public class HelperDashBoardController {
    public AnchorPane contextHelperDashBoard;
    public ToggleButton btnAvailableItems;
    public ToggleButton btnOrder;
    public ToggleGroup toggleable;
    public ToggleButton btnTrainingGround;
    public ToggleButton btnLogOut;
    public ToggleButton btnAvailableAllItems;
    public ToggleButton btnRecord;

    public void btnAvailableItemsOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/ShopAvailableItemsForm.fxml");
        Parent load = FXMLLoader.load(resource);
        contextHelperDashBoard.getChildren().clear();
        contextHelperDashBoard.getChildren().add(load);
    }

    public void btnOrderOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/OrderForm.fxml");
        Parent load = FXMLLoader.load(resource);
        contextHelperDashBoard.getChildren().clear();
        contextHelperDashBoard.getChildren().add(load);
    }


    public void btnTrainingGroundOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/TrainingGroundForm.fxml");
        Parent load = FXMLLoader.load(resource);
        contextHelperDashBoard.getChildren().clear();
        contextHelperDashBoard.getChildren().add(load);
    }

    public void btnLogOutOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/LoginForm.fxml");
        Parent load = FXMLLoader.load(resource);
        Stage window = (Stage) contextHelperDashBoard.getScene().getWindow();
        window.setScene(new Scene(load));


    }

    public void AvailableAllItemsOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/AvailableAllItemsForm.fxml");
        Parent load = FXMLLoader.load(resource);
        contextHelperDashBoard.getChildren().clear();
        contextHelperDashBoard.getChildren().add(load);
    }

    public void recordOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/RecordForm.fxml");
        Parent load = FXMLLoader.load(resource);
        contextHelperDashBoard.getChildren().clear();
        contextHelperDashBoard.getChildren().add(load);
    }
}
