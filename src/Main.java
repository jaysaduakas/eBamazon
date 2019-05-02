import Ebamazon.controller.BannerController;
import Ebamazon.controller.NavBarController;
import Ebamazon.model.CurrentSession;
import Ebamazon.model.User;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    private Stage primaryStage;
    private BorderPane bp;


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        this.primaryStage = primaryStage;
        CurrentSession currentSession = new CurrentSession();
        bp = new BorderPane();


        showMainView(currentSession);
        //DisplayManager dm = new DisplayManager(user);
        //primaryStage = dm.getMainStage();
        //primaryStage.show();
    }

    private void showMainView(CurrentSession currentSession) throws IOException {
        //intialize banner controller and view
        FXMLLoader bannerLoader = new FXMLLoader();
        bannerLoader.setLocation(Main.class.getResource("Ebamazon/view/bannerView.fxml"));
        Node banner = bannerLoader.load();
        BannerController bc = bannerLoader.getController();
        bc.setCurrentSession(currentSession);
        bc.setParent(bp);

        //initialize navbar controller and view
        FXMLLoader navBarLoader = new FXMLLoader();
        navBarLoader.setLocation(Main.class.getResource("Ebamazon/view/navBarView.fxml"));
        Node navBar = navBarLoader.load();
        NavBarController nbc = navBarLoader.getController();
        nbc.setParent(bp);
        nbc.setCurrentSession(currentSession);

        //intialize homeview controller and view
        FXMLLoader homeViewLoader = new FXMLLoader();
        homeViewLoader.setLocation(Main.class.getResource("Ebamazon/view/homeView.fxml"));
        Node homeview = homeViewLoader.load();
        bp.setCenter(homeview);
        bp.setAlignment(bp.getCenter(), Pos.TOP_CENTER);

        //set navbar for banner
        bc.setNavBarController(nbc);

        bp.setTop(banner);
        bp.setLeft(navBar);

        Scene scene = new Scene(bp);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
