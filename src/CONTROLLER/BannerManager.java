package CONTROLLER;

import MODEL.User;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;


public class BannerManager {

    HBox banner;
    Image logo = new Image("https://beyond-gaming.net/wp-content/uploads/2017/08/TEST-logo-box.png");

    public BannerManager(DisplayManager dm, User curUser){
        banner = new HBox(50);

        ImageView logoview = new ImageView(logo);
        logoview.setFitHeight(100);
        logoview.setFitWidth(200);

        Label testLabel1 = new Label();
        TextArea testLabel2 = new TextArea();
        Button testLabel3 = new Button();
        Label testLabel4 = new Label();

        testLabel2.setPrefHeight(25);  //sets height of the TextArea to 400 pixels
        testLabel2.setPrefWidth(200);

        testLabel1.setText("Enter Search Info: ");
        testLabel2.setText("Search Here");
        testLabel3.setText("Submit");
        testLabel4.setText("User's name is " + curUser.getName());

        banner.getChildren().addAll(logoview, testLabel1, testLabel2, testLabel3);
    }

    HBox getBanner(){
        return banner;
    }
}
