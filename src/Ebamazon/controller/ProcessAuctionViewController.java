package Ebamazon.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import Ebamazon.model.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;

public class ProcessAuctionViewController {

    private CurrentSession currentSession;
    private ToggleGroup bidType;
    private ToggleGroup acceptDeny;
    private Auction curAuction;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label bannedWarning;

    @FXML
    private TextField title;

    @FXML
    private TextArea auctionDescTextArea;

    @FXML
    private TextField keywords;

    @FXML
    private RadioButton bid;

    @FXML
    private RadioButton fixed;

    @FXML
    private TextField price;

    @FXML
    private ImageView image;

    @FXML
    private RadioButton accept;

    @FXML
    private RadioButton deny;

    @FXML
    private TextArea reason;

    @FXML
    private Button submit;

    @FXML
    private Label numRemainingLabel;

    @FXML
    void disableReason(ActionEvent event) {
        reason.setDisable(true);
    }

    @FXML
    void enableReason(ActionEvent event) {
        reason.setDisable(false);
    }

    @FXML
    void submit(ActionEvent event) {
        SuperUser su = (SuperUser)currentSession.getCurUser();
        if (accept.isSelected()){
            su.confirmAuction(curAuction);
            attachAuction();
        } else {
            su.denyAuction(curAuction);
            //add auction to blacklisted items.
            //set reason blacklisted to reason in box
            attachAuction();
        }
    }

    @FXML
    void initialize() {
        assert bannedWarning != null : "fx:id=\"bannedWarning\" was not injected: check your FXML file 'processAuctionView.fxml'.";
        assert title != null : "fx:id=\"title\" was not injected: check your FXML file 'processAuctionView.fxml'.";
        assert auctionDescTextArea != null : "fx:id=\"auctionDescTextArea\" was not injected: check your FXML file 'processAuctionView.fxml'.";
        assert keywords != null : "fx:id=\"keywords\" was not injected: check your FXML file 'processAuctionView.fxml'.";
        assert bid != null : "fx:id=\"bid\" was not injected: check your FXML file 'processAuctionView.fxml'.";
        assert fixed != null : "fx:id=\"fixed\" was not injected: check your FXML file 'processAuctionView.fxml'.";
        assert price != null : "fx:id=\"price\" was not injected: check your FXML file 'processAuctionView.fxml'.";
        assert image != null : "fx:id=\"image\" was not injected: check your FXML file 'processAuctionView.fxml'.";
        assert accept != null : "fx:id=\"accept\" was not injected: check your FXML file 'processAuctionView.fxml'.";
        assert deny != null : "fx:id=\"deny\" was not injected: check your FXML file 'processAuctionView.fxml'.";
        assert reason != null : "fx:id=\"reason\" was not injected: check your FXML file 'processAuctionView.fxml'.";
        assert submit != null : "fx:id=\"submit\" was not injected: check your FXML file 'processAuctionView.fxml'.";
        bidType = new ToggleGroup();
        acceptDeny = new ToggleGroup();

        bannedWarning.setVisible(false);
        bid.setToggleGroup(bidType);
        fixed.setToggleGroup(bidType);
        accept.setToggleGroup(acceptDeny);
        deny.setToggleGroup(acceptDeny);
    }

    public void setCurrentSession(CurrentSession currentSession) {
        this.currentSession = currentSession;
        attachAuction();
    }

    private void attachAuction(){
        SuperUser su = (SuperUser)currentSession.getCurUser();
        ArrayList<Auction> auctions = su.getPendingAuctions();
        numRemainingLabel.setText(Integer.toString(auctions.size()));
        if (auctions.size() > 0){
            curAuction = auctions.get(0);
            title.setText(curAuction.getTitle());
            auctionDescTextArea.setText(curAuction.getDescription());
            keywords.setText(unparseKeyWords(curAuction.getKeywords()));
            if (curAuction.isFixed()) fixed.setSelected(true);
            else bid.setSelected(true);
            price.setText(curAuction.getPrice().toString());
            image.setVisible(true);
            image.setImage(curAuction.getAuctionImages().get(0).getImage());
        }
        else{
            clearAllFields();
        }
    }

    private String unparseKeyWords(ArrayList<AuctionKeyword> list){
        String keywords = "";
        for (AuctionKeyword ak : list){
            keywords += ak.getKeyword() + " ";
        }
        return keywords;
    }

    private void clearAllFields(){
        title.setText("");
        auctionDescTextArea.setText("");
        keywords.setText("");
        fixed.setSelected(false);
        bid.setSelected(false);
        price.setText("");
        image.setVisible(false);
    }
}
