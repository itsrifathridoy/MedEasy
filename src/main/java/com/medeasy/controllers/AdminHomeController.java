package com.medeasy.controllers;

import com.medeasy.Main;
import com.medeasy.models.Admin;
import com.medeasy.models.Patient;
import com.medeasy.util.DatabaseConnection;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.UUID;

public class AdminHomeController implements Initializable {
    @FXML
    Rectangle img;

    @FXML
    private Label date;

    @FXML
    private Label time;

    @FXML
    private Label name;

    @FXML
    private Label numOfDoctors;

    @FXML
    private Label numOfPatients;
    @FXML
    private BorderPane rootPane;
    private String email;

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
                name.setText(admin.getPersonNameEn());
                numOfDoctors.setText(String.valueOf(numOfDoctor));
                numOfPatients.setText(String.valueOf(numOfPatient));
                byte byteArray[] = admin.getBlob().getBytes(1, (int) admin.getBlob().length());
                FileOutputStream outPutStream = new FileOutputStream(path);
                outPutStream.write(byteArray);
                outPutStream.close();
                FileInputStream imgStream = new FileInputStream(path);
                img.setFill(new ImagePattern(new Image(imgStream)));
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
                loader.setLocation(getClass().getResource("/com/medeasy/views/doctorsList.fxml"));

                Parent root = loader.load();
                rootPane.setCenter(root);
                rootPane.setRight(null);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

    }
}
