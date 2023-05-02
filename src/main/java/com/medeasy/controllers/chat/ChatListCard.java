//package com.medeasy.controllers.chat;
//
//import javafx.application.Platform;
//import javafx.fxml.FXML;
//import javafx.fxml.Initializable;
//import javafx.scene.control.Label;
//import javafx.scene.shape.Circle;
//
//import java.net.URL;
//import java.util.ResourceBundle;
//
//public class ChatListCard implements Initializable {
//    @FXML
//    private Circle picture;
//
//    @FXML
//    private Label name;
//
//    @FXML
//    private Label message;
//
//    @FXML
//    private Label time;
//    private Message msg;
//    private String userID;
//
//    public void setUserID(String userID) {
//        this.userID = userID;
//    }
//
//    public void setMessage(Message msg) {
//        this.msg = msg;
//    }
//
//    @Override
//    public void initialize(URL url, ResourceBundle resourceBundle) {
//        Platform.runLater(()->{
//            name.setText(msg.getFrom());
//            message.setText(msg.getText());
//        });
//
//    }
//}
