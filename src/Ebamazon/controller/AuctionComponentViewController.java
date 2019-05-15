package Ebamazon.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import Ebamazon.model.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.shape.Circle;

import javax.management.StringValueExp;

public class AuctionComponentViewController {

    private BigDecimal minPrice;
    private AuctionResult auctionResult;
    private CurrentSession currentSession;
    //only needs to be set in MyAuctions version
    private BorderPane parent;
    private Auction auction;


    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane auctionComponent;

    @FXML
    private ImageView auctionImage;

    @FXML
    private Label title;

    @FXML
    private Label creator;

    @FXML
    private Label date;

    @FXML
    private Label desc;

    @FXML
    private Circle liveCircle;

    @FXML
    private Label keywords;

    @FXML
    private TextField bidBox;

    @FXML
    private Label fixedLabel;

    @FXML
    private Button bidButton;

    @FXML
    private Label price;

    @FXML
    private Button viewBidsButton;

    @FXML
    void bid(ActionEvent event) {
        Bid b = new Bid();
        b.setAuction(auctionResult);
        b.setOrdinaryUser((OrdinaryUser)currentSession.getCurUser());
        b.setWinningBid(false);
        if (bidBox.getText().equals("") || auctionResult.isFixed()){
            b.setAmount(minPrice);
            if (b.getOrdinaryUser().makeBid(b)) disableBidding();
        }
        else {
            int compareValue = (BigDecimal.valueOf(Double.parseDouble(bidBox.getText())).compareTo(minPrice));
            if ((compareValue >= 0)) {
                b.setAmount(BigDecimal.valueOf(Double.parseDouble(bidBox.getText())));
                if (b.getOrdinaryUser().makeBid(b)) disableBidding();
            }
        }
    }

    @FXML
    void viewBids(ActionEvent event) throws IOException {
        FXMLLoader viewBidsLoader = new FXMLLoader();
        viewBidsLoader.setLocation(getClass().getResource("../view/sellItemView.fxml"));
        AnchorPane view = viewBidsLoader.load();
        SellItemViewController sivc = viewBidsLoader.getController();
        sivc.setAuction(auction);
        sivc.setCurrentSession(currentSession);
        sivc.setParent(parent);
        parent.setCenter(view);
    }



