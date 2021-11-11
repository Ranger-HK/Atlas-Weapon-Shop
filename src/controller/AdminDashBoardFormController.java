package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;

public class AdminDashBoardFormController {

    public AnchorPane contextDashBoard;
    public ToggleButton btnUserManage;
    public ToggleButton btnAddItem;
    public ToggleButton btnAddCategoryAndModel;
    public ToggleButton btnSupplier;
    public ToggleButton btnSystemReport;
    public ToggleButton btnLogOut;


    public void userManageOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/ManageUserForm.fxml");
        Parent load = FXMLLoader.load(resource);
        contextDashBoard.getChildren().clear();
        contextDashBoard.getChildren().add(load);

    }

    public void addItemOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/AdminItemForm.fxml");
        Parent load = FXMLLoader.load(resource);
        contextDashBoard.getChildren().clear();
        contextDashBoard.getChildren().add(load);

    }

    public void addCategoryAndModelOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/AddCategoryAndModel.fxml");
        Parent load = FXMLLoader.load(resource);
        contextDashBoard.getChildren().clear();
        contextDashBoard.getChildren().add(load);

    }

    public void supplierOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/SupplierForm.fxml");
        Parent load = FXMLLoader.load(resource);
        contextDashBoard.getChildren().clear();
        contextDashBoard.getChildren().add(load);

    }


    public void systemReportOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/SystemReportForm.fxml");
        Parent load = FXMLLoader.load(resource);
        contextDashBoard.getChildren().clear();
        contextDashBoard.getChildren().add(load);

    }

    public void logOutOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/LoginForm.fxml");
        Parent load = FXMLLoader.load(resource);
        Stage window = (Stage) contextDashBoard.getScene().getWindow();
        window.setScene(new Scene(load));

    }
}
