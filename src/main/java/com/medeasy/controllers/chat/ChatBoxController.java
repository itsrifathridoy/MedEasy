//package com.medeasy.controllers.chat;
//
//import com.medeasy.util.DatabaseConnection;
//import javafx.application.Platform;
//import javafx.fxml.FXML;
//import javafx.fxml.FXMLLoader;
//import javafx.fxml.Initializable;
//import javafx.scene.Parent;
//import javafx.scene.control.Label;
//import javafx.scene.layout.VBox;
//import javafx.scene.shape.Circle;
//
//import java.io.FileInputStream;
//import java.io.FileOutputStream;
//import java.io.ObjectInputStream;
//import java.net.URL;
//import java.sql.Blob;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.util.ArrayList;
//import java.util.ResourceBundle;
//
//public class ChatBoxController implements Initializable {
//
//    @FXML
//    private VBox chatListContainer;
//
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
//    private String userID;
//
//    public void setUserID(String userID) {
//        System.out.println(userID);
//        this.userID = userID;
//    }
//
//    private ArrayList<Message> getInbox()
//    {
//        try {
//            String sql = "SELECT inbox FROM chatbox WHERE userID = ?";
//            DatabaseConnection databaseConnection = new DatabaseConnection();
//            PreparedStatement statement = databaseConnection.getConnection().prepareStatement(sql);
//            System.out.println(getClass().getSimpleName()+userID);
//            statement.setString(1, userID);
//            ResultSet resultSet = statement.executeQuery();
//            Blob blob = null;
//            while (resultSet.next()) {
//                blob = resultSet.getBlob(1);
//            }
//            String path = "temp/" + userID;
//            if(blob!=null)
//            {
//                byte byteArray[] = blob.getBytes(1, (int) blob.length());
//                FileOutputStream outPutStream = new FileOutputStream(path);
//                outPutStream.write(byteArray);
//                outPutStream.close();
//                FileInputStream imgStream = new FileInputStream(path);
//                ObjectInputStream objectInputStream = new ObjectInputStream(imgStream);
//                ArrayList<Message> inbox = (ArrayList<Message>) objectInputStream.readObject();
//                return inbox;
//            }
//            else
//            {
//               return null;
//            }
//
//
//        }catch (Exception e)
//        {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    @Override
//    public void initialize(URL url, ResourceBundle resourceBundle) {
//        Platform.runLater(()->{
//            try {
//                ArrayList<Message> inbox = getInbox();
//                if(inbox!=null) {
//                    for (Message msg:inbox) {
//                        FXMLLoader loader = new FXMLLoader();
//                        loader.setLocation(getClass().getResource("/com/medeasy/views/chatBox/chatListCard.fxml"));
//                        Parent root = loader.load();
//                        ChatListCard controller = loader.getController();
//                        controller.setMessage(msg);
//                        controller.setUserID(userID);
//                        chatListContainer.getChildren().add(root);
//                    }
//                }
//            }
//            catch (Exception e)
//            {
//                e.printStackTrace();
//            }
//        });
//
//    }
//}
