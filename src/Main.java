import CONTROLLER.DisplayManager;
import MODEL.User;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        User user = new User();
        DisplayManager dm = new DisplayManager();
        primaryStage = dm.getMainStage();
        primaryStage.show();
    }
}
