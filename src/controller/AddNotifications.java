package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

import java.util.Objects;

public class AddNotifications {

    public void sceneNotifications(String title, String txt, int showMode, int image) {

        Image img;
        switch (image) {
            case 1:
                img = new Image("/assets/icon/Save5.jpeg");
                break;
            case 2:
                img = new Image("/assets/icon/Close2.jpeg");
                break;
            case 3:
                img = new Image("/assets/icon/Delete3.jpeg");
                break;
            case 4:
                img = new Image("/assets/icon/Lock4.jpeg");
                break;
            case 5:
                img = new Image("/assets/icon/Warning1.jpeg");
                break;
            default:
                img = new Image("/assets/icon/Unlock6.jpeg");
                break;
        }

        Objects.requireNonNull(Notifications.class.getResource("/style/style.css")).toExternalForm();
        Notifications notificationBuilder = Notifications.create();
        notificationBuilder.title(title)
                .text(txt)
                .graphic(new ImageView(img))
                .hideAfter(Duration.seconds(2))
                .position(Pos.BOTTOM_RIGHT)
                .onAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {

                    }
                });

        switch (showMode) {
            case 1:
                notificationBuilder.darkStyle();
                notificationBuilder.showConfirm();
                break;
            case 2:
                notificationBuilder.darkStyle();
                notificationBuilder.showInformation();
                break;
            case 3:
                notificationBuilder.darkStyle();
                notificationBuilder.showError();
                break;
            case 4:
                notificationBuilder.darkStyle();
                notificationBuilder.showWarning();
                break;
            default:
                notificationBuilder.darkStyle();
                notificationBuilder.show();
                break;
        }

    }
}
