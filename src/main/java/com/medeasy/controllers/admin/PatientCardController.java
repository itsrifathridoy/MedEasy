package com.medeasy.controllers.admin;

import com.medeasy.models.Doctor;
import com.medeasy.models.Patient;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.URL;
import java.util.ResourceBundle;

public class PatientCardController implements Initializable {
    private Patient patient;
    @FXML
    private Circle picture;

    @FXML
    private Label name;

    @FXML
    private Label lastAppointment;

    @FXML
    private Label bloodGroup;

    @FXML
    private Label disease;

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {

                    name.setText(patient.getUsername());
                    lastAppointment.setText(patient.getLastAppointment());
                    disease.setText(patient.getDisease());
                    bloodGroup.setText(patient.getBloodGroup());

                    String path = "temp/" + patient.getUserID() + ".png";
                    byte byteArray[] = patient.getBlob().getBytes(1, (int) patient.getBlob().length());
                    FileOutputStream outPutStream = new FileOutputStream(path);
                    outPutStream.write(byteArray);
                    outPutStream.close();
                    FileInputStream imgStream = new FileInputStream(path);
                    picture.setFill(new ImagePattern(new Image(imgStream)));

                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }

            }
        });

    }
    @FXML
    void sendMessage(ActionEvent event) {

    }

    @FXML
    void viewDetails(ActionEvent event) {

    }
}
