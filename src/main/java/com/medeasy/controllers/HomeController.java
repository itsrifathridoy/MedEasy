package com.medeasy.controllers;

import com.medeasy.controllers.home.AppointmentCardController;
import com.medeasy.util.FXMLScene;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HomeController implements Initializable {

    @FXML
    private VBox appointmentContainer;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        for(int i=0;i<10;i++)
        {
            try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/com/medeasy/views/appointmentCard.fxml"));

                Parent root = loader.load();

            appointmentContainer.getChildren().add(root);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
