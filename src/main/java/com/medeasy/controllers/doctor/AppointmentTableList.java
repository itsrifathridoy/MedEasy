package com.medeasy.controllers.doctor;

import com.medeasy.controllers.patient.BloodBankCardController;
import com.medeasy.models.Appointment;
import com.medeasy.models.BloodBank;
import com.medeasy.util.DatabaseConnection;
import com.medeasy.util.DatabaseReadCall;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

public class AppointmentTableList implements Initializable {
    @FXML
    private TextField searchBox;
    @FXML
    private VBox contrainer;
    @FXML
    private String userID;

    public void setUserID(String userID) {
        this.userID = userID;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Platform.runLater(()->{
            if(searchBox.getText().isEmpty()) {
                for (Appointment appointment:getAppointmentList()) {
                    try {
                        FXMLLoader loader = new FXMLLoader();
                        loader.setLocation(getClass().getResource("/com/medeasy/views/doctors/appointmentTableCard.fxml"));
                        Parent root = loader.load();
                        AppointmentTableCardController controller = loader.getController();
                        controller.setAppointment(appointment);
                        contrainer.getChildren().add(root);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });

    }
    private ArrayList<Appointment> getAppointmentList() {
        ArrayList<Appointment> appointments = new ArrayList<>();
        String sql = "SELECT * FROM appointments WHERE doctorID = '"+userID+"'";
        try {
            DatabaseConnection databaseConnection = new DatabaseConnection();
            ResultSet resultSet = databaseConnection.queryData(sql);
            while (resultSet.next()) {
                appointments.add(new Appointment(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4),resultSet.getString(5),resultSet.getString(6),resultSet.getString(7),resultSet.getString(8)));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return appointments;
    }
    @FXML
    void search(KeyEvent event) throws SQLException, ClassNotFoundException, IOException {
        contrainer.getChildren().clear();
        FadeTransition fadeTransition = new FadeTransition();
        fadeTransition.setNode(contrainer);
        fadeTransition.setDuration(Duration.seconds(0.5));
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1);
        fadeTransition.play();
        ArrayList<Appointment> appointments = new ArrayList<>();
        String searchSQL = "select * from appointments where (name LIKE ? OR status LIKE ?)";
        HashMap<Integer,Object> searchHash = new HashMap<>();
        searchHash.put(1,"%"+((TextField)event.getSource()).getText()+"%");
        searchHash.put(2,"%"+((TextField)event.getSource()).getText()+"%");
        DatabaseReadCall databaseReadCall = new DatabaseReadCall(searchSQL,searchHash);
        databaseReadCall.setOnSucceeded(workerStateEvent -> {
            try {
                ResultSet resultSet = databaseReadCall.getValue();

                while (resultSet.next()) {
                    appointments.add(new Appointment(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4),resultSet.getString(5),resultSet.getString(6),resultSet.getString(7),resultSet.getString(8)));
                }
                if (appointments.size() > 0) {

                    for (Appointment appointment:getAppointmentList()) {
                        try {
                            FXMLLoader loader = new FXMLLoader();
                            loader.setLocation(getClass().getResource("/com/medeasy/views/doctors/appointmentTableCard.fxml"));
                            Parent root = loader.load();
                            AppointmentTableCardController controller = loader.getController();
                            controller.setAppointment(appointment);
                            contrainer.getChildren().add(root);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }

                } else {
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("/com/medeasy/views/others/noResult.fxml"));
                    Parent root = loader.load();
                    contrainer.getChildren().add(root);
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

        });
        Thread thread =new Thread(databaseReadCall);
        thread.setDaemon(true);
        thread.start();
    }
}
