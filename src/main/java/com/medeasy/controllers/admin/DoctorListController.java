package com.medeasy.controllers.admin;

import com.medeasy.models.Doctor;
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
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

public class DoctorListController implements Initializable {

    @FXML
    private GridPane doctorContainer;
    @FXML
    private TextField searchBox;
    private ArrayList<Doctor> getDoctorList()
    {
        ArrayList<Doctor> doctors = new ArrayList<>();
        String doctorListSQL = "SELECT userID,personNameEn,specialities,qualification,designation,picture FROM doctors";
        try {
            DatabaseConnection databaseConnection = new DatabaseConnection();
            ResultSet resultSet = databaseConnection.queryData(doctorListSQL);
            while (resultSet.next())
            {
                doctors.add(new Doctor(resultSet.getString(1),resultSet.getString(2),resultSet.getString(3),resultSet.getString(4),resultSet.getString(5),resultSet.getBlob(6)));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return doctors;

    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        FadeTransition fadeTransition = new FadeTransition();
        fadeTransition.setNode(doctorContainer);
        fadeTransition.setDuration(Duration.seconds(1));
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1);
        fadeTransition.play();
        if(searchBox.getText().isEmpty()) {
            int col = 0;
            int row = 1;
            for (Doctor doctor : getDoctorList()) {
                try {
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("/com/medeasy/views/doctors/doctorCard.fxml"));
                    Parent root = loader.load();
                    DoctorCardController controller = loader.getController();
                    controller.setDoctor(doctor);
                    if (col == 4) {
                        col = 0;
                        ++row;
                    }
                    doctorContainer.add(root, col++, row);
                    GridPane.setMargin(root, new Insets(20));

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
    @FXML
    void addDoctor(ActionEvent event) {
        FXMLScene fxmlScene = FXMLScene.load("/com/medeasy/views/doctors/addDoctor.fxml");
        Scene scene = new Scene(fxmlScene.getRoot(), Color.TRANSPARENT);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    @FXML
    void search(KeyEvent event) throws SQLException, ClassNotFoundException, IOException {
        doctorContainer.getChildren().clear();
        FadeTransition fadeTransition = new FadeTransition();
        fadeTransition.setNode(doctorContainer);
        fadeTransition.setDuration(Duration.seconds(0.5));
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1);
        fadeTransition.play();
        ArrayList<Doctor> doctors = new ArrayList<>();
        String searchSQL = "select userID,personNameEn,specialities,qualification,designation,picture from doctors where (personNameEn LIKE ? OR specialities LIKE ?)";
        HashMap<Integer,Object> searchHash = new HashMap<>();
        searchHash.put(1,"%"+((TextField)event.getSource()).getText()+"%");
        searchHash.put(2,"%"+((TextField)event.getSource()).getText()+"%");
        DatabaseReadCall databaseReadCall = new DatabaseReadCall(searchSQL,searchHash);
        databaseReadCall.setOnSucceeded(workerStateEvent -> {
            try {
                ResultSet resultSet = databaseReadCall.getValue();

                while (resultSet.next()) {
                    doctors.add(new Doctor(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getString(5), resultSet.getBlob(6)));
                }
                if (doctors.size() > 0) {
                    int col = 0;
                    int row = 1;
                    for (Doctor doctor : doctors) {
                        try {
                            FXMLLoader loader = new FXMLLoader();
                            loader.setLocation(getClass().getResource("/com/medeasy/views/doctors/doctorCard.fxml"));
                            Parent root = loader.load();
                            DoctorCardController controller = loader.getController();
                            controller.setDoctor(doctor);
                            if (col == 4) {
                                col = 0;
                                ++row;
                            }
                            doctorContainer.add(root, col++, row);
                            GridPane.setMargin(root, new Insets(20));

                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }

                } else {
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("/com/medeasy/views/others/noResult.fxml"));
                    Parent root = loader.load();
                    doctorContainer.add(root, 0, 1);
                    GridPane.setMargin(root, new Insets(20));
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
