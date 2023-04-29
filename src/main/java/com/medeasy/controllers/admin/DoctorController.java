package com.medeasy.controllers.admin;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DoctorController implements Initializable {

    @FXML
    private GridPane doctorContainer;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        int col=0;
        int row=1;
        for(int i=0;i<10;i++)
        {
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/com/medeasy/views/doctorCard.fxml"));

                Parent root = loader.load();
                if(col==4)
                {
                    col=0;
                    ++row;
                }
                doctorContainer.add(root,col++,row);
                GridPane.setMargin(root,new Insets(20));

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
    @FXML
    void addDoctor(ActionEvent event) {
        Stage stage = new Stage();
        stage.show();
    }
}
