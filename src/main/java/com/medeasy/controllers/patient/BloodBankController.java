package com.medeasy.controllers.patient;

import com.medeasy.controllers.admin.DoctorCardController;
import com.medeasy.models.BloodBank;
import com.medeasy.models.Doctor;
import com.medeasy.models.Record;
import com.medeasy.util.DatabaseConnection;
import com.medeasy.util.DatabaseReadCall;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import org.w3c.dom.Text;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

public class BloodBankController implements Initializable {
    @FXML
    private VBox contrainer;
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
                for (BloodBank bloodBank:getBloodBankList()) {
                    try {
                        FXMLLoader loader = new FXMLLoader();
                        loader.setLocation(getClass().getResource("/com/medeasy/views/others/boodBankCard.fxml"));
                        Parent root = loader.load();
                        BloodBankCardController controller = loader.getController();
                        controller.setBloodBank(bloodBank);
                        contrainer.getChildren().add(root);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });

    }
    private ArrayList<BloodBank> getBloodBankList() {
        ArrayList<BloodBank> bloodBanks = new ArrayList<>();
        String sql = "SELECT * FROM bloodbank";
        try {
            DatabaseConnection databaseConnection = new DatabaseConnection();
            ResultSet resultSet = databaseConnection.queryData(sql);
            while (resultSet.next()) {
                bloodBanks.add(new BloodBank(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4)));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return bloodBanks;
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
        ArrayList<BloodBank> bloodBanks = new ArrayList<>();
        String searchSQL = "select * from bloodbank where (name LIKE ? OR address LIKE ?)";
        HashMap<Integer,Object> searchHash = new HashMap<>();
        searchHash.put(1,"%"+((TextField)event.getSource()).getText()+"%");
        searchHash.put(2,"%"+((TextField)event.getSource()).getText()+"%");
        DatabaseReadCall databaseReadCall = new DatabaseReadCall(searchSQL,searchHash);
        databaseReadCall.setOnSucceeded(workerStateEvent -> {
            try {
                ResultSet resultSet = databaseReadCall.getValue();

                while (resultSet.next()) {
                    bloodBanks.add(new BloodBank(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4)));
                }
                if (bloodBanks.size() > 0) {

                    for (BloodBank bloodBank:bloodBanks) {
                        try {
                            FXMLLoader loader = new FXMLLoader();
                            loader.setLocation(getClass().getResource("/com/medeasy/views/others/boodBankCard.fxml"));
                            Parent root = loader.load();
                            BloodBankCardController controller = loader.getController();
                            controller.setBloodBank(bloodBank);
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