    @FXML
    void initialize() {
        assert auctionComponent != null : "fx:id=\"auctionComponent\" was not injected: check your FXML file 'auctionComponentView.fxml'.";
        assert auctionImage != null : "fx:id=\"auctionImage\" was not injected: check your FXML file 'auctionComponentView.fxml'.";
        assert title != null : "fx:id=\"title\" was not injected: check your FXML file 'auctionComponentView.fxml'.";
        assert creator != null : "fx:id=\"creator\" was not injected: check your FXML file 'auctionComponentView.fxml'.";
        assert date != null : "fx:id=\"date\" was not injected: check your FXML file 'auctionComponentView.fxml'.";
        assert desc != null : "fx:id=\"desc\" was not injected: check your FXML file 'auctionComponentView.fxml'.";
        assert liveCircle != null : "fx:id=\"liveCircle\" was not injected: check your FXML file 'auctionComponentView.fxml'.";
        assert keywords != null : "fx:id=\"keywords\" was not injected: check your FXML file 'auctionComponentView.fxml'.";
        assert bidBox != null : "fx:id=\"bidBox\" was not injected: check your FXML file 'auctionComponentView.fxml'.";
        assert fixedLabel != null : "fx:id=\"fixedLabel\" was not injected: check your FXML file 'auctionComponentView.fxml'.";
        assert bidButton != null : "fx:id=\"bidButton\" was not injected: check your FXML file 'auctionComponentView.fxml'.";
        assert price != null : "fx:id=\"price\" was not injected: check your FXML file 'auctionComponentView.fxml'.";
        bidBox.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d{0,10}([\\.]\\d{0,2})?")) {
                    bidBox.setText(oldValue);
                }
            }
        });
    }

    public void updateDisplayImage(Image image){
        auctionImage.setImage(image);
    }

    public AnchorPane getAuctionComponent() {
        return auctionComponent;
    }

    public ImageView getAuctionImage() {
        return auctionImage;
    }

    public Label getTitle() {
        return title;
    }

    public Label getCreator() {
        return creator;
    }

    public Label getDate() {
        return date;
    }

    public Label getDesc() {
        return desc;
    }

    public Label getKeywords() {
        return keywords;
    }

    public TextField getBidBox() {
        return bidBox;
    }

    public Button getBidButton() {
        return bidButton;
    }

    public Label getPrice() {
        return price;
    }

    public BigDecimal getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(BigDecimal minPrice) {
        this.minPrice = minPrice;
    }

    public AuctionResult getAuctionResult() {
        return auctionResult;
    }

    public void setAuctionResult(AuctionResult auctionResult) {
        this.auctionResult = auctionResult;
    }

    public CurrentSession getCurrentSession() {
        return currentSession;
    }

    public void setCurrentSession(CurrentSession currentSession) {
        this.currentSession = currentSession;
    }

    public Circle getLiveCircle() {
        return liveCircle;
    }

    public void setLiveCircle(Circle liveCircle) {
        this.liveCircle = liveCircle;
    }

    public Label getFixedLabel() {
        return fixedLabel;
    }

    public void setFixedLabel(Label fixedLabel) {
        this.fixedLabel = fixedLabel;
    }

    public void setUpAuction(Auction a, CurrentSession cs){
        getTitle().setText(a.getTitle());
        getCreator().setText(a.getOrdinaryUser().getUsername());
        getDesc().setText(a.getDescription());
        if (a.getDateTimeConfirmed()!=null){
            getDate().setText(a.getDateTimeConfirmed().toString());
        }else{
            getDate().setText("Auction Is Not Yet Confirmed by SuperUser");
        }
        String price = "Price: " + a.getPrice();
        BigDecimal friendDiscount = (a.getPrice().multiply(BigDecimal.valueOf(0.05)));
        BigDecimal taxOnPrice = (a.getPrice().multiply(BigDecimal.valueOf(cs.getTaxRate())));

        if (cs.getCurUser().getUserStatus()== UserStatus.OU) {
            price += " + Tax(" + cs.getTaxRate()*100 + "%) " + taxOnPrice.setScale(2, RoundingMode.DOWN).toString();
            minPrice = a.getPrice().add(taxOnPrice).setScale(2, RoundingMode.DOWN);
            for (String friend : cs.getFriendsUsernames()){
                if (a.getOrdinaryUser().getUsername().equals(friend)){
                    price += " - Discount(5%) " + friendDiscount.setScale(2, RoundingMode.DOWN).toString();
                    minPrice = minPrice.subtract(friendDiscount);
                }
            }
        }
        getPrice().setText(price );
        getBidBox().setPromptText((minPrice.toString()));
        Image image = null;
        for (AuctionImage i : a.getAuctionImages()){
            if (i.isDefaultPhoto()){
                image = i.getImage();
            }
        }
        String keywords = "";
        for (AuctionKeyword k : a.getKeywords()){
            keywords+= k.getKeyword() + ", ";
        }
        keywords = keywords.substring(0, keywords.length()-2);
        getKeywords().setText(keywords);
        updateDisplayImage(image);

        if (a.isFixed()) {
            bidBox.setDisable(true);
            fixedLabel.setVisible(true);
        }

        disableBidding();
    }

    public Button getViewBidsButton() {
        return viewBidsButton;
    }

    public void setViewBidsButton(Button viewBidsButton) {
        this.viewBidsButton = viewBidsButton;
    }

    private void disableBidding(){
        ArrayList<Bid> bidArray = currentSession.getBidsForAuction(auctionResult);
        for(Bid b : bidArray){
            if (currentSession.getUserStatus()==UserStatus.OU) {
                if ((currentSession.getCurUser().getUsername()).equals(b.getOrdinaryUser().getUsername())) {
                    bidButton.setDisable(true);
                    bidButton.setText("Placed!");
                    price.setText("Your Bid: " + b.getAmount().toString());
                    bidBox.setDisable(true);
                }
            }
        }

    }

    public BorderPane getParent() {
        return parent;
    }

    public void setParent(BorderPane parent) {
        this.parent = parent;
    }

    public Auction getAuction() {
        return auction;
    }

    public void setAuction(Auction auction) {
        this.auction = auction;
    }
}

