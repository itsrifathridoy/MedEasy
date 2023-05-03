package com.medeasy.controllers.patient;

import com.medeasy.controllers.doctor.AppointmentCardController;
import com.medeasy.models.Appointment;
import com.medeasy.models.BloodBank;
import com.medeasy.models.Record;
import com.medeasy.util.DatabaseConnection;
import com.medeasy.util.DatabaseReadCall;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
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

public class RecordController implements Initializable {
    @FXML
    private VBox recordContrainer;
    @FXML
    private TextField searchBox;
    private String userID;

    public void setUserID(String userID) {
        this.userID = userID;
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Platform.runLater(()->{
            if(searchBox.getText().isEmpty()) {
                for (Record record : getRecordList()) {
                    try {
                        FXMLLoader loader = new FXMLLoader();
                        loader.setLocation(getClass().getResource("/com/medeasy/views/patients/recordCard.fxml"));
                        Parent root = loader.load();
                        RecordCardController controller = loader.getController();
                        controller.setRecord(record);
                        recordContrainer.getChildren().add(root);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });

    }
    private ArrayList<Record> getRecordList() {
        System.out.println(userID);
        ArrayList<Record> records = new ArrayList<>();
        String sql = "SELECT * FROM records WHERE userID = '" + userID + "'";
        try {
            DatabaseConnection databaseConnection = new DatabaseConnection();
            ResultSet resultSet = databaseConnection.queryData(sql);
            while (resultSet.next()) {
                records.add(new Record(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getString(5),resultSet.getString(6),resultSet.getBlob(7)));
            }
            System.out.println(records.size());

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return records;
    }

    @FXML
    void search(KeyEvent event) throws SQLException, ClassNotFoundException, IOException {
        recordContrainer.getChildren().clear();
        FadeTransition fadeTransition = new FadeTransition();
        fadeTransition.setNode(recordContrainer);
        fadeTransition.setDuration(Duration.seconds(0.5));
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1);
        fadeTransition.play();
        ArrayList<Record> records = new ArrayList<>();
        String searchSQL = "select * from records where (title LIKE ? OR type LIKE ? OR doctorName LIKE ?)";
        HashMap<Integer,Object> searchHash = new HashMap<>();
        searchHash.put(1,"%"+((TextField)event.getSource()).getText()+"%");
        searchHash.put(2,"%"+((TextField)event.getSource()).getText()+"%");
        searchHash.put(3,"%"+((TextField)event.getSource()).getText()+"%");
        DatabaseReadCall databaseReadCall = new DatabaseReadCall(searchSQL,searchHash);
        databaseReadCall.setOnSucceeded(workerStateEvent -> {
            try {
                ResultSet resultSet = databaseReadCall.getValue();

                while (resultSet.next()) {
                    records.add(new Record(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getString(5),resultSet.getString(6),resultSet.getBlob(7)));
                }
                if (records.size() > 0) {

                    for (Record record:records) {
                        try {
                            FXMLLoader loader = new FXMLLoader();
                            loader.setLocation(getClass().getResource("/com/medeasy/views/patients/recordCard.fxml"));
                            Parent root = loader.load();
                            RecordCardController controller = loader.getController();
                            controller.setRecord(record);
                            recordContrainer.getChildren().add(root);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }

                } else {
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("/com/medeasy/views/others/noResult.fxml"));
                    Parent root = loader.load();
                    recordContrainer.getChildren().add(root);
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
