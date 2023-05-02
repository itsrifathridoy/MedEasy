package com.medeasy.controllers.patient;

import com.medeasy.models.Patient;
import com.medeasy.util.DatabaseConnection;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

import java.awt.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ProfileController implements Initializable {

    @FXML
    private Label name;

    @FXML
    private Label fatherName;

    @FXML
    private Label motherName;

    @FXML
    private Label dob;
    @FXML
    private Rectangle img;
    @FXML
    private Label email;

    @FXML
    private Label address;

    @FXML
    private Label username;

    @FXML
    private Label bloodGroup;

    @FXML
    private Label height;

    @FXML
    private Label weight;

    @FXML
    private Label lastAppointment;

    @FXML
    private Label currentDisease;
    private String userID;

    public void setUserID(String userID) {
        this.userID = userID;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Platform.runLater(()->{
            try {
                DatabaseConnection databaseConnection =new DatabaseConnection();
                Patient patient = databaseConnection.getPatient(userID,"userID");
                name.setText(patient.getPersonNameBn());
                fatherName.setText(patient.getFatherNameBn());
                motherName.setText(patient.getMotherNameBn());
                dob.setText(patient.getDob());
                bloodGroup.setText(patient.getBloodGroup());
                currentDisease.setText(patient.getDisease());
                lastAppointment.setText(patient.getLastAppointment());
                username.setText(patient.getUsername());
                String path = "temp/"+patient.getUserID()+".png";
                if(patient.getBlob()!=null) {
                    byte byteArray[] = patient.getBlob().getBytes(1, (int) patient.getBlob().length());
                    FileOutputStream outPutStream = new FileOutputStream(path);
                    outPutStream.write(byteArray);
                    outPutStream.close();
                    FileInputStream imgStream = new FileInputStream(path);
                    img.setFill(new ImagePattern(new Image(imgStream)));
                }
                else
                {
                    FileInputStream imgStream = new FileInputStream("src/main/resources/com/medeasy/img/img_5.png");
                    img.setFill(new ImagePattern(new Image(imgStream)));
                }
                email.setText(patient.getEmail());
                address.setText(patient.getAddressBn());
                height.setText(patient.getHeight());
                weight.setText(patient.getWeight());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        });
    }
}
