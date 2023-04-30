package com.medeasy.controllers;

import com.medeasy.Main;
import com.medeasy.models.Patient;
import com.medeasy.util.FXMLScene;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DetailsViewController implements Initializable {
    @FXML
    private Label name;

    @FXML
    private Label fName;

    @FXML
    private Label mName;

    @FXML
    private Label dob;

    @FXML
    private Label address;

    @FXML
    private Label address1;
    private Patient patient;
    private Stage mainStage;

    public void setMainStage(Stage mainStage) {
        this.mainStage = mainStage;
    }

    public void setPatient(Patient patient) {

        this.patient = patient;
        if(patient.getPersonNameEn()!=null)
        {
            name.setText(patient.getPersonNameEn());
            dob.setText(patient.getDob());
            fName.setText(patient.getFatherNameEn());
            mName.setText(patient.getMotherNameEn());
            String[] str = patient.getAddressEn().split(",");
            String temp = "";
            for (int i = 0; i < str.length - 2; i++) {
                temp = temp + str[i] + ",";
            }
            address.setText(temp);
            address1.setText(str[str.length - 2] + "," + str[str.length - 1]);
        }
        else {
            name.setText(patient.getPersonNameBn());
            dob.setText(patient.getDob());
            fName.setText(patient.getFatherNameBn());
            mName.setText(patient.getMotherNameBn());
            String[] str = patient.getAddressBn().split(",");
            String temp = "";
            for (int i = 0; i < str.length - 2; i++) {
                temp = temp + str[i] + ",";
            }
            address.setText(temp);
            address1.setText(str[str.length - 2] + "," + str[str.length - 1]);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Platform.runLater(()->{
            Main.enableMove(name.getScene(),(Stage) name.getScene().getWindow());
        });
    }


    public void nextCreateAccount(ActionEvent actionEvent) throws IOException {
        FXMLScene fxmlScene = FXMLScene.load("/com/medeasy/views/verify_input.fxml");
        System.out.println(fxmlScene.getController());
        VerifyInputController controller = (VerifyInputController) fxmlScene.getController();
        controller.setPatient(patient);
        controller.setMainStage(mainStage);
        Scene scene = new Scene(fxmlScene.getRoot());
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
    public void close(MouseEvent me) {
        Stage stage = (Stage) ((Node) me.getSource()).getScene().getWindow();
        stage.close();
    }

    public void minimize(MouseEvent me) {
        Stage stage = (Stage) ((Node) me.getSource()).getScene().getWindow();
        stage.setIconified(true);
    }

    public void cancel(ActionEvent actionEvent) {
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }
}
