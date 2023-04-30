package com.medeasy.controllers.admin;

import com.medeasy.models.Doctor;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.shape.Circle;

import java.net.URL;
import java.util.ResourceBundle;

public class DoctorCardController implements Initializable {
    private Doctor doctor;
    @FXML
    private Circle picture;

    @FXML
    private Label name;

    @FXML
    private Label qualification;

    @FXML
    private Label designation;

    @FXML
    private Label specialities;

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                name.setText(doctor.getPersonNameEn());
                qualification.setText(doctor.getQualification());
                designation.setText(doctor.getDesignation());
                specialities.setText(doctor.getSpecialities());
            }
        });

    }
}
