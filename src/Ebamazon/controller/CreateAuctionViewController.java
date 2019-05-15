package Ebamazon.controller;

import Ebamazon.model.*;
import javafx.animation.AnimationTimer;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class CreateAuctionViewController {
    private CurrentSession currentSession;
    private ArrayList<imageComponentViewController> imageControllers;
    private ArrayList<Image> imageList;
    List<File> imageFiles;
    private ToggleGroup toggleGroup;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField auctionTitleTextField;

    @FXML
    private TextArea auctionDescriptionTextArea;

    @FXML
    private TextField auctionKeywordsTextField;

    @FXML
    private RadioButton bidRadioButton;

    @FXML
    private ToggleGroup Type;

    @FXML
    private RadioButton fixedPriceRadioButton;

    @FXML
    private TextField priceTextField;

    @FXML
    private Button submitButton;

    @FXML
    private VBox imageComponentVBox;

    @FXML
    private Button uploadButton;

    @FXML
    void initialize() {
        assert auctionTitleTextField != null : "fx:id=\"auctionTitleTextField\" was not injected: check your FXML file 'createAuctionView.fxml'.";
        assert auctionDescriptionTextArea != null : "fx:id=\"auctionDescriptionTextArea\" was not injected: check your FXML file 'createAuctionView.fxml'.";
        assert auctionKeywordsTextField != null : "fx:id=\"auctionKeywordsTextField\" was not injected: check your FXML file 'createAuctionView.fxml'.";
        assert bidRadioButton != null : "fx:id=\"bidRadioButton\" was not injected: check your FXML file 'createAuctionView.fxml'.";
        assert Type != null : "fx:id=\"Type\" was not injected: check your FXML file 'createAuctionView.fxml'.";
        assert fixedPriceRadioButton != null : "fx:id=\"fixedPriceRadioButton\" was not injected: check your FXML file 'createAuctionView.fxml'.";
        assert priceTextField != null : "fx:id=\"priceTextField\" was not injected: check your FXML file 'createAuctionView.fxml'.";
        assert submitButton != null : "fx:id=\"submitButton\" was not injected: check your FXML file 'createAuctionView.fxml'.";
        assert imageComponentVBox != null : "fx:id=\"imageComponentVBox\" was not injected: check your FXML file 'createAuctionView.fxml'.";
        assert uploadButton != null : "fx:id=\"uploadButton\" was not injected: check your FXML file 'createAuctionView.fxml'.";
        imageControllers = new ArrayList<>();
        imageList = new ArrayList<>();
        imageFiles = new ArrayList<>();
        toggleGroup = new ToggleGroup();
        //utilizes regex to make sure that only numeric/decimal characters may be entered in price text
        //additionally its limits price to 10 digits before decimal and 2 digits after
        priceTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d{0,8}([\\.]\\d{0,2})?")) {
                    priceTextField.setText(oldValue);
                }
            }
        });
        auctionTitleTextField.lengthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable,
                                Number oldValue, Number newValue) {
                if (newValue.intValue() > oldValue.intValue()) {
                    // Check if the new character is greater than LIMIT
                    if (auctionTitleTextField.getText().length() >= 20) {

                        // if it's 20th character then just setText to previous
                        // one
                        auctionTitleTextField.setText(auctionTitleTextField.getText().substring(0, 20));
                    }
                }
            }
        });
        auctionDescriptionTextArea.lengthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable,
                                Number oldValue, Number newValue) {
                if (newValue.intValue() > oldValue.intValue()) {
                    // Check if the new character is greater than LIMIT
                    if (auctionDescriptionTextArea.getText().length() >= 2048) {

                        // if it's 20th character then just setText to previous
                        // one
                        auctionDescriptionTextArea.setText(auctionDescriptionTextArea.getText().substring(0, 2048));
                    }
                }
            }
        });
        updateloop();
    }

    @FXML
    void submitAuction(ActionEvent event) {
        Auction auction = new Auction();
        if(scrubAuction()) {
            Warning warning = new Warning();
            warning.setSuperUser(null);
            warning.setOrdinaryUser((OrdinaryUser)currentSession.getCurUser());
            warning.setReason("Your Auction, " + auctionTitleTextField.getText() + ", contains a taboo word.");
            warning.insertWarning();
            auction.setKickback(true);
        } else{
            auction.setKickback(false);
        }
        auction.setDescription(auctionDescriptionTextArea.getText());
        auction.setKeywords(assembleAuctionKeywords());
        auction.setTitle(auctionTitleTextField.getText());
        auction.setAuctionImages(assembleAuctionImages());
        auction.setFixed((fixedPriceRadioButton.isSelected()));
        auction.setPrice(BigDecimal.valueOf(Double.parseDouble(priceTextField.getText())));
        OrdinaryUser ou = (OrdinaryUser) currentSession.getCurUser();
        auction.setOrdinaryUser(ou);
        ou.submitAuction(auction);
        clearAll();
    }

    private boolean scrubAuction() {
        boolean tabooFound = false;
        try {
            InputScrubber is = new InputScrubber();
            auctionTitleTextField.setText(is.scrubInput(auctionTitleTextField.getText()));
            if(is.hasTaboo()) {tabooFound = true;}
            auctionDescriptionTextArea.setText(is.scrubInput(auctionDescriptionTextArea.getText()));
            if(is.hasTaboo()) {tabooFound = true;}
            auctionKeywordsTextField.setText(is.scrubInput(auctionKeywordsTextField.getText()));
            // Note: Taboo in keywords does not trigger a kickback
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
        return tabooFound;
    }

    private ArrayList<String> removeTaboo(ArrayList<String> list) {
        String s;
        // Remove taboo words, i.e., all words that contain an asterisk (*)
        for (int i = (list.size() - 1); i >= 0; i--) {
            s = list.get(i);
            if (s.contains("*")) {
                list.remove(s);
            }
        }
        return list;
    }

    @FXML
    void uploadPhotos(ActionEvent event) throws IOException {
        //set a new stage for the file chooser
        Stage stage = new Stage();
        //set up file chooser and its file type filers
        FileChooser fc = new FileChooser();
        fc.setTitle("Choose Images to Upload");
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Images Files", "*.jpg", "*.png", "*.gif", "*.bmp"));
        //get list of images
        List<File> images = fc.showOpenMultipleDialog(stage);
        //store in list of all image files, used to create auction and auction image files
        imageFiles.addAll(images);
        //for each file in images...
        for (File file : images) {
            //convert to jfx image
            Image image = new Image(file.toURI().toString());
            imageList.add(image);
            //create image component view and set its instance variables
            FXMLLoader imageComponentLoader = new FXMLLoader();
            imageComponentLoader.setLocation(getClass().getResource("../view/imageComponentView.fxml"));
            HBox view = imageComponentLoader.load();
            //attach new view to VBox, get reference to controller, set the values in the controller
            imageComponentVBox.getChildren().add(view);
            imageComponentViewController icvc = imageComponentLoader.getController();
            imageControllers.add(icvc);
            icvc.loadImage(image);
            //add the components radio button to toggle group
            icvc.getRadioButton().setToggleGroup(toggleGroup);
        }
    }

    private void updateloop() {
        AnimationTimer at = new AnimationTimer() {
            @Override
            public void handle(long now) {
                //only allow 1 auction type to be selected
                if (fixedPriceRadioButton.isSelected()) bidRadioButton.setSelected(false);
                if (bidRadioButton.isSelected()) fixedPriceRadioButton.setSelected(false);

                //only enable submit button if all fields filled
                if ((fixedPriceRadioButton.isSelected() || bidRadioButton.isSelected()) && !auctionTitleTextField.getText().equals("")
                        && !auctionDescriptionTextArea.getText().equals("") && !auctionKeywordsTextField.getText().equals("")
                        && !priceTextField.getText().equals("") && !(toggleGroup.getSelectedToggle()==null)){
                        submitButton.setDisable(false);
                }
                else {
                    submitButton.setDisable(true);
                }
            }
        };
        at.start();
    }

    //IF ANY INDIVIDUAL KEYWORD IS > 100 CHAR IT WILL THROW SQL ERROR
    private ArrayList<String> parseKeywordInput() {
        String [] auctionKeywords = auctionKeywordsTextField.getText().split(" ");
        return new ArrayList<String>(Arrays.asList(auctionKeywords));
    }

    private ArrayList<AuctionKeyword> assembleAuctionKeywords() {
        ArrayList<AuctionKeyword> keywords = new ArrayList<>();
        ArrayList<String> wordList = parseKeywordInput();
        wordList = removeTaboo(wordList);           // Remove taboo words
        for (String s : wordList) {
            AuctionKeyword ak = new AuctionKeyword();
            ak.setKeyword(s);
            keywords.add(ak);
        }
        return keywords;
    }

    private ArrayList<AuctionImage> assembleAuctionImages() {
        ArrayList<AuctionImage> returnList =  new ArrayList<>();
        for (int i = 0; i < imageControllers.size(); i++){
            AuctionImage ai = new AuctionImage();
            ai.setImage(imageFiles.get(i));
            ai.setDefaultPhoto(imageControllers.get(i).getRadioButton().isSelected());
            ai.setImageNumber(i);
            returnList.add(ai);
            System.out.println(ai.toString());
        }
        return returnList;
    }

    private void clearAll() {
        imageControllers.clear();
        imageFiles.clear();
        imageList.clear();
        auctionKeywordsTextField.clear();
        auctionTitleTextField.clear();
        auctionDescriptionTextArea.clear();
        imageComponentVBox.getChildren().clear();
        toggleGroup = new ToggleGroup();
        priceTextField.clear();
        bidRadioButton.setSelected(false);
        fixedPriceRadioButton.setSelected(false);
    }

    public void setCurrentSession(CurrentSession currentSession) {
        this.currentSession = currentSession;
    }
}
