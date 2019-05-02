package Ebamazon.controller;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class AuctionComponentViewController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ImageView auctionImage;

    @FXML
    void initialize() {
        assert auctionImage != null : "fx:id=\"auctionImage\" was not injected: check your FXML file 'auctionComponentView.fxml'.";
    }

    public void updateDisplayImage(Image image){
        auctionImage.setImage(image);
    }

}

