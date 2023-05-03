package com.medeasy.controllers.admin;

import com.medeasy.chatsocket.chat.controller.ClientFormController;
import com.medeasy.models.Admin;
import com.medeasy.models.Doctor;
import com.medeasy.util.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.*;
import java.net.Socket;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.ResourceBundle;

public class AdminHomeController extends Thread implements Initializable {
    @FXML
    private BorderPane rootPane;

    @FXML
    private Label date;

    @FXML
    private Label time;

    @FXML
    private Label name;

    @FXML
    private Rectangle img;

    @FXML
    private Label numOfDoctors;

    @FXML
    private Label numOfPatients;
    private String email;
    private String userID;
    private BufferedReader bufferedReader;
    private Socket socket;
    PrintWriter printWriter;
    @FXML
    private Label grettings;

    public void setUserID(String userID) {
        this.userID = userID;
    }


    @FXML
    void chatbox(ActionEvent event) throws IOException {

        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/com/medeasy/views/chatbox.fxml"));

            Parent root = loader.load();
            ClientFormController controller = loader.getController();
            System.out.println(controller);
            controller.setUsername(name.getText());
            rootPane.setCenter(root);
            rootPane.setRight(null);
            rootPane.setTop(null);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void dashboard(ActionEvent event) {
        FXMLScene fxmlScene = FXMLScene.load("/com/medeasy/views/admin/adminHome.fxml");
        Scene scene = new Scene(fxmlScene.getRoot());
        AdminHomeController controller = (AdminHomeController) fxmlScene.getController();
        controller.setEmail(email);
        controller.setUserID(userID);
        Stage stage = (Stage) rootPane.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    void logout(ActionEvent event) {
        LoginInfoSave.clearLoginInfo();
        FXMLScene fxmlScene = FXMLScene.load("/com/medeasy/views/loginReg/login.fxml");
        FXMLScene.switchScene(fxmlScene, (Node) event.getSource());
    }

    @FXML
    void profile(ActionEvent event) {

    }

    @FXML
    void patients(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/com/medeasy/views/admin/patientsList.fxml"));

            Parent root = loader.load();
            rootPane.setCenter(root);
            rootPane.setRight(null);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public void setEmail(String email) {
        this.email = email;
    }
    private int getNumOfUsers(String role) throws SQLException, ClassNotFoundException {
        String sql = "SELECT COUNT(*) FROM users WHERE role = ?";
        DatabaseConnection databaseConnection = new DatabaseConnection();
        Connection connection = databaseConnection.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1,role);

        ResultSet resultSet = statement.executeQuery();
        System.out.println(resultSet);
        int count=0;
        while (resultSet.next())
        {
            count =resultSet.getInt(1);
        }
        return count;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Platform.runLater(()->{
            DatabaseConnection db = null;
            try {
                db = new DatabaseConnection();
            } catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            try {
                Admin admin = db.getAdmin(email,"email");
                int numOfDoctor = getNumOfUsers("DOCTOR");
                int numOfPatient = getNumOfUsers("PATIENT");
                String path = "temp/"+admin.getUserID()+".png";
                System.out.println(admin);
                setUserID(admin.getUserID());
                grettings.setText(new GreetingMaker(LocalTime.now()).printTimeOfDay());
                name.setText(admin.getPersonNameEn());
                numOfDoctors.setText(String.valueOf(numOfDoctor));
                numOfPatients.setText(String.valueOf(numOfPatient));
                if(admin.getBlob()!=null) {
                    byte byteArray[] = admin.getBlob().getBytes(1, (int) admin.getBlob().length());
                    FileOutputStream outPutStream = new FileOutputStream(path);
                    outPutStream.write(byteArray);
                    outPutStream.close();
                    FileInputStream imgStream = new FileInputStream(path);
                    img.setFill(new ImagePattern(new Image(imgStream)));
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        });

    }
    @FXML
    void doctors(ActionEvent event) {
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/com/medeasy/views/doctors/doctorsList.fxml"));
                Parent root = loader.load();
                DoctorListController controller = loader.getController();
                controller.setUserID(userID);
                rootPane.setCenter(root);
                rootPane.setRight(null);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

    }
}
