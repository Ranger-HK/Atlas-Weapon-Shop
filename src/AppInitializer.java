import com.sun.javafx.application.LauncherImpl;
import javafx.application.Application;
import javafx.stage.Stage;
import controller.IntPreLorder;

import java.io.IOException;

import static javafx.fxml.FXMLLoader.load;

public class AppInitializer extends Application {

    public static Stage primaryStage=null;
    public static Stage primaryScene=null;

    @Override
    public void init() throws Exception {
        IntPreLorder init=new IntPreLorder();
        init.checkFunction();
    }

    public static void main(String[] args) {

        //launch(args);
        LauncherImpl.launchApplication(AppInitializer.class,controller.launchPreLoader.class,args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        AppInitializer.primaryStage = primaryStage;
        /*primaryStage.setScene(new Scene(load(getClass().getResource("view/loginForm.fxml"))));
        primaryStage.setTitle(" ");
        //primaryStage.setAlwaysOnTop(true);
        primaryStage.show();*/

    }
}