package com.medeasy.controllers.doctor;

import com.medeasy.models.Appointment;
import com.medeasy.models.Patient;
import com.medeasy.util.DatabaseConnection;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.shape.Circle;

import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AppointmentCardController implements Initializable {
    @FXML
    private Circle img;

    @FXML
    private Label name;

    @FXML
    private Label status;

    @FXML
    private Label discription;
    private Appointment appointment;

    public void setAppointment(Appointment appointment) {
        this.appointment = appointment;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Platform.runLater(()->{
            try {
                DatabaseConnection databaseConnection = new DatabaseConnection();
                Patient patient= databaseConnection.getPatient(appointment.getUserID(), "userID");
                discription.setText(appointment.getDiscription());
                name.setText(patient.getUsername());
                status.setText(appointment.getStatus());

            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }

        });
    }
}
