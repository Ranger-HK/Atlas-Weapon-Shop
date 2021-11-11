package controller;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.User;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;

public class LoginFormController {
    public JFXTextField userNameContext;
    public JFXPasswordField passwordContext;
    public AnchorPane contextLogin;


    public void openAdminOrHelperForm(ActionEvent actionEvent) throws IOException, SQLException, ClassNotFoundException {
        User user = new User(userNameContext.getText(),passwordContext.getText());
        String login = new UserServiceController().login(user);

        if (login.equals("Admin")) {
            URL resource = getClass().getResource("../view/AdminDashBoardForm.fxml");
            Parent load = FXMLLoader.load(resource);
            Stage window = (Stage) contextLogin.getScene().getWindow();
            window.setScene(new Scene(load));
            window.centerOnScreen();

        }else if (login.equals("Helper")) {
            URL resource = getClass().getResource("../view/HelperDashBoard.fxml");
            Parent load = FXMLLoader.load(resource);
            Stage window = (Stage) contextLogin.getScene().getWindow();
            window.setScene(new Scene(load));
            window.centerOnScreen();

        }else {
            new AddNotifications().sceneNotifications("Warning","Invalid UserName & Password",0,5);
        }

    }


    public void goToPasswordOnAction(ActionEvent actionEvent) {
        passwordContext.requestFocus();

    }

}
