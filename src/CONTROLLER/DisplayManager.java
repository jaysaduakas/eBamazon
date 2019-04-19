package CONTROLLER;

import MODEL.User;
import javafx.scene.Scene;
import javafx.scene.SubScene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

enum Views {AUCTION_FEED, AUCTION, USER_SETTINGS, COMPLAINT_USER, TRANSACTION_HISTORY, INBOX, MESSAGE };


public class DisplayManager {
    private final int HEIGHT = 800;
    private final int WIDTH = 800;

    private User curUser;

    private Stage mainStage;
    private Scene scene;
    private BorderPane bp;
    private SubScene curDisplay;

    private BannerManager bm;
    private NavBarManager nbm;

    public DisplayManager(){
        curUser = new User();

        mainStage = new Stage();
        bp = new BorderPane();
        bm = new BannerManager(this, curUser);
        nbm = new NavBarManager(this, curUser);
        scene = new Scene(bp, WIDTH, HEIGHT);

        bp.setTop(bm.getBanner());
        bp.setLeft(nbm.getNavBar());

        mainStage.setScene(scene);
    }

    public Stage getMainStage(){
        return mainStage;
    }
}
