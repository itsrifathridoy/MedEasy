package com.medeasy.controllers.admin;

import com.jfoenix.controls.JFXButton;
import com.medeasy.models.Doctor;
import com.medeasy.models.Patient;
import com.medeasy.util.DatabaseConnection;
import com.medeasy.util.DatabaseReadCall;
import com.medeasy.util.FXMLScene;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class PatientsController implements Initializable {

    @FXML
    private GridPane patientsContainer;
    @FXML
    private TextField searchBox;
    private ArrayList<Patient> getPatientsList()
    {
        ArrayList<Patient> patients = new ArrayList<>();
        String patientsListSQL = "SELECT userID,username,lastAppointment,bloodGroup,currentDisease,picture FROM patients";
        try {
            DatabaseConnection databaseConnection = new DatabaseConnection();
            ResultSet resultSet = databaseConnection.queryData(patientsListSQL);
            while (resultSet.next())
            {
                patients.add(new Patient(resultSet.getString(1),resultSet.getString(2),resultSet.getString(3),resultSet.getString(4),resultSet.getString(5),resultSet.getBlob(6)));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return patients;

    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if(searchBox.getText().isEmpty()) {
            int col = 0;
            int row = 1;
            for (Patient patient : getPatientsList()) {
                try {
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("/com/medeasy/views/patientsCard.fxml"));
                    Parent root = loader.load();
                    PatientCardController controller = loader.getController();
                    controller.setPatient(patient);
                    if (col == 4) {
                        col = 0;
                        ++row;
                    }
                    patientsContainer.add(root, col++, row);
                    GridPane.setMargin(root, new Insets(20));

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    @FXML
    void search(KeyEvent event) throws SQLException, ClassNotFoundException, IOException {
        patientsContainer.getChildren().clear();
        FadeTransition fadeTransition = new FadeTransition();
        fadeTransition.setNode(patientsContainer);
        fadeTransition.setDuration(Duration.seconds(0.5));
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1);
        fadeTransition.play();
        ArrayList<Patient> patients = new ArrayList<>();
        DatabaseConnection databaseConnection = new DatabaseConnection();
        Connection connection = databaseConnection.getConnection();
        PreparedStatement statement = connection.prepareStatement("select userID,username,lastAppointment,bloodGroup,currentDisease,picture from patients where (username LIKE ?)");
        statement.setString(1,"%"+((TextField)event.getSource()).getText()+"%");
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next())
        {
            patients.add(new Patient(resultSet.getString(1),resultSet.getString(2),resultSet.getString(3),resultSet.getString(4),resultSet.getString(5),resultSet.getBlob(6)));
        }
        if(patients.size()>0)
        {
            int col = 0;
            int row = 1;
            for (Patient patient : getPatientsList()) {
                try {
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("/com/medeasy/views/patientsCard.fxml"));
                    Parent root = loader.load();
                    PatientCardController controller = loader.getController();
                    controller.setPatient(patient);
                    if (col == 4) {
                        col = 0;
                        ++row;
                    }
                    patientsContainer.add(root, col++, row);
                    GridPane.setMargin(root, new Insets(20));

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

        }
        else
        {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/com/medeasy/views/noResult.fxml"));
            Parent root = loader.load();
            patientsContainer.add(root, 0, 1);
            GridPane.setMargin(root, new Insets(20));
        }


    }
}
