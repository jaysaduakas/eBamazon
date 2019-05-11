package Ebamazon.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

import Ebamazon.model.CurrentSession;
import Ebamazon.model.SuperUser;
import Ebamazon.model.Taboo;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class MaintainTabooViewController {

    private CurrentSession currentSession;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextArea tabooTextArea;

    @FXML
    private VBox tabooComponentVBox;

    @FXML
    private Button submitButton;

    @FXML
    void submitTaboo(ActionEvent event) throws IOException {
        if (tabooTextArea.getText().equals("")) return;
        for (String s : parseTabooInput()) {
            Taboo taboo = new Taboo();
            taboo.setSuperUser((SuperUser) currentSession.getCurUser());
            taboo.setWord(s);
            if (currentSession.insertTaboo(taboo)) {
                FXMLLoader tabooComLoader = new FXMLLoader();
                tabooComLoader.setLocation(getClass().getResource("../view/tabooComponentView.fxml"));
                HBox view = tabooComLoader.load();
                TabooComponentViewController tcvc = tabooComLoader.getController();
                tcvc.setTaboo(taboo.getWord());
                tcvc.setCurrentSession(currentSession);
                tabooComponentVBox.getChildren().add(view);
            }
        }
        tabooTextArea.setText("");
    }

    @FXML
    void initialize() {
        assert tabooTextArea != null : "fx:id=\"tabooTextArea\" was not injected: check your FXML file 'maintainTabooView.fxml'.";
        assert tabooComponentVBox != null : "fx:id=\"tabooComponentVBox\" was not injected: check your FXML file 'maintainTabooView.fxml'.";
        assert submitButton != null : "fx:id=\"submitButton\" was not injected: check your FXML file 'maintainTabooView.fxml'.";

    }

    public void setCurrentSession(CurrentSession currentSession) {
        this.currentSession = currentSession;
        attachTabooComponents();

    }

    private void attachTabooComponents(){
        try {
            for (String s : currentSession.getAllTaboos()){
                FXMLLoader tabooComLoader = new FXMLLoader();
                tabooComLoader.setLocation(getClass().getResource("../view/tabooComponentView.fxml"));
                HBox view = tabooComLoader.load();
                TabooComponentViewController tcvc = tabooComLoader.getController();
                tcvc.setTaboo(s);
                tcvc.setCurrentSession(currentSession);
                tabooComponentVBox.getChildren().add(view);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private ArrayList<String> parseTabooInput(){
        String [] auctionKeywords = tabooTextArea.getText().split(" ");
        return new ArrayList<String>(Arrays.asList(auctionKeywords));
    }
}
