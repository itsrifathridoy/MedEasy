package com.medeasy.controllers.doctor;

import com.jfoenix.controls.JFXButton;
import com.medeasy.models.Appointment;
import com.medeasy.util.DatabaseConnection;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class AppointmentTableCardController implements Initializable {
    @FXML
    private Label name;

    @FXML
    private Label description;
    @FXML
    private JFXButton statusBtn;
    private Appointment appointment;
    private String userID;


    public void setAppointment(Appointment appointment) {
        this.appointment = appointment;
    }

    @FXML
    void approval(ActionEvent event) {
        if(statusBtn.getText().equals("Pending"))
        {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Are you sure?");
            alert.setHeaderText("Want to approve?");
            Optional<ButtonType> optional = alert.showAndWait();
            if(optional.get() == ButtonType.OK)
            {
                try {
                    DatabaseConnection databaseConnection = new DatabaseConnection();
                    String sql = "UPDATE appointments SET `status` = ? where `appointmentID` = ?";
                    Connection connection = databaseConnection.getConnection();
                    PreparedStatement statement = connection.prepareStatement(sql);
                    statement.setString(1, "Approved");
                    statement.setString(2, appointment.getAppointmentID());

                    int result = statement.executeUpdate();
                    if(result>0) {
                        statusBtn.setText("Approved");
                        statusBtn.setStyle("-fx-background-color: #48c8a7");
                    }

                } catch (SQLException e) {
                    throw new RuntimeException(e);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }

            }
        }
        else if(statusBtn.getText().equals("Approved"))

        {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Are you sure?");
            alert.setHeaderText("Are you completed this appointment?");
            Optional<ButtonType> optional = alert.showAndWait();
            if(optional.get() == ButtonType.OK)
            {
                try {
                    DatabaseConnection databaseConnection = new DatabaseConnection();
                    String sql = "UPDATE appointments SET `status` = ? where `appointmentID` = ?";
                    Connection connection = databaseConnection.getConnection();
                    PreparedStatement statement = connection.prepareStatement(sql);
                    statement.setString(1, "Done");
                    statement.setString(2, appointment.getAppointmentID());
                    int result = statement.executeUpdate();
                    if(result>0) {
                        statusBtn.setText("Done");
                        statusBtn.setStyle("-fx-background-color: #096950");
                        statusBtn.setDisable(true);
                    }

                } catch (SQLException e) {
                    throw new RuntimeException(e);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }

            }
        }
    }

    @FXML
    void view(ActionEvent event) {

    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Platform.runLater(()->{
            name.setText(appointment.getName());
            description.setText(appointment.getDiscription());
            userID = appointment.getUserID();
            String status = appointment.getStatus();
            statusBtn.setText(status);
            if(status.equals("Pending"))
            {
                statusBtn.setStyle("-fx-background-color: #e7a313");
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Are you sure?");
                alert.setHeaderText("Want to approve?");
                Optional<ButtonType> optional = alert.showAndWait();
                if(optional.get() == ButtonType.OK)
                {
                    try {
                        DatabaseConnection databaseConnection = new DatabaseConnection();
                        String sql = "UPDATE appointments SET `status` = ?";
                        Connection connection = databaseConnection.getConnection();
                        PreparedStatement statement = connection.prepareStatement(sql);
                        statement.setString(1,"Approved");
                        statusBtn.setText("Approved");
                        statusBtn.setStyle("-fx-background-color: #48c8a7");

                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    } catch (ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    }

                }
            }
            else if(status.equals("Done"))

            {
                statusBtn.setStyle("-fx-background-color: #096950");
                statusBtn.setDisable(true);
            }
            else if(status.equals("Approved"))

            {
                statusBtn.setStyle("-fx-background-color: #48c8a7");
            }
        });
    }
}
