package com.medeasy.controllers.doctor;

import com.medeasy.models.Appointment;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class AppointmentTableCardController implements Initializable {
    @FXML
    private Label name;

    @FXML
    private Label description;
    private Appointment appointment;


    public void setAppointment(Appointment appointment) {
        this.appointment = appointment;
    }

    @FXML
    void approval(ActionEvent event) {

    }

    @FXML
    void view(ActionEvent event) {

    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Platform.runLater(()->{
            name.setText(appointment.getName());
            description.setText(appointment.getDiscription());
        });
    }
}
