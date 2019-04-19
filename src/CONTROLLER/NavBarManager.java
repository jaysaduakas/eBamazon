package CONTROLLER;

import MODEL.User;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;



public class NavBarManager {

    VBox navBar;

    public NavBarManager(DisplayManager dm, User curUser){
        navBar = new VBox(50);

        Button testLabel1 = new Button();
        Button testLabel2 = new Button();
        Button testLabel3 = new Button();

        testLabel1.setText("Test1");
        testLabel2.setText("Test2");
        testLabel3.setText("Test3");

        navBar.getChildren().addAll(testLabel1, testLabel2, testLabel3);
    }

    VBox getNavBar(){
        return navBar;
    }
}
