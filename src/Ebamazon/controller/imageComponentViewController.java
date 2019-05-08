package Ebamazon.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

public class imageComponentViewController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private HBox imageComponentView;

    @FXML
    private ImageView imageView;

    @FXML
    private RadioButton radioButton;

    @FXML
    void initialize() {
        assert imageComponentView != null : "fx:id=\"imageComponentView\" was not injected: check your FXML file 'imageComponentView.fxml'.";
        assert imageView != null : "fx:id=\"imageView\" was not injected: check your FXML file 'imageComponentView.fxml'.";
        assert radioButton != null : "fx:id=\"radioButton\" was not injected: check your FXML file 'imageComponentView.fxml'.";

    }

    public HBox getImageComponentView() {
        return imageComponentView;
    }
    public void loadImage(Image image){
        imageView.setImage(image);
    }

    public void setImageComponentView(HBox imageComponentView) {
        this.imageComponentView = imageComponentView;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

    public RadioButton getRadioButton() {
        return radioButton;
    }

    public void setRadioButton(RadioButton radioButton) {
        this.radioButton = radioButton;
    }
}
