package controller;

import javafx.application.Preloader;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class launchPreLoader extends Preloader {
    private Stage preLoarderStage;
    @Override
    public void start(Stage primaryStage) throws Exception {
        this.preLoarderStage=primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("../view/intPreLorder.fxml"));
        Scene scene=new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();






    }

    @Override
    public void handleStateChangeNotification(StateChangeNotification info) {
        if (info.getType()== StateChangeNotification.Type.BEFORE_START){
            preLoarderStage.hide();
        }
    }
}